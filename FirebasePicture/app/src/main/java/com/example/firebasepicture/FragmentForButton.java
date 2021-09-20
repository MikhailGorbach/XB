package com.example.firebasepicture;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

interface GetDataFromFragment {
    void GetData(String data);
}

public class FragmentForButton extends Fragment{
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        initComponents(v);

        return v;
    }

    private void initComponents(View v){
        recyclerView = v.findViewById(R.id.recview);

        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query = firebaseFirestore.collection("models");

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
                 Glide.with(holder.img1.getContext()).load(model.getPicture()).into(holder.img1);
             }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
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
            //relativeLayout = itemView.findViewById(R.id.backLayout);
        }
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
}