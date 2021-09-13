package com.example.firebasepicture;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class SettingsPolitikaFragment extends Fragment {


    Button b1; //имя кнопки

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings_politika, container, false);

        b1 = v.findViewById(R.id.button25); //ищет по Id в фрагменте

        b1.setOnClickListener(new View.OnClickListener() { //по тапу
            @Override
            public void onClick(View v) {

                com.example.firebasepicture.SettingsFragment settingsFragment = new com.example.firebasepicture.SettingsFragment();
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.fragment_container, settingsFragment);
                transaction.commit();
            }
        });
        return v;
    }
}