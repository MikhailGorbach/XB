package com.example.firebasepicture.Menu.Settings;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firebasepicture.R;
import com.example.firebasepicture.databinding.FragmentAboutAppBinding;

public class AboutApp extends Fragment {
    private Fragment back;
    private FragmentAboutAppBinding binding;

    public AboutApp(Fragment back) {
        this.back = back;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAboutAppBinding.inflate(inflater, container, false);
        initComponents();
        return binding.getRoot();
    }

    private void initComponents(){
        binding.btnBackFragmentAboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,back).commit();
            }
        });
        try {
            PackageManager pm = getActivity().getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(getActivity().getPackageName(), 0);
            binding.textView9.setText(packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException ex) {
            ex.printStackTrace();
        }
        binding.textView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/homevis_bot"));
                startActivity(browserIntent);

            }
        });
    }
}