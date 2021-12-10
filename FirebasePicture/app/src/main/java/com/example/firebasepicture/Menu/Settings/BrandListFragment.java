package com.example.firebasepicture.Menu.Settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.firebasepicture.R;
import com.example.firebasepicture.databinding.FragmentBrandListBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BrandListFragment extends Fragment {
    private Fragment back;
    private FragmentBrandListBinding binding;
    private FirebaseFirestore firebaseFirestore;
    private SellerRVAdapter adapter;
    private ArrayList<Seller> arrayList;
    private BrandListFragment f;

    public BrandListFragment(Fragment back) {
        this.back = back;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBrandListBinding.inflate(inflater, container, false);
        initComponents();
        return binding.getRoot();
    }

    void initComponents(){

        arrayList = new ArrayList<Seller>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        f = this;

        binding.btnBackFragmentBrandList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, back).commit();
            }
        });
        adapter = new SellerRVAdapter(arrayList, f);
        load();

        binding.recyclerViewList.setLayoutManager(new GridLayoutManager(this.getContext(), 1));
        binding.recyclerViewList.setAdapter(adapter);
    }

    private void load(){
        Query query = firebaseFirestore
                .collection("sellers");

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    Log.d("debug", "Empty");

                    if (f.getContext() != null)
                        Toast.makeText( f.getContext(), "No data found in Database", Toast.LENGTH_SHORT).show();

                    return;
                }

                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                if(list.isEmpty()){
                    Log.d("debug", "EmptyList");
                    Toast.makeText(f.getContext(), "Error with download from Database", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (DocumentSnapshot d : list) {
                    arrayList.add(d.toObject(Seller.class));
                }

                Log.d("debug", "All Ok!");
                adapter.notifyDataSetChanged();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("debug", "Fail get data from Database.");
                if (f.getContext() != null)
                    Toast.makeText(f.getContext(), "Fail get data from Database.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class SellerRVAdapter extends RecyclerView.Adapter<SellerRVAdapter.ViewHolder>{
        private ArrayList<Seller> cardsList;
        private BrandListFragment fragment;

        public SellerRVAdapter(ArrayList<Seller> coursesArrayList, BrandListFragment fragment) {
            this.cardsList = coursesArrayList;
            this.fragment = fragment;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_card, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Seller card = cardsList.get(position);

            Glide.with(holder.img3.getContext()).load(card.getLogo()).into(holder.img3);
            holder.txtName2.setText(card.getName());
        }

        @Override
        public int getItemCount() {
            return cardsList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView img3;
            public TextView txtName2;
            public ConstraintLayout layout;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                img3 = itemView.findViewById(R.id.img3);
                img3.setClipToOutline(true);
                txtName2 = itemView.findViewById(R.id.name2);
                layout = itemView.findViewById(R.id.constrain);
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }
    }

}