package com.example.firebasepicture;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class CustomGridAdapter extends RecyclerView.Adapter<CustomGridAdapter.ViewHolder> implements GetDataFromFragment {
    private static final String TAG = "FragmentDebag";

    private List<Integer> picturesList;     //id картинки
    private List<String> descriptionsList;  //описание модели
    private List<String> buttonsTextList;   //Значение возращаемое кнопкой

    private Context context;
    private LayoutInflater inflater;

    public CustomGridAdapter(Context ctx, List<Integer> picturesList, List<String> descriptionsList, List<String> buttonsList){
        this.buttonsTextList = buttonsList;
        this.descriptionsList = descriptionsList;
        this.picturesList = picturesList;

        inflater = LayoutInflater.from(ctx);
        context = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_grid_layout, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.des.setText(descriptionsList.get(position));
        holder.pic.setImageResource(picturesList.get(position));
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Click on btn");
                GetDataFromFragment listener = (GetDataFromFragment) v.getContext();
                listener.GetData(buttonsTextList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return descriptionsList.size();
    }

    @Override
    public void GetData(String data) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView des;
        ImageView pic;
        Button btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            initCoponents(itemView);
        }

        private void initCoponents(View v){
            pic = v.findViewById(R.id.idModel);
            des = v.findViewById(R.id.txtDescription);
            btn = v.findViewById(R.id.btnAccept);
        }
    }
}
