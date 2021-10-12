package com.example.firebasepicture.Policy;

import android.annotation.SuppressLint;
import android.app.UiModeManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.firebasepicture.Policy.Welcom.Welcome;
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
    private PolicyFragment ctx;

    public PolicyFragment(ChipNavigationBar bottomNav_) {
        btnPrivatePolicyStatus = btnTermsAndPolicyStatus = false;
        bottomNav = bottomNav_;
        ctx = this;
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

        btnPrivatePolicy.setImageResource((!btnPrivatePolicyStatus) ? R.drawable.check_off : R.drawable.check_on);
        btnTermsAndPolicy.setImageResource((!btnTermsAndPolicyStatus) ? R.drawable.check_off : R.drawable.check_on);

        if (btnPrivatePolicyStatus && btnTermsAndPolicyStatus) {
            btnPrivatePolicyAccept.setBackgroundColor(Color.BLUE);
            btnPrivatePolicyAccept.setEnabled(true);
        }else {
            btnPrivatePolicyAccept.setBackgroundColor(Color.GRAY);
            btnPrivatePolicyAccept.setEnabled(false);
        }

        btnPrivatePolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPrivatePolicyStatus = !btnPrivatePolicyStatus;
                btnPrivatePolicy.setImageResource(btnPrivatePolicyStatus ? R.drawable.check_on : R.drawable.check_off);
                btnPrivatePolicyAccept.setEnabled((btnPrivatePolicyStatus && btnTermsAndPolicyStatus) ? true : false);

                if (btnPrivatePolicyStatus && btnTermsAndPolicyStatus)
                    btnPrivatePolicyAccept.setBackgroundColor(Color.BLUE);
                else
                    btnPrivatePolicyAccept.setBackgroundColor(Color.GRAY);
            }
        });

        btnTermsAndPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTermsAndPolicyStatus = !btnTermsAndPolicyStatus;
                btnTermsAndPolicy.setImageResource(btnTermsAndPolicyStatus ? R.drawable.check_on : R.drawable.check_off);
                btnPrivatePolicyAccept.setEnabled((btnPrivatePolicyStatus && btnTermsAndPolicyStatus) ? true : false);

                if (btnPrivatePolicyStatus && btnTermsAndPolicyStatus)
                    btnPrivatePolicyAccept.setBackgroundColor(Color.BLUE);
                else
                    btnPrivatePolicyAccept.setBackgroundColor(Color.GRAY);
            }
        });

        btnPrivatePolicyAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bottomNav.setVisibility(View.VISIBLE);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Welcome()).commit();
                //remove(getFragmentManager().findFragmentById(R.id.fragment_container)).commit();
            }
        });

        txtLinkPrivatePolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivatePolicyFragment f = new PrivatePolicyFragment(ctx,0);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
            }
        });

        txtLinkTermsAndConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivatePolicyFragment f = new PrivatePolicyFragment(ctx,1);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
            }
        });
    }
}