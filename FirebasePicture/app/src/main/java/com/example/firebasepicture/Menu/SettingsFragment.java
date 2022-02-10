package com.example.firebasepicture.Menu;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.firebasepicture.Activity.MainActivity;
import com.example.firebasepicture.Menu.Settings.BrandListFragment;
import com.example.firebasepicture.Menu.Settings.InfoFragment;
import com.example.firebasepicture.Menu.Settings.SupportFragment;
import com.example.firebasepicture.Menu.Settings.TelegramFragment;
import com.example.firebasepicture.R;
import com.example.firebasepicture.Utility.OnBackPressedListener;
import com.example.firebasepicture.databinding.FragmentSettingsNewBinding;

public class SettingsFragment extends Fragment implements OnBackPressedListener {
    private FragmentSettingsNewBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsNewBinding.inflate(inflater, container, false);
        initComponent();
        return binding.getRoot();
    }

    private void initComponent(){
        Fragment f = this;

        binding.layoutSupportFragmentSetting.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new SupportFragment(f)).commit();
                }
            });
       binding.layoutTeltegramFragmentSetting.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new TelegramFragment(f)).commit();
                }
            });
       /*

       binding.layoutListFragmentSetting.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new BrandListFragment(f)).commit();
                }
            });

       */
       binding.layoutInfoFragmentSetting.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new InfoFragment(f)).commit();
                }
            });
    }

    @Override
    public void onBackPressed() {
        Log.d("debug", "Нажатие на кнопку назад в " + this.toString());
        getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.fragment_container)).commit();

        MainActivity.clear();

        MainActivity.bottomNav.setItemEnabled(R.id.bottom_nav_main, false);
        MainActivity.bottomNav.setItemEnabled(R.id.bottom_nav_main, true);
    }
}