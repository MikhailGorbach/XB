package com.example.firebasepicture.Menu.MenuFragmentSource;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.firebasepicture.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class FragmentForButton extends Fragment{
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter adapter;
    private Context context;
    private int name;
    private FragmentForButton fragment;

    public FragmentForButton(int name){
        this.name = name;
        fragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("debug", "FragmentForButton -> onCreateView");
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        initComponents(v);

        Log.d("debug", "FragmentForButton <- onCreateView");
        return v;
    }

    private void initComponents(View v){
        Log.d("debug", "FragmentForButton -> initComponents");
        context = v.getContext();
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
                 Glide.with(holder.img1.getContext()).load(model.getPic()).into(holder.img1);
                 holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         getFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentForCard(model, fragment)).commit();
                     }
                 });
             }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        Log.d("debug", "FragmentForButton <- initComponents");
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