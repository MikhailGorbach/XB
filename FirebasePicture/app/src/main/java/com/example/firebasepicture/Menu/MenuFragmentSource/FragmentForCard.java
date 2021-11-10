package com.example.firebasepicture.Menu.MenuFragmentSource;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.firebasepicture.Model;
import com.example.firebasepicture.R;

public class FragmentForCard extends Fragment implements GetDataFromFragment{
    private Model model;
    private TextView txtDeskSeller;
    private ImageView img;
    private ImageButton btnImgShareIt;

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
        txtDeskSeller   = v.findViewById(R.id.txtDeskSeller);
        img             = v.findViewById(R.id.imgDeskAvatar);
        btnImgShareIt   = v.findViewById(R.id.imgBtnShareIt);

        ( (TextView) v.findViewById(R.id.txtDeskPrice) ).setText(model.getPrice());
        ( (TextView) v.findViewById(R.id.txtDeskTitle) ).setText(model.getName());
        ( (TextView) v.findViewById(R.id.txtDeskDescription) ).setText(model.getDescription());
        ( (TextView) v.findViewById(R.id.txtDeskWidth) ).setText(model.getWidth() + " см.");
        ( (TextView) v.findViewById(R.id.txtDeskDepth) ).setText(model.getDepth() + " см.");
        ( (TextView) v.findViewById(R.id.txtDeskHeight) ).setText(model.getHeight() + " см.");
        ( (TextView) v.findViewById(R.id.txtDeskMaterial) ).setText(model.getMaterial());
        ( (TextView) v.findViewById(R.id.txtDeskContries) ).setText(model.getCountry());
        ( (TextView) v.findViewById(R.id.txtDeskWeight) ).setText(model.getWeight() + " см.");
        ( (TextView) v.findViewById(R.id.txtDeskVolume) ).setText(model.getVolume() + " м3.");
        ( (TextView) v.findViewById(R.id.txtDeskAssembling) ).setText(model.getAssembling());
        ( (TextView) v.findViewById(R.id.txtDeskDelivery) ).setText(model.getDelivery());
        ( (TextView) v.findViewById(R.id.txtDeskCompany) ).setText(model.getCompany());

        txtDeskSeller.setText(model.getSeller() == null ? "Нет" : model.getSeller());
        txtDeskSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                MyDialogFragment myDialogFragment = new MyDialogFragment(model);
                myDialogFragment.show(manager, "myDialog");
            }
        });

        ( (TextView) v.findViewById(R.id.txtDeskLink) ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getLink()));
                startActivity(browserIntent);

            }
        });
        Glide.with(v.getContext()).load(model.getPhoto()).into(img);

        v.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
        ((ImageButton) v.findViewById(R.id.btn3D)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDataFromFragment listener;
                listener = (GetDataFromFragment) getActivity();

                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.fragment_container)).commit();
                listener.GetData(model.getName());
            }
        });

        btnImgShareIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String textToSend="http://share";
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

    @Override
    public void GetData(String data) {

    }
}