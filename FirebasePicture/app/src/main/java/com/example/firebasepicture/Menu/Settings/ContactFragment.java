package com.example.firebasepicture.Menu.Settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firebasepicture.R;
import com.example.firebasepicture.databinding.FragmentContactBinding;

public class ContactFragment extends Fragment {
    private Fragment fragment;
    private FragmentContactBinding binding;
    public ContactFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactBinding.inflate(inflater, container, false);
        initComponents();
        return binding.getRoot();
    }

    private void initComponents(){
        binding.btnBackFragmentContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
    }
}