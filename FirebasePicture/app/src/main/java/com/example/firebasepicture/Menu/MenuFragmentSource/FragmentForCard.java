package com.example.firebasepicture.Menu.MenuFragmentSource;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.firebasepicture.R;

public class FragmentForCard extends Fragment implements GetDataFromFragment{
    private Model model;
    private TextView txtTitle;
    private TextView txtPrice;
    private ImageView img;

    private Fragment fragment;

    public FragmentForCard(Model model, Fragment fragment) {
        this.model = model;
        this.fragment = fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_for_card, container, false);
        initComponents(v);
        return v;
    }

    void initComponents(View v){
        txtTitle    = v.findViewById(R.id.txtDaskTitle);
        txtPrice    = v.findViewById(R.id.txtDaskPrice);
        img         = v.findViewById(R.id.imgDaskAvatar);

        txtTitle.setText(model.getTitle());
        txtPrice.setText(model.getPrice());
        Glide.with(v.getContext()).load(model.getPic()).into(img);

        v.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
        ((Button) v.findViewById(R.id.btn3D)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDataFromFragment listener;
                listener = (GetDataFromFragment) getActivity();

                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.fragment_container)).commit();
                listener.GetData(model.getName());
            }
        });

    }

    @Override
    public void GetData(String data) {

    }
}