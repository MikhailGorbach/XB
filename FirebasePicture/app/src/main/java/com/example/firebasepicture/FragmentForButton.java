package com.example.firebasepicture;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

interface GetDataFromFragment {
    void GetData(String data);
}

public class FragmentForButton extends Fragment{
    private RecyclerView recyclerView;
    private AdapterToCards adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        initComponents(v);

        return v;
    }

    private void initComponents(View v){
        //Подключаем корневую папку с карточками
        FirestoreRecyclerOptions<Model> options =
                new FirestoreRecyclerOptions.Builder<Model>()
                    .setQuery(FirebaseFirestore.getInstance().collection("Данные"), Model.class)
                    .build();

        adapter = new AdapterToCards(options);

        recyclerView = v.findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    /* @Override
    public void onClick(View v) {
        //Возращаемые данные
        String res = "Kek2";

        Button btn = (Button) v;

        if(btn.getId()==R.id.btnKek1) res = "blasterH";
        if(btn.getId()==R.id.btnKek2) res = "Cyber";
        if(btn.getId()==R.id.btnKek3) res = "Nyi myanmar force";

        GetDataFromFragment listener = (GetDataFromFragment) getActivity();
        listener.GetData(res);
    }*/
}