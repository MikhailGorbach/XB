package com.example.firebasepicture;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

interface GetDataFromFragment {
    void GetData(String data);
}

public class FragmentForButton extends Fragment implements GetDataFromFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_for_button, container, false);

        initComponents(v);

        return v;
    }

    @Override
    public void GetData(String data) {

    }

    private void initComponents(View v){

        Button btnKek = v.findViewById(R.id.btnKek);
        btnKek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Возращаемые данные
                String res = "Kek2";

                GetDataFromFragment listener = (GetDataFromFragment) getActivity();
                listener.GetData(res);
            }
        });
    }
}