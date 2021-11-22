package com.example.firebasepicture.Menu.Settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firebasepicture.R;
import com.example.firebasepicture.databinding.FragmentAboutUsBinding;

public class AboutUsFragment extends Fragment {
    private Fragment fragment;
    private FragmentAboutUsBinding binding;
    public AboutUsFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAboutUsBinding.inflate(inflater, container, false);
        initComponents();
        return binding.getRoot();
    }

    private void initComponents(){
        binding.btnBackFragmentAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
    }
}