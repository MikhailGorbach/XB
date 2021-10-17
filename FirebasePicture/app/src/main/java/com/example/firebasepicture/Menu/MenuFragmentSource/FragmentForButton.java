package com.example.firebasepicture.Menu.MenuFragmentSource;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.firebasepicture.R;
import com.firebase.ui.firestore.ChangeEventListener;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Objects;

public class FragmentForButton extends Fragment{
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter adapter;
    private FragmentForButton fragment;
    private int name;
    private Query query;
    private List<Model> list;

    public FragmentForButton(int name){
        this.name = name;
        fragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        initComponents(v);

        return v;
    }

    private void initComponents(View v){
        recyclerView = v.findViewById(R.id.recview);

        firebaseFirestore = FirebaseFirestore.getInstance();
        query = firebaseFirestore.collection("models").whereEqualTo("categories",getString(name)).limit(6);

        //Подключаем корневую папку с карточками
        FirestoreRecyclerOptions<Model> options =
                new FirestoreRecyclerOptions.Builder<Model>()
                    .setQuery(query, Model.class)
                    .build();

        adapter = new FirestoreRecyclerAdapter<Model, AdapterToCards>(options) {
             @NonNull
             @Override
             public AdapterToCards onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                 View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_disign, parent, false);
                 return new AdapterToCards(v);
             }

             @Override
             protected void onBindViewHolder(@NonNull AdapterToCards holder, int position, @NonNull Model model) {
                 holder.txtTitle.setText(model.getTitle());
                 holder.txtPrice.setText(model.getPrice());
                 Glide.with(holder.img1.getContext()).load(model.getPic()).into(holder.img1);
                 holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         getFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentForCard(model, fragment)).commit();
                     }
                 });
             }
        };
        Context ctx = this.getContext();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ctx, 2);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(int currentPage) {
                Query nextQuery = firebaseFirestore.collection("models")
                        .orderBy("title")
                        .startAfter(adapter.getItem(adapter.getItemCount()-1))
                        .whereEqualTo("categories",getString(name))
                        .limit(6);

            }
        });
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    public class AdapterToCards extends RecyclerView.ViewHolder {

        public ImageView img1;
        public TextView txtTitle;
        public TextView txtPrice;
        public RelativeLayout relativeLayout;

        public AdapterToCards(@NonNull View itemView) {
            super(itemView);

            img1 = itemView.findViewById(R.id.img1);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            relativeLayout = itemView.findViewById(R.id.backLayout);
        }
    }

    public abstract static class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
        public static String TAG = "EndlessScrollListener";

        private int previousTotal = 0; // The total number of items in the dataset after the last load
        private boolean loading = true; // True if we are still waiting for the last set of data to load.
        private int visibleThreshold = 1;   //Минимальное кол. элементов, вниз, после которых начётся загрузка
        int firstVisibleItem, visibleItemCount, totalItemCount;

        private int currentPage = 1;

        RecyclerViewPositionHelper mRecyclerViewHelper;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(recyclerView);
            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = mRecyclerViewHelper.getItemCount();
            firstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();


            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            if (!loading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + visibleThreshold)) {
                // End has been reached
                // Do something
                currentPage++;

                onLoadMore(currentPage);

                loading = true;
            }
        }

        //Start loading
        public abstract void onLoadMore(int currentPage);
    }

    public static class RecyclerViewPositionHelper {

        final RecyclerView recyclerView;
        final RecyclerView.LayoutManager layoutManager;

        RecyclerViewPositionHelper(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            this.layoutManager = recyclerView.getLayoutManager();
        }

        public static RecyclerViewPositionHelper createHelper(RecyclerView recyclerView) {
            if (recyclerView == null) {
                throw new NullPointerException("Recycler View is null");
            }
            return new RecyclerViewPositionHelper(recyclerView);
        }

        /**
         * Returns the adapter item count.
         *
         * @return The total number on items in a layout manager
         */
        public int getItemCount() {
            return layoutManager == null ? 0 : layoutManager.getItemCount();
        }

        /**
         * Returns the adapter position of the first visible view. This position does not include
         * adapter changes that were dispatched after the last layout pass.
         *
         * @return The adapter position of the first visible item or {@link RecyclerView#NO_POSITION} if
         * there aren't any visible items.
         */
        public int findFirstVisibleItemPosition() {
            final View child = findOneVisibleChild(0, layoutManager.getChildCount(), false, true);
            return child == null ? RecyclerView.NO_POSITION : recyclerView.getChildAdapterPosition(child);
        }

        /**
         * Returns the adapter position of the first fully visible view. This position does not include
         * adapter changes that were dispatched after the last layout pass.
         *
         * @return The adapter position of the first fully visible item or
         * {@link RecyclerView#NO_POSITION} if there aren't any visible items.
         */
        public int findFirstCompletelyVisibleItemPosition() {
            final View child = findOneVisibleChild(0, layoutManager.getChildCount(), true, false);
            return child == null ? RecyclerView.NO_POSITION : recyclerView.getChildAdapterPosition(child);
        }

        /**
         * Returns the adapter position of the last visible view. This position does not include
         * adapter changes that were dispatched after the last layout pass.
         *
         * @return The adapter position of the last visible view or {@link RecyclerView#NO_POSITION} if
         * there aren't any visible items
         */
        public int findLastVisibleItemPosition() {
            final View child = findOneVisibleChild(layoutManager.getChildCount() - 1, -1, false, true);
            return child == null ? RecyclerView.NO_POSITION : recyclerView.getChildAdapterPosition(child);
        }

        /**
         * Returns the adapter position of the last fully visible view. This position does not include
         * adapter changes that were dispatched after the last layout pass.
         *
         * @return The adapter position of the last fully visible view or
         * {@link RecyclerView#NO_POSITION} if there aren't any visible items.
         */
        public int findLastCompletelyVisibleItemPosition() {
            final View child = findOneVisibleChild(layoutManager.getChildCount() - 1, -1, true, false);
            return child == null ? RecyclerView.NO_POSITION : recyclerView.getChildAdapterPosition(child);
        }

        View findOneVisibleChild(int fromIndex, int toIndex, boolean completelyVisible,
                                 boolean acceptPartiallyVisible) {
            OrientationHelper helper;
            if (layoutManager.canScrollVertically()) {
                helper = OrientationHelper.createVerticalHelper(layoutManager);
            } else {
                helper = OrientationHelper.createHorizontalHelper(layoutManager);
            }

            final int start = helper.getStartAfterPadding();
            final int end = helper.getEndAfterPadding();
            final int next = toIndex > fromIndex ? 1 : -1;
            View partiallyVisible = null;
            for (int i = fromIndex; i != toIndex; i += next) {
                final View child = layoutManager.getChildAt(i);
                final int childStart = helper.getDecoratedStart(child);
                final int childEnd = helper.getDecoratedEnd(child);
                if (childStart < end && childEnd > start) {
                    if (completelyVisible) {
                        if (childStart >= start && childEnd <= end) {
                            return child;
                        } else if (acceptPartiallyVisible && partiallyVisible == null) {
                            partiallyVisible = child;
                        }
                    } else {
                        return child;
                    }
                }
            }
            return partiallyVisible;
        }
    }

}