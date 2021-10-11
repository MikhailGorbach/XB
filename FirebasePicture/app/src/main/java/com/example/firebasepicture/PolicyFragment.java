package com.example.firebasepicture;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class PolicyFragment extends Fragment {
    private Boolean btnPrivatePolicyStatus;
    private Boolean btnTermsAndPolicyStatus;

    private ImageButton btnPrivatePolicy;
    private ImageButton btnTermsAndPolicy;
    private Button btnPrivatePolicyAccept;

    public PolicyFragment() {
        btnPrivatePolicyStatus = btnTermsAndPolicyStatus = false;
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

    private void initComponents(View v){
        btnPrivatePolicy = v.findViewById(R.id.imgCheckPrivatePolicy);
        btnTermsAndPolicy = v.findViewById(R.id.imgCheckTermsAndPolicy);
        btnPrivatePolicyAccept = v.findViewById(R.id.btnPrivatePolicyAccept);

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
                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.fragment_container)).commit();
            }
        });
    }
}