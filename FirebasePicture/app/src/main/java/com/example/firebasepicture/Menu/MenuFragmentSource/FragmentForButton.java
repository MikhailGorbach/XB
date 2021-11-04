package com.example.firebasepicture.Menu.MenuFragmentSource;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.firebasepicture.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FragmentForButton extends Fragment{
    private final int limit = 16;

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerView;
    private ArrayList<Model> modelList;
    private ModelRVAdapter rvAdapter;
    private DocumentSnapshot lastVisible;
    private FragmentForButton fragment;
    private String name;
    private Query query;

    public FragmentForButton(String name){
        this.name = name;
        fragment = this;
        modelList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        initComponents(v);
        return v;
    }

    private void initComponents(View v){
        firebaseFirestore = FirebaseFirestore.getInstance();
        rvAdapter = new ModelRVAdapter(modelList,fragment);

        recyclerView = v.findViewById(R.id.recview);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(int currentPage) {
                Query nextQuery = firebaseFirestore.collection("models")
                        .startAfter(lastVisible)
                        .whereEqualTo("category",name)
                        .limit(limit);

                nextQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Model c = d.toObject(Model.class);
                                modelList.add(c);
                                lastVisible = d;
                            }
                            rvAdapter.notifyDataSetChanged();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });

        query = firebaseFirestore
                .collection("models")
                .whereEqualTo("category",name)
                .limit(limit);

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        Model c = d.toObject(Model.class);
                        modelList.add(c);
                        lastVisible = d;
                    }
                    modelList.sort(new Comparator<Model>() {
                        @Override
                        public int compare(Model o1, Model o2) {
                            return o1.price.compareTo(o2.price);
                        }
                    });
                    rvAdapter.notifyDataSetChanged();
                } else
                    Toast.makeText(fragment.getContext(), "No data found in Database", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(fragment.getContext(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(rvAdapter);
    }

    public abstract static class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
        public static String TAG = "EndlessScrollListener";

        private int previousTotal = 0;      // The total number of items in the dataset after the last load
        private boolean loading = true;     // True if we are still waiting for the last set of data to load.
        private int visibleThreshold = 0;   //Минимальное кол. элементов, вниз, после которых начётся загрузка
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
                currentPage++;
                onLoadMore(currentPage);
                loading = true;
            }
        }

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

        public int getItemCount() {
            return layoutManager == null ? 0 : layoutManager.getItemCount();
        }

        public int findFirstVisibleItemPosition() {
            final View child = findOneVisibleChild(0, layoutManager.getChildCount(), false, true);
            return child == null ? RecyclerView.NO_POSITION : recyclerView.getChildAdapterPosition(child);
        }

        public int findFirstCompletelyVisibleItemPosition() {
            final View child = findOneVisibleChild(0, layoutManager.getChildCount(), true, false);
            return child == null ? RecyclerView.NO_POSITION : recyclerView.getChildAdapterPosition(child);
        }

        public int findLastVisibleItemPosition() {
            final View child = findOneVisibleChild(layoutManager.getChildCount() - 1, -1, false, true);
            return child == null ? RecyclerView.NO_POSITION : recyclerView.getChildAdapterPosition(child);
        }

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

    public class ModelRVAdapter extends RecyclerView.Adapter<ModelRVAdapter.ViewHolder> {
        private ArrayList<Model> modelsList;
        private FragmentForButton fragment;

        public ModelRVAdapter(ArrayList<Model> coursesArrayList, FragmentForButton fragment) {
            this.modelsList = coursesArrayList;
            this.fragment = fragment;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_design, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Model model = modelsList.get(position);

            holder.txtPrice.setText(model.getPrice());
            Glide.with(holder.img1.getContext()).load(model.getPic()).into(holder.img1);
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentForCard(model, fragment)).commit();
                }
            });

        }

        @Override
        public int getItemCount() {
            return modelsList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView img1;
            public TextView txtTitle;
            public TextView txtPrice;
            public RelativeLayout relativeLayout;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                img1 = itemView.findViewById(R.id.img1);
                txtTitle = itemView.findViewById(R.id.txtTitle);
                txtPrice = itemView.findViewById(R.id.txtPrice);
                relativeLayout = itemView.findViewById(R.id.backLayout);

                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }
    }
}