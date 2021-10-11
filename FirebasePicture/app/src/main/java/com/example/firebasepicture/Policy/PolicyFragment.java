package com.example.firebasepicture.Policy;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.firebasepicture.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class PolicyFragment extends Fragment {
    private Boolean btnPrivatePolicyStatus;
    private Boolean btnTermsAndPolicyStatus;

    private ImageButton btnPrivatePolicy;
    private ImageButton btnTermsAndPolicy;
    private TextView txtLinkPrivatePolicy;
    private TextView txtLinkTermsAndConditions;
    private Button btnPrivatePolicyAccept;
    private ChipNavigationBar bottomNav;


    public PolicyFragment(ChipNavigationBar bottomNav_) {
        btnPrivatePolicyStatus = btnTermsAndPolicyStatus = false;
        bottomNav = bottomNav_;

        bottomNav.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_policy, container, false);

        initComponents(v);

        return v;
    }

    @SuppressLint("WrongConstant")
    private void initComponents(View v){
        btnPrivatePolicy            = v.findViewById(R.id.imgCheckPrivatePolicy);
        btnTermsAndPolicy           = v.findViewById(R.id.imgCheckTermsAndPolicy);
        btnPrivatePolicyAccept      = v.findViewById(R.id.btnPrivatePolicyAccept);
        txtLinkPrivatePolicy        = v.findViewById(R.id.txtLinkPrivatePolicy);
        txtLinkTermsAndConditions   = v.findViewById(R.id.txtLinkTermsAndConditions);

        btnPrivatePolicy.setImageResource(R.drawable.check_off);
        btnTermsAndPolicy.setImageResource(R.drawable.check_off);
        btnPrivatePolicyAccept.setEnabled(false);

        btnPrivatePolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPrivatePolicyStatus = !btnPrivatePolicyStatus;
                btnPrivatePolicy.setImageResource(btnPrivatePolicyStatus ? R.drawable.check_on : R.drawable.check_off);
                btnPrivatePolicyAccept.setEnabled((btnPrivatePolicyStatus && btnTermsAndPolicyStatus) ? true : false);
            }
        });

        btnTermsAndPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTermsAndPolicyStatus = !btnTermsAndPolicyStatus;
                btnTermsAndPolicy.setImageResource(btnTermsAndPolicyStatus ? R.drawable.check_on : R.drawable.check_off);
                btnPrivatePolicyAccept.setEnabled((btnPrivatePolicyStatus && btnTermsAndPolicyStatus) ? true : false);
            }
        });

        btnPrivatePolicyAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNav.setVisibility(View.VISIBLE);
                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.fragment_container)).commit();
            }
        });

        txtLinkPrivatePolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().add(R.id.infoFragmentContainer, new PrivatePolicyFragment(btnPrivatePolicyAccept)).commit();
            }
        });

        txtLinkTermsAndConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}