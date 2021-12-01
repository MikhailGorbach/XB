package com.example.firebasepicture;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AdapterToCards extends FirestoreRecyclerAdapter<Model, AdapterToCards.ViewHolderToCards> {

    public AdapterToCards(@NonNull FirestoreRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolderToCards holder, int position, @NonNull Model model) {
        holder.txtTitle.setText(model.getName());
        holder.txtPrice.setText(model.getPrice());
        Glide.with(holder.img1.getContext()).load(model.photo).into(holder.img1);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OneCardFragment());
            }
        });
    }

    @NonNull
    @Override
    public ViewHolderToCards onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_design, parent,false);
        return new ViewHolderToCards(view);
    }

    public class ViewHolderToCards extends RecyclerView.ViewHolder {
        public ImageView        img1;
        public TextView         txtTitle;
        public TextView         txtPrice;
        public RelativeLayout   relativeLayout;

        public ViewHolderToCards(@NonNull View itemView) {
            super(itemView);

            img1 = itemView.findViewById(R.id.imgOnCard);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtPrice = itemView.findViewById(R.id.txtPrice);
        }

    }
}
