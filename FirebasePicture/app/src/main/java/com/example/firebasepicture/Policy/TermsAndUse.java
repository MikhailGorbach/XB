package com.example.firebasepicture.Policy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firebasepicture.R;
import com.example.firebasepicture.databinding.FragmentTermsAndUseBinding;

public class TermsAndUse extends Fragment {
    private Fragment fragment;
    private FragmentTermsAndUseBinding binding;
    public TermsAndUse(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTermsAndUseBinding.inflate(inflater, container, false);
        initComponent();
        return binding.getRoot();
    }

    private void initComponent(){
        binding.btnBackFragmentTermsAndUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
    }

}