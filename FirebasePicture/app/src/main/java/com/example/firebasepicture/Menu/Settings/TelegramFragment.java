package com.example.firebasepicture.Menu.Settings;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.firebasepicture.R;
import com.example.firebasepicture.Utility.OnBackPressedListener;
import com.example.firebasepicture.databinding.FragmentTelegramBinding;

import java.util.ArrayList;
import java.util.List;

public class TelegramFragment extends Fragment implements OnBackPressedListener {
    private Fragment fragment;
    private FragmentTelegramBinding binding;
    public TelegramFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTelegramBinding.inflate(inflater, container, false);
        initComponents();
        return binding.getRoot();
    }

    private void initComponents(){
        binding.btnBackFragmentSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
        binding.btnBotFragmentTelegram2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/homevis_bot"));
                startActivity(browserIntent);
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