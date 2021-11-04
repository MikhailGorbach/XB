package com.example.firebasepicture.Menu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.firebasepicture.Policy.PrivatePolicyFragment;
import com.example.firebasepicture.R;
import com.example.firebasepicture.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private TextView txtLinkSettingPrivatePolicy;
    private TextView txtLinkSettingTermsAndConditions;
    private TextView txtLinkSettingFeedBack;
    private SettingsFragment ctx;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        initComponent();
        return binding.getRoot();
    }

    private void initComponent(){
        ctx = this;

        txtLinkSettingPrivatePolicy = binding.txtLinkSettingPrivatePolicy;
        txtLinkSettingTermsAndConditions = binding.txtLinkSettingTermsAndConditions;
        txtLinkSettingFeedBack = binding.txtLinkSettingFeedBack;

        txtLinkSettingPrivatePolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivatePolicyFragment f = new PrivatePolicyFragment(ctx,0);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
            }
        });

        txtLinkSettingTermsAndConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivatePolicyFragment f = new PrivatePolicyFragment(ctx,1);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
            }
        });

        txtLinkSettingFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new PrivatePolicyFragment(ctx,2)).commit();
            }
        });
    }
}