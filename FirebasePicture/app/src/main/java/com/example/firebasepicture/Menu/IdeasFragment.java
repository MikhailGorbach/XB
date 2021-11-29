package com.example.firebasepicture.Menu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        links.add("https://homevis.tech/static/users/img/1.png");
        links.add("https://homevis.tech/static/users/img/2.png");
        links.add("https://homevis.tech/static/users/img/3.png");

        UsersAdapter adapter = new UsersAdapter(links, this);
        binding.listMyList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.listMyList.setAdapter(adapter);
    }

}