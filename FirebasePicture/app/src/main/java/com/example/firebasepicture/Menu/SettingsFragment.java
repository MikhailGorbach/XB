package com.example.firebasepicture.Menu;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebasepicture.Policy.PrivatePolicyFragment;
import com.example.firebasepicture.R;

public class SettingsFragment extends Fragment {
    private TextView txtLinkSettingPrivatePolicy;
    private TextView txtLinkSettingTermsAndConditions;
    private TextView txtLinkSettingFeedBack;
    private TextView txtLinkSettingScenes;
    private SettingsFragment ctx;
    private ImageButton btnImgShareIt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        initComponent(v);

        return v;
    }

    private void initComponent(View v){
        ctx = this;

        btnImgShareIt = v.findViewById(R.id.imgBtnShareIt);
        txtLinkSettingPrivatePolicy = v.findViewById(R.id.txtLinkSettingPrivatePolicy);
        txtLinkSettingTermsAndConditions = v.findViewById(R.id.txtLinkSettingTermsAndConditions);
        txtLinkSettingFeedBack = v.findViewById(R.id.txtLinkSettingFeedBack);
        //txtLinkSettingScenes = v.findViewById(R.id.txtLinkSettingScenes);

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

        btnImgShareIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String textToSend="some text";
                intent.putExtra(Intent.EXTRA_TEXT, textToSend);
                try
                {
                    startActivity(Intent.createChooser(intent, "Описание действия"));
                }
                catch (android.content.ActivityNotFoundException ex)
                {
                    Toast.makeText(getContext(), "Some error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}