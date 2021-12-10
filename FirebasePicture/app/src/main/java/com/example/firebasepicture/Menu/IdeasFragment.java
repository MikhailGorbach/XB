package com.example.firebasepicture.Menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebasepicture.Menu.MenuFragmentSource.FragmentForButton;
import com.example.firebasepicture.Policy.Welcome.Adapter.PageAdapter;
import com.example.firebasepicture.R;
import com.example.firebasepicture.databinding.FragmentIdeasBinding;
import com.example.firebasepicture.databinding.FragmentPageBinding;
import com.example.firebasepicture.databinding.PageAdapterIdeasCardBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class IdeasFragment extends Fragment {
    private FragmentIdeasBinding binding;
    private ArrayList<String> links;

    public IdeasFragment(){
        links = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIdeasBinding.inflate(inflater, container, false);
        initComponents();
        return binding.getRoot();
    }

    private void initComponents(){
        links.add("https://homevis.tech/static/sales/sale1.png");
        links.add("https://homevis.tech/static/sales/sale2.png");
        links.add("https://homevis.tech/static/sales/sale3.png");

        MePicAdapter adapter = new MePicAdapter(links, this, this.getContext());
        LinearLayoutManager lm = new LinearLayoutManager(requireContext());
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.listMyList.setAdapter(adapter);
        binding.listMyList.setLayoutManager(lm);
    }

    class MePicAdapter extends RecyclerView.Adapter<MePicAdapter.MePicAdapterViewHolder>{

        public Fragment fragment;
        private ArrayList<String> ulrList;
        private Context mContext;

        public MePicAdapter(ArrayList<String> urlList, Fragment fragment, Context mContext) {
            this.ulrList = urlList;
            this.fragment = fragment;
            this.mContext = mContext;
        }

        @NonNull
        @Override
        public MePicAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MePicAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.page_adapter_ideas_card, parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull MePicAdapterViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Glide.with(mContext).load(ulrList.get(position)).into(holder.image);
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = "";
                    switch (ulrList.get(position)){
                        case "https://homevis.tech/static/sales/sale1.png":
                            name = "sale1";
                            break;
                        case "https://homevis.tech/static/sales/sale2.png":
                            name = "sale2";
                            break;
                        case "https://homevis.tech/static/sales/sale3.png":
                            name = "sale3";
                            break;
                    }
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentForButton(name, fragment,true) ).commit();
                }
            });

        }

        @Override
        public int getItemCount() {
            return ulrList.size();
        }

        public class MePicAdapterViewHolder extends RecyclerView.ViewHolder {
            public ImageView image;

            public MePicAdapterViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.pictureImage);
                image.setClipToOutline(true);
            }
        }

    }

}