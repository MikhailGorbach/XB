package com.example.firebasepicture.Menu.Settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.firebasepicture.R;

public class SupportFragment extends Fragment {
    private Fragment fragment;

    public SupportFragment(Fragment fragment) {
        this.fragment = fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_support, container, false);
        initComponents(v);
        return v;
    }

    private void initComponents(View v){
        Fragment f = this;
        ( (ImageButton) v.findViewById(R.id.btnBackFragmentSupport) ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
        ( (LinearLayout) v.findViewById(R.id.linerUpCardFragmentSupport) ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("support@homevis.tech");
                intent.setData(data);
                startActivity(intent);
            }
        });
        ( (LinearLayout) v.findViewById(R.id.linerDownCardFragmentSupport) ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new TelegramFragment(f)).commit();
            }
        });
    }
}