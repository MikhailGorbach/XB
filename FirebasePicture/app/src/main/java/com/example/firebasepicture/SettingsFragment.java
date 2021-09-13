package com.example.firebasepicture;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class SettingsFragment extends Fragment {

    Button b1;
    Button b2;
    Button b3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        b1 = v.findViewById(R.id.button22);
        b2 = v.findViewById(R.id.button44);
        b3 = v.findViewById(R.id.button24);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                com.example.int2.settings.SettingsPolitikaFragment settingsPolitikaFragment = new com.example.int2.settings.SettingsPolitikaFragment();
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.fragment_container, settingsPolitikaFragment);
                transaction.commit();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                com.example.int2.settings.SettingsPravilaFragment settingsPravilaFragment = new com.example.int2.settings.SettingsPravilaFragment();
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.fragment_container, settingsPravilaFragment);
                transaction.commit();
            }
        });



        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                com.example.int2.settings.SettingsConnectionFragment settingsСonnectionFragment = new com.example.int2.settings.SettingsConnectionFragment();
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.fragment_container, settingsСonnectionFragment);
                transaction.commit();
            }
        });
        return v;
    }
}