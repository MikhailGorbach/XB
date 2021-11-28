package com.example.firebasepicture.Menu.Settings;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.firebasepicture.Menu.MenuFragmentSource.GetDataFromFragment;
import com.example.firebasepicture.R;
import com.example.firebasepicture.databinding.FragmentContactBinding;

public class ContactFragment extends Fragment {
    private Fragment fragment;
    private FragmentContactBinding binding;
    private Fragment context;
    public ContactFragment(Fragment fragment) {
        this.fragment = fragment;
        context = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactBinding.inflate(inflater, container, false);
        initComponents();
        return binding.getRoot();
    }

    private void initComponents(){
        binding.btnBackFragmentContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
        binding.textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setPrimaryClip(ClipData.newPlainText("", binding.textView4.getText().toString()));

                Toast.makeText(context.getContext(), "Почта скопирована в буфер обмена", Toast.LENGTH_SHORT).show();
            }
        });
    }
}