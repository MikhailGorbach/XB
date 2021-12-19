package com.example.firebasepicture.Menu.MenuFragmentSource;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
    private TextView txtDeskArticle;
    private TextView txtMore;
    private TextView txtDescription;

    private ImageView img;
    private ImageButton btnImgShareIt;
    private Context context;

    private Fragment fragment;

    public FragmentForCard(Model model, Fragment fragment) {
        this.model = model;
        this.fragment = fragment;

        context = fragment.getContext();
    }

    public FragmentForCard(Model model, Context context) {
        this.model = model;
        this.fragment = null;

        this.context = context;
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

    void initComponents(View view){
        txtDeskSeller   = view.findViewById(R.id.txtDeskSeller);
        txtDeskArticle  = view.findViewById(R.id.txtDeskArticle);
        img             = view.findViewById(R.id.imgDeskAvatar);
        img.setClipToOutline(true);
        btnImgShareIt   = view.findViewById(R.id.imgBtnShareIt);
        txtMore = ( (TextView) view.findViewById(R.id.txtMore));
        txtDescription = ( (TextView) view.findViewById(R.id.txtDeskDescription) );

        ( (TextView) view.findViewById(R.id.txtDeskPrice) ).setText(model.getPrice() + " ₽");
        ( (TextView) view.findViewById(R.id.txtDeskTitle) ).setText(model.getName());

        txtDescription.setText(model.getDescription());

        ( (TextView) view.findViewById(R.id.txtDeskWidth) ).setText(model.getWidth() + " см");
        ( (TextView) view.findViewById(R.id.txtDeskDepth) ).setText(model.getDepth() + " см");
        ( (TextView) view.findViewById(R.id.txtDeskHeight) ).setText(model.getHeight() + " см");
        ( (TextView) view.findViewById(R.id.txtDeskMaterial) ).setText(model.getMaterial());
        ( (TextView) view.findViewById(R.id.txtDeskColor) ).setText(model.getColour());
        ( (TextView) view.findViewById(R.id.txtDeskContries) ).setText(model.getCountry());
        ( (TextView) view.findViewById(R.id.txtDeskAssembling) ).setText(model.getAssembling());
        ( (TextView) view.findViewById(R.id.txtDeskDelivery) ).setText("Доставка и сборка в течение "+model.getDelivery());
        ( (TextView) view.findViewById(R.id.txtDeskCompany) ).setText(model.getCompany());

        txtMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtMore.getText().toString().equals("ещё")) {

                    ValueAnimator animator = ValueAnimator.ofInt(3,100);
                    animator.setDuration(900);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            txtDescription.setMaxLines((Integer) animator.getAnimatedValue());
                        }
                    });
                    animator.start();

                    txtMore.setText("скрыть");
                }else{
                    ValueAnimator animator = ValueAnimator.ofInt(txtDescription.getLineCount(),3);
                    animator.setDuration(300);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            txtDescription.setMaxLines((Integer) animator.getAnimatedValue());
                        }
                    });
                    animator.start();
                    txtMore.setText("ещё");
                }
            }
        });

        txtDeskArticle.setText(model.getArticle());
        txtDeskArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setPrimaryClip(ClipData.newPlainText("", model.getArticle()));

                Toast.makeText(context, "Артикул скопирован в буфер обмена", Toast.LENGTH_SHORT).show();
            }
        });

        txtDeskSeller.setText(model.getSeller() == null ? "Нет" : model.getSeller());
        ((ImageView) view.findViewById(R.id.imgDeskPicture2FragmentForCard)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                MyDialogFragment myDialogFragment = new MyDialogFragment(model);
                myDialogFragment.show(manager, "myDialog");
            }
        });

        ( (TextView) view.findViewById(R.id.txtDeskLink) ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getLink()));
                startActivity(browserIntent);

            }
        });
        Glide.with(view.getContext()).load(model.getPhoto()).into(img);

        ((ImageButton) view.findViewById(R.id.btnBack)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragment != null)
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                else
                    getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.fragment_container)).commit();
            }
        });
        ((ImageButton) view.findViewById(R.id.btn3D)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDataFromFragment listener;
                listener = (GetDataFromFragment) getActivity();

                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.fragment_container)).commit();
                listener.GetData(model.getArticle());
            }
        });



        btnImgShareIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String textToSend="https://homevis.tech/"+model.getArticle();
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