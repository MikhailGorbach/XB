package com.example.firebasepicture.Menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.firebasepicture.R;

public class InfoFragment extends Fragment {
    private Fragment fragment;
    public InfoFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info, container, false);
        initComponent(v);
        return v;
    }

    private void initComponent(View v){
        View.OnClickListener onPrivatePolicyClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        View.OnClickListener onTermsClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        View.OnClickListener onAboutClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        View.OnClickListener onContactClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        View.OnClickListener onAppClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };

        ( (ImageButton) v.findViewById(R.id.btnBackFragmentInfo) ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });

        ( (LinearLayout) v.findViewById(R.id.linerServiceAndHelpFragmentInfo)).setOnClickListener(onPrivatePolicyClick);
        ( (ImageButton) v.findViewById(R.id.btnBack1ImageFragmentInfo)).setOnClickListener(onPrivatePolicyClick);
        ( (LinearLayout) v.findViewById(R.id.linerTermsFragmentInfo)).setOnClickListener(onTermsClick);
        ( (ImageButton) v.findViewById(R.id.btnBack2ImageFragmentInfo)).setOnClickListener(onTermsClick);
        ( (LinearLayout) v.findViewById(R.id.linerAboutFragmentInfo)).setOnClickListener(onAboutClick);
        ( (ImageButton) v.findViewById(R.id.btnBack3ImageFragmentInfo)).setOnClickListener(onAboutClick);
        ( (LinearLayout) v.findViewById(R.id.linerContactFragmentInfo)).setOnClickListener(onContactClick);
        ( (ImageButton) v.findViewById(R.id.btnBack4ImageFragmentInfo)).setOnClickListener(onContactClick);
        ( (LinearLayout) v.findViewById(R.id.linerAppFragmentInfo)).setOnClickListener(onAppClick);
        ( (ImageButton) v.findViewById(R.id.btnBack5ImageFragmentInfo)).setOnClickListener(onAppClick);
    }
}