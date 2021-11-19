package com.example.firebasepicture.Menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.firebasepicture.R;

public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings_new, container, false);
        initComponent(v);
        return v;
    }

    private void initComponent(View v){
        Fragment f = this;

        ( (LinearLayout) v.findViewById(R.id.layoutSupportFragmentSetting) ).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new SupportFragment(f)).commit();
                }
            });
        ( (LinearLayout) v.findViewById(R.id.layoutTeltegramFragmentSetting) ).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new TelegramFragment(f)).commit();
                }
            });
        ( (LinearLayout) v.findViewById(R.id.layoutListFragmentSetting) ).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new BrandListFragment(f)).commit();
                }
            });
        ( (LinearLayout) v.findViewById(R.id.layoutInfoFragmentSetting) ).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new InfoFragment(f)).commit();
                }
            });
      /*
        txtLinkSettingPrivatePolicy = v.findViewById(R.id.txtLinkSettingPrivatePolicy);
        txtLinkSettingTermsAndConditions = v.findViewById(R.id.txtLinkSettingTermsAndConditions);
        txtLinkSettingFeedBack = v.findViewById(R.id.txtLinkSettingFeedBack);

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
        });*/
    }
}