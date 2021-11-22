package com.example.firebasepicture.Menu.MenuFragmentSource;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.firebasepicture.Model;
import com.example.firebasepicture.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
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
    private FragmentForButton fragment;
    private String name;
    private String rname;
    private Query query;
    private Comparator<DocumentSnapshot> comparator;

    public FragmentForButton(String name){
        this.rname = name;
        fragment = this;
        modelList = new ArrayList<>();

        switchName();
    }

    private void switchName(){
        switch (rname) {
            case "Диваны":
                name = "sofa";
                break;
            case "Кресла":
                name = "armchair";
                break;
            case "Стулья":
                name = "chair";
                break;
            case "Кровати":
                name = "bed";
                break;
            case "Столы":
                name = "table";
                break;
            case "Двери":
                name = "door";
                break;
            case "Тумбы":
                name = "pedestal";
                break;
            case "Комоды":
                name = "dresser";
                break;
            case "Банкетки":
                name = "banquette";
                break;
            case "Пуфы":
                name = "poof";
                break;
            case "Шкафы":
                name = "wardrobe";
                break;
            case "Кушетки":
                name = "couch";
                break;
            case "Стеллажи":
                name = "shelf";
                break;
            case "Настенное освещение":
                name = "lightWall";
                break;
            case "Напольное освещение":
                name = "lightFloor";
                break;
            case "Люстры":
                name = "chandelier";
                break;
        }
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

        ((TextView) v.findViewById(R.id.txtCategoryList)).setText(rname);
        ((ImageButton) v.findViewById(R.id.imgBtnSortFragmentList)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });

        recyclerView = v.findViewById(R.id.recview);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        loadMax();

        if(rvAdapter.getItemCount() == 0)
            loadQuery();

        recyclerView.setAdapter(rvAdapter);
    }

    private void loadQuery(){
        query = firebaseFirestore
                .collection("models")
                .whereEqualTo("category",name);

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    //Здесь вылетала ошибка
                    Context context = fragment.getContext();

                    if (context != null)
                        Toast.makeText( context, "No data found in Database", Toast.LENGTH_SHORT).show();

                    return;
                }

                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                list.sort(comparator);
                if(list.isEmpty()){
                    Toast.makeText(fragment.getContext(), "Error with download from Database", Toast.LENGTH_SHORT).show();
                    return;
                }

                int max = rvAdapter.getItemCount(), i = 0, j = 0;
                for (DocumentSnapshot d : list) {
                    if(i++ < max) continue;
                    if(j++ >= limit) break;

                    modelList.add(d.toObject(Model.class));
                }

                rvAdapter.notifyDataSetChanged();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(fragment.getContext(), "Fail get data from Database.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMin(){
        comparator = new Comparator<DocumentSnapshot>() {
            @Override
            public int compare(DocumentSnapshot o1, DocumentSnapshot o2) {
                return Integer.parseInt(o1.toObject(Model.class).getPrice()) >
                       Integer.parseInt(o2.toObject(Model.class).getPrice()) ? 1 :
                        (Integer.parseInt(o1.toObject(Model.class).getPrice()) ==
                         Integer.parseInt(o2.toObject(Model.class).getPrice())) ? 0 : -1;
            }
        };
        loadQuery();
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(int currentPage) {
                loadQuery();
            }
        });
    }

    private void loadMax(){
        comparator = new Comparator<DocumentSnapshot>() {
            @Override
            public int compare(DocumentSnapshot o1, DocumentSnapshot o2) {
                return Integer.parseInt(o1.toObject(Model.class).getPrice()) >
                        Integer.parseInt(o2.toObject(Model.class).getPrice()) ? -1 :
                        (Integer.parseInt(o1.toObject(Model.class).getPrice()) ==
                         Integer.parseInt(o2.toObject(Model.class).getPrice())) ? 0 : 1;
            }
        };
        loadQuery();
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(int currentPage) {
                loadQuery();
            }
        });
    }

    private void showBottomSheetDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this.getContext(),R.style.CustomBottomSheetDialogTheme);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);

        bottomSheetDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        ((Button)bottomSheetDialog.getWindow().findViewById(R.id.btnCancelBottomSheetDialog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        ((Button)bottomSheetDialog.getWindow().findViewById(R.id.btnSortMaxBottomSheetDialog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvAdapter.remove();
                bottomSheetDialog.dismiss();
                loadMax();
            }
        });
        ((Button)bottomSheetDialog.getWindow().findViewById(R.id.btnSortMinBottomSheetDialog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvAdapter.remove();
                bottomSheetDialog.dismiss();
                loadMin();
            }
        });

        bottomSheetDialog.show();
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

        public void remove(){
            while(modelsList.size() > 0) modelsList.remove(0);
            notifyDataSetChanged();
        }

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

            holder.txtPrice.setText(model.getPrice() + " ₽");
            holder.txtTitle.setText(model.getName());
            holder.txtCompany.setText(model.getCompany());
            Glide.with(holder.img1.getContext()).load(model.getPhoto()).into(holder.img1);
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
            public TextView txtCompany;
            public RelativeLayout relativeLayout;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                img1 = itemView.findViewById(R.id.imgOnCard);
                txtTitle = itemView.findViewById(R.id.txtTitle);
                txtPrice = itemView.findViewById(R.id.txtPrice);
                txtCompany = itemView.findViewById(R.id.txtCompany);
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