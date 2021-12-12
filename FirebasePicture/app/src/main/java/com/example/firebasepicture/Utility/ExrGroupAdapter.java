package com.example.firebasepicture.Utility;

import android.animation.ValueAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasepicture.Menu.Settings.ElementA;
import com.example.firebasepicture.R;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;

import ayalma.ir.expandablerecyclerview.ExpandableRecyclerView;

public abstract class ExrGroupAdapter extends ExpandableRecyclerView.Adapter<ExrGroupAdapter.ChildViewHolder, ExrGroupAdapter.MainViewHolder, String, String> {
    public ArrayList<ElementA> ElementList;
    public ArrayList<String> TitleList;

    public ExrGroupAdapter(ArrayList<String> TitleList,ArrayList<ElementA> ElementList) {
        this.ElementList = ElementList;
        this.TitleList = TitleList;
    }

    @Override
    public int getGroupItemCount() {
        return TitleList.size() - 1;
    }

    @Override
    public String getGroupItem(int i) {
        return TitleList.get(i);
    }

    @Override
    public int getChildItemCount(int i) {
        return 1;
    }

    @Override
    public int getChildItemViewType(int i, int i1) {
        return 1;
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder holder, int group, int position) {
        super.onBindChildViewHolder(holder, group, position);
        holder.name.setText(getChildItem(group, position));
    }

    @Override
    public void onBindGroupViewHolder(MainViewHolder holder, int group) {
        super.onBindGroupViewHolder(holder, group);
        holder.title.setText(TitleList.get(group));
        if(group == getGroupItemCount())
            holder.setLine(View.INVISIBLE,false);
    }

    //Класс который представляет собой подтекст
    public class ChildViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public ChildViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_name);
        }
    }

    @Override
    protected ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_item, parent, false);
        return new ChildViewHolder(rootView);
    }

    //Класс который представляет собой надтекст
    public class MainViewHolder extends ExpandableRecyclerView.GroupViewHolder {
        public ImageView img;
        public TextView title;
        private boolean expanded;
        private View line;

        public MainViewHolder(View itemView) {
            super(itemView);

            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            title = (TextView) itemView.findViewById(R.id.title_exr_group);
            img = (ImageView) itemView.findViewById(R.id.img_exr_group);
            line = (View) itemView.findViewById(R.id.line_exr_group);

        }

        @Override
        public void expand() {
            ValueAnimator animatorPic = ValueAnimator.ofFloat(0, 1);
            animatorPic.setInterpolator(new DecelerateInterpolator());
            animatorPic.setDuration(200);
            animatorPic.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    ViewHelper.setRotation(img, 180 * (float) (animation.getAnimatedValue()));
                    ViewHelper.setAlpha(line, 255 - 255 * (float) (animation.getAnimatedValue()));
                    img.postInvalidate();
                    line.postInvalidate();
                }
            });
            animatorPic.start();
            expanded = true;
        }

        @Override
        public void collapse() {
            com.nineoldandroids.animation.ValueAnimator animator = com.nineoldandroids.animation.ValueAnimator.ofFloat(1, 0);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.setDuration(200);
            animator.addUpdateListener(new com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(com.nineoldandroids.animation.ValueAnimator animation) {
                    ViewHelper.setRotation(img, 180 * (float) (animation.getAnimatedValue()));
                    ViewHelper.setAlpha(line, 255 - 255 * (float) (animation.getAnimatedValue()));
                    img.postInvalidate();
                }
            });
            animator.start();
            expanded = false;
        }

        @Override
        public void setExpanded(boolean expanded) {
            ViewHelper.setRotation(img, expanded ? 180 : 0);
            ViewHelper.setAlpha(line, expanded ? 0 : 255);
            this.expanded = expanded;
        }

        @Override
        public boolean isExpanded() {
            return expanded;
        }

        public void setLine(int visibleLine, boolean enableLine){
            line.setVisibility(visibleLine);
            line.setEnabled(enableLine);
        }
    }

    @Override
    protected MainViewHolder onCreateGroupViewHolder(ViewGroup parent) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.exr_group_two, parent, false);
        return new MainViewHolder(rootView);
    }

}
