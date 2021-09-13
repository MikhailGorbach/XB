package com.example.firebasepicture;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.int2.FurnitureItem;

import java.util.ArrayList;


public class IdeasFragment extends Fragment {

    private ArrayList<FurnitureItem> furnitureItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ideas, container, false);



    }
}