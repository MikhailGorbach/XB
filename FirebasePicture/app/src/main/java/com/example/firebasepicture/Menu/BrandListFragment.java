package com.example.firebasepicture.Menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.firebasepicture.R;

public class BrandListFragment extends Fragment {
    Fragment back;

    public BrandListFragment(Fragment back) {
        this.back = back;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_brand_list, container, false);
        initComponents(v);
        return v;
    }

    void initComponents(View v){
        ( (LinearLayout) v.findViewById(R.id.layoutBackFragmentBrandList)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, back).commit();
            }
        });
    }

}