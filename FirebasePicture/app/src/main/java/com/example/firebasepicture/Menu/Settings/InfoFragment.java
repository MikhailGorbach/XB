package com.example.firebasepicture.Menu.Settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firebasepicture.Policy.PrivatePolicyFragment;
import com.example.firebasepicture.Policy.TermsAndUseFragment;
import com.example.firebasepicture.R;
import com.example.firebasepicture.databinding.FragmentInfoBinding;

public class InfoFragment extends Fragment {
    private Fragment fragment;
    private FragmentInfoBinding binding;
    public InfoFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        initComponent();
        return binding.getRoot();
    }

    private void initComponent(){
        InfoFragment f = this;
        View.OnClickListener onPrivatePolicyClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new PrivatePolicyFragment(f)).commit();
            }
        };
        View.OnClickListener onTermsClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new TermsAndUseFragment(f)).commit();
            }
        };
        View.OnClickListener onAboutClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutUsFragment(f)).commit();
            }
        };
        View.OnClickListener onContactClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ContactFragment(f)).commit();
            }
        };
        View.OnClickListener onAppClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };

        binding.btnBackFragmentInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });

        binding.linerServiceAndHelpFragmentInfo.setOnClickListener(onPrivatePolicyClick);
        binding.btnBack1ImageFragmentInfo.setOnClickListener(onPrivatePolicyClick);

        binding.linerTermsFragmentInfo.setOnClickListener(onTermsClick);
        binding.btnBack2ImageFragmentInfo.setOnClickListener(onTermsClick);

        binding.linerAboutFragmentInfo.setOnClickListener(onAboutClick);
        binding.btnBack3ImageFragmentInfo.setOnClickListener(onAboutClick);

        binding.linerContactFragmentInfo.setOnClickListener(onContactClick);
        binding.btnBack4ImageFragmentInfo.setOnClickListener(onContactClick);

        binding.linerAppFragmentInfo.setOnClickListener(onAppClick);
        binding.btnBack5ImageFragmentInfo.setOnClickListener(onAppClick);
    }
}