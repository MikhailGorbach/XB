package com.example.firebasepicture;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class SettingsPravilaFragment extends Fragment {


    Button b1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings_pravila, container, false);

        b1 = v.findViewById(R.id.button26);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                com.example.int2.settings.SettingsFragment settingsFragment = new com.example.int2.settings.SettingsFragment();
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.fragment_container, settingsFragment);
                transaction.commit();
            }
        });
        return v;
    }
}