package com.example.firebasepicture.Menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasepicture.Activity.MainActivity;
import com.example.firebasepicture.Menu.MenuFragmentSource.FragmentForButton;
import com.example.firebasepicture.Menu.MenuFragmentSource.FragmentForCard;
import com.example.firebasepicture.Model;
import com.example.firebasepicture.R;
import com.example.firebasepicture.Utility.OnBackPressedListener;
import com.example.firebasepicture.databinding.FragmentListBinding;
import com.example.firebasepicture.databinding.FragmentMenuBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment implements OnBackPressedListener {

    private RecyclerView listOfItemSetting;
    private ArrayList<Model> arrayList;
    private FragmentMenuBinding binding;
    private FirebaseFirestore firebaseFirestore;
    private Fragment fragment;
    private UsersAdapter adapter;
    private Boolean ButtonWithRigth;

    public MenuFragment() {
        fragment = this;
        ButtonWithRigth = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMenuBinding.inflate(inflater, container, false);
        initComponent();
        return binding.getRoot();
    }

    void fillDef(){
        //Задвинуть кнопку слева на права
        if(ButtonWithRigth){
            ButtonWithRigth = !ButtonWithRigth;
            binding.editTextTextMultiLine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, binding.editTextTextMultiLine.getLayoutParams().height,0));
            binding.button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1));
        }

        arrayList.clear();

        arrayList.add(new Model("Диваны"));
        arrayList.add(new Model("Кресла"));
        arrayList.add(new Model("Стулья"));
        arrayList.add(new Model("Кровати"));
        arrayList.add(new Model("Столы"));
        arrayList.add(new Model("Двери"));
        arrayList.add(new Model("Тумбы"));
        arrayList.add(new Model("Комоды"));
        arrayList.add(new Model("Банкетки"));
        arrayList.add(new Model("Пуфы"));
        arrayList.add(new Model("Шкафы"));
        arrayList.add(new Model("Раковины"));
        arrayList.add(new Model("Оттоманки"));
        arrayList.add(new Model("Стеллажи"));
        arrayList.add(new Model("Настенное освещение"));
        arrayList.add(new Model("Напольное освещение"));
        arrayList.add(new Model("Потолочное освещение"));
        arrayList.add(new Model("Накладные светильники"));

        if(adapter != null)
            adapter.notifyDataSetChanged();

        binding.msg.setVisibility((arrayList.size() == 0) ? View.VISIBLE : View.INVISIBLE);
    }

    void initComponent() {
        firebaseFirestore = FirebaseFirestore.getInstance();

        binding.editTextTextMultiLine.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Выдвинуть кнопку справа на лева
                if(!ButtonWithRigth){
                    ButtonWithRigth = !ButtonWithRigth;
                    binding.editTextTextMultiLine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, binding.editTextTextMultiLine.getLayoutParams().height,10f));
                    binding.button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,0));
                }

                String name = binding.editTextTextMultiLine.getText().toString();
                if(name.length() == 0) {
                    fillDef();
                    return;
                }
                Query query = firebaseFirestore
                        .collection("models");

                query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        String n = name;
                        if (queryDocumentSnapshots.isEmpty()) {
                            if (fragment.getContext() != null)

                                return;
                        }

                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        arrayList.clear();
                        Model m;

                        boolean articleCheck = name.matches("[-+]?\\d+");
                        for(DocumentSnapshot d : list){
                            m = d.toObject(Model.class);

                            if(articleCheck)
                                if(m.getArticle().indexOf(name) != -1){
                                    arrayList.add(m);
                                }

                            for (String a : m.getName().split(" ")){
                                if(a.indexOf(name) != -1){
                                    arrayList.add(m);
                                    break;
                                }
                            }

                        }

                        if(ButtonWithRigth)
                            adapter.notifyDataSetChanged();
                        else
                            fillDef();

                        binding.msg.setVisibility((arrayList.size() == 0) ? View.VISIBLE : View.INVISIBLE);
                    }
                });
                binding.listOfItemSetting.setPadding(0,0,0, 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.editTextTextMultiLine.setText("");
                fillDef();
            }
        });

        arrayList = new ArrayList<>();
        fillDef();

        adapter = new UsersAdapter(arrayList, this);
        binding.listOfItemSetting.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.listOfItemSetting.setAdapter(adapter);
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



class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserAdapterViewHolder>{
    public Fragment fragment;
    private List<Model> userModelList;

    public UsersAdapter(List<Model> userModelList, Fragment fragment) {
        this.userModelList = userModelList;
        this.fragment = fragment;
    }

    @Override
    public UsersAdapter.UserAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UsersAdapter.UserAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_uses,null));
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.UserAdapterViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtTitle.setText(userModelList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                String name = userModelList.get(position).getName();

                if(userModelList.get(position).getScaleCompensation() == -1l)
                    fragment.getFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, new FragmentForButton(name, fragment)).commit();
                else
                    fragment.getFragmentManager().beginTransaction().
                            replace(R.id.fragment_container, new FragmentForCard(userModelList.get(position), fragment)).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public class UserAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public RelativeLayout layout;

        public UserAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.uses_title);
            txtTitle.setMaxLines(1);
            txtTitle.setSingleLine();
        }
    }
}
