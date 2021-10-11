package com.example.firebasepicture.Policy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;

import androidx.fragment.app.Fragment;

import com.example.firebasepicture.R;

public class TermsAndConditionsFragment extends Fragment {
    private ImageButton btnBack;
    private ScrollView scrlView;
    private Button btnPrivatePolicyAccept;
    private Boolean boolTmp;

    public TermsAndConditionsFragment(Button btnPrivatePolicyAccept_){
        btnPrivatePolicyAccept = btnPrivatePolicyAccept_;
        boolTmp = btnPrivatePolicyAccept.isEnabled();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_private_policy, container, false);

        initComponents(v);

        return v;
    }

    private void initComponents(View v){
        btnPrivatePolicyAccept.setEnabled(false);

        btnBack = v.findViewById(R.id.btnPrivatePolicyBack);
        scrlView = v.findViewById(R.id.scrlViewOfMainText);

        scrlView.setVerticalScrollBarEnabled(false);
        scrlView.setHorizontalScrollBarEnabled(false);

        btnBack.setImageResource(R.drawable.bbback);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPrivatePolicyAccept.setEnabled(boolTmp);
                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.infoFragmentContainer)).commit();
            }
        });
    }
}