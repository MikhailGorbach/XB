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

import com.example.firebasepicture.R;

import java.util.ArrayList;

public class CourseRVAdapter extends RecyclerView.Adapter<CourseRVAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<Model> coursesArrayList;
    private Context context;

    // creating constructor for our adapter class
    public CourseRVAdapter(ArrayList<Model> coursesArrayList, Context context) {
        this.coursesArrayList = coursesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new AdapterToCards(LayoutInflater.from(context).inflate(R.layout.course_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterToCards holder, int position) {
        // setting data to our text views from our modal class.
        Model model = coursesArrayList.get(position);
        holder.courseNameTV.setText(model.getCourseName());
        holder.courseDurationTV.setText(model.getCourseDuration());
        holder.courseDescTV.setText(model.getCourseDescription());
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return coursesArrayList.size();
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
}