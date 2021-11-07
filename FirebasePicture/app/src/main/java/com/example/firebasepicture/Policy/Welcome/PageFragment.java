package com.example.firebasepicture.Policy.Welcome;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.firebasepicture.MainActivity;
import com.example.firebasepicture.R;
import com.example.firebasepicture.databinding.FragmentPageBinding;

public class PageFragment extends Fragment {

    private boolean btnVisible;
    private int resDrawable;
    private int resWelcomeText;
    private int resWelcomeTitle;

    private FragmentPageBinding binding;
    private ImageView imgWelcomPicture;
    private Button btnWelcomAccept;
    private TextView txtWelcomeTitle;
    private TextView txtWelcomeText;

    public PageFragment(int resDrawable, boolean btnVisible, int resWelcomeTitle, int resWelcomeText) {
        this.btnVisible = btnVisible;
        this.resDrawable = resDrawable;
        this.resWelcomeText = resWelcomeText;
        this.resWelcomeTitle = resWelcomeTitle;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPageBinding.inflate(inflater, container, false);
        initComponents();
        return binding.getRoot();
    }

    private void initComponents() {
        imgWelcomPicture = binding.imgWelcomPicture;
        btnWelcomAccept = binding.btnWelcomAccept;
        txtWelcomeTitle = binding.txtWelcomeTitle;
        txtWelcomeText = binding.txtWelcomeText;

        imgWelcomPicture.setImageResource(resDrawable);

        btnWelcomAccept.setVisibility((btnVisible) ? View.VISIBLE : View.INVISIBLE);
        btnWelcomAccept.setEnabled(btnVisible);
        btnWelcomAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = MainActivity.mySharedPreferences.edit();
                editor.putBoolean(MainActivity.PrivatePolicyKey, true);
                editor.apply();

                MainActivity.bottomNav.setEnabled(true);
                MainActivity.bottomNav.setVisibility(View.VISIBLE);

                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.fragment_container)).commit();
            }
        });

        txtWelcomeTitle.setText(resWelcomeTitle);
        txtWelcomeText.setText(resWelcomeText);
    }

}