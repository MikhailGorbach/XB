package com.example.firebasepicture.Policy;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.firebasepicture.Activity.MainActivity;
import com.example.firebasepicture.Policy.Welcome.WelcomeFragment;
import com.example.firebasepicture.R;
import com.example.firebasepicture.databinding.FragmentPolicyBinding;

public class PolicyFragment extends Fragment {

    private Boolean btnPrivatePolicyStatus;
    private Boolean btnTermsAndPolicyStatus;
    private FragmentPolicyBinding binding;
    private ImageButton btnPrivatePolicy;
    private ImageButton btnTermsAndPolicy;
    private TextView txtLinkPrivatePolicy;
    private TextView txtLinkTermsAndConditions;
    private Button btnPrivatePolicyAccept;
    private PolicyFragment ctx;

    public PolicyFragment() {
        MainActivity.bottomNav.setEnabled(false);
        MainActivity.bottomNav.setVisibility(View.INVISIBLE);
        btnPrivatePolicyStatus = btnTermsAndPolicyStatus = false;
        ctx = this;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPolicyBinding.inflate(inflater, container, false);
        initComponents();
        return binding.getRoot();
    }

    private void initComponents() {
        btnPrivatePolicy = binding.imgCheckPrivatePolicy;
        btnTermsAndPolicy = binding.imgCheckTermsAndPolicy;
        btnPrivatePolicyAccept = binding.btnPrivatePolicyAccept;
        txtLinkPrivatePolicy = binding.txtLinkPrivatePolicy;
        txtLinkTermsAndConditions = binding.txtLinkTermsAndConditions;

        btnPrivatePolicy.setImageResource((!btnPrivatePolicyStatus) ? R.drawable.check_out : R.drawable.check_in);
        btnTermsAndPolicy.setImageResource((!btnTermsAndPolicyStatus) ? R.drawable.check_out : R.drawable.check_in);

        if (btnPrivatePolicyStatus && btnTermsAndPolicyStatus) {
            btnPrivatePolicyAccept.setBackgroundColor(Color.BLUE);
            btnPrivatePolicyAccept.setEnabled(true);
        } else {
            btnPrivatePolicyAccept.setBackgroundColor(Color.GRAY);
            btnPrivatePolicyAccept.setEnabled(false);
        }

        btnPrivatePolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPrivatePolicyStatus = !btnPrivatePolicyStatus;
                btnPrivatePolicy.setImageResource(btnPrivatePolicyStatus ? R.drawable.check_in : R.drawable.check_out);
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
                btnTermsAndPolicy.setImageResource(btnTermsAndPolicyStatus ? R.drawable.check_in : R.drawable.check_out);
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
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new WelcomeFragment()).commit();
            }
        });

        txtLinkPrivatePolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new PrivatePolicyFragment(ctx)).commit();
            }
        });

        txtLinkTermsAndConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new TermsAndUseFragment(ctx)).commit();
            }
        });
    }
}