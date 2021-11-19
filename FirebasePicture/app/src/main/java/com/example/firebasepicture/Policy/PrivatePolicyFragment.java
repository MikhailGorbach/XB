package com.example.firebasepicture.Policy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.firebasepicture.R;
import com.example.firebasepicture.databinding.FragmentPrivatePolicyBinding;

public class PrivatePolicyFragment extends Fragment {

    private Fragment fragment;
    private FragmentPrivatePolicyBinding binding;

    public PrivatePolicyFragment(Fragment fragment){
        this.fragment = fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPrivatePolicyBinding.inflate(inflater,container,false);
        initComponents();
        return binding.getRoot();
    }

    private void initComponents(){
        binding.btnBackFragmentPrivatePolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
    }

}