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

    private ImageButton btnBack;
    private ScrollView scrlView;
    private Fragment fragment;

    private FragmentPrivatePolicyBinding binding;
    private TextView txtPrivatePolicyName_1;
    private TextView txtPrivatePolicyName_2;
    private TextView txtPrivatePolicy;
    private TextView txtConnectInfo;

    private int tip;

    public PrivatePolicyFragment(Fragment fragment, int tip){
        this.fragment = fragment;
        this.tip = tip;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPrivatePolicyBinding.inflate(inflater,container,false);
        initComponents();
        return binding.getRoot();
    }

    private void initComponents(){
        txtPrivatePolicy = binding.txtPrivatePolicy;
        txtPrivatePolicyName_1 = binding.txtPrivatePolicyNameFirst;
        txtPrivatePolicyName_2 = binding.txtPrivatePolicyNameSecond;
        txtConnectInfo = binding.txtConnectInfo;
        btnBack = binding.btnPrivatePolicyBack;
        scrlView = binding.scrlViewOfMainText;

        scrlView.setVerticalScrollBarEnabled(false);
        scrlView.setHorizontalScrollBarEnabled(false);

        txtConnectInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Нажатие на ссылку
            }
        });

        type(tip);

        btnBack.setImageResource(R.drawable.bbback);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
    }

    private void type(int i){
        txtConnectInfo.setEnabled(false);
        txtConnectInfo.setVisibility(View.INVISIBLE);

        switch (i){
            case 0:
                txtPrivatePolicyName_1.setText(R.string.PrivatePolicyName_1);
                txtPrivatePolicyName_2.setText(R.string.PrivatePolicyName_2);
                txtPrivatePolicy.setText(R.string.PrivatePolicyText);
                break;
            case 1:
                txtPrivatePolicyName_1.setText(R.string.TermsAndConditionsName_1);
                txtPrivatePolicyName_2.setText(R.string.TermsAndConditionsName_2);
                txtPrivatePolicy.setText(R.string.TermsAndConditionsText);
                break;
            case 2:
                txtPrivatePolicyName_1.setText(R.string.ConnectName_1);
                txtPrivatePolicyName_2.setText(R.string.ConnectName_2);
                txtPrivatePolicy.setText(R.string.ConnectText);
                txtConnectInfo.setEnabled(true);
                txtConnectInfo.setVisibility(View.VISIBLE);
                break;
        }
    }
}