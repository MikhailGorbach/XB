package com.example.firebasepicture.Menu.Settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firebasepicture.R;
import com.example.firebasepicture.Utility.OnBackPressedListener;
import com.example.firebasepicture.databinding.FragmentAboutUsBinding;

public class AboutUsFragment extends Fragment implements OnBackPressedListener {
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

    @Override
    public void onBackPressed() {
        Log.d("debug", "Нажатие на кнопку назад в " + this.toString());
        if(fragment != null)
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        else
            getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.fragment_container)).commit();


    }
}