package com.example.firebasepicture;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.int2.menu.proizv1.Proizv1Fragment;

public class MenuFragment2 extends Fragment {

    Button b1;
    Button b2;

    public MenuFragment2() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_menu2, container, false);

        b1 = v.findViewById(R.id.button12);
        b2 = v.findViewById(R.id.button7);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MenuFragment menuFragment = new MenuFragment();
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.fragment_container, menuFragment);
                transaction.commit();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Proizv1Fragment proizv1Fragment = new Proizv1Fragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, proizv1Fragment);
                transaction.commit();
            }
        });

        return v;

    }
}