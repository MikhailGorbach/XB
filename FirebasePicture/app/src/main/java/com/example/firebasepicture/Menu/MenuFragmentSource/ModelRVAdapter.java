package com.example.firebasepicture.Menu.MenuFragmentSource;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebasepicture.R;

import java.util.ArrayList;

public class ModelRVAdapter extends RecyclerView.Adapter<ModelRVAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<Model> modelsList;
    private Context context;

    // creating constructor for our adapter class
    public ModelRVAdapter(ArrayList<Model> coursesArrayList, Context context) {
        this.modelsList = coursesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ModelRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_disign, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ModelRVAdapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        Model model = modelsList.get(position);

        holder.txtTitle.setText(model.getTitle());
        holder.txtPrice.setText(model.getPrice());
        Glide.with(holder.img1.getContext()).load(model.getPic()).into(holder.img1);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentForCard(model, fragment)).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
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
        }
    }
}
