package com.example.firebasepicture;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OneCardFragment extends Fragment {

    public static OneCardFragment newInstance(String param1, String param2) {
        OneCardFragment fragment = new OneCardFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_one_card, container, false);

        initComponents(v);

        return v;
    }

    void initComponents(View v){

    }
}