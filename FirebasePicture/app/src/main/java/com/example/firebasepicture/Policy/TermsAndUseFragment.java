package com.example.firebasepicture.Policy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebasepicture.Menu.Settings.ElementA;
import com.example.firebasepicture.R;
import com.example.firebasepicture.Utility.ExrGroupAdapter;
import com.example.firebasepicture.databinding.FragmentTermsAndUseBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ayalma.ir.expandablerecyclerview.ExpandableRecyclerView;

public class TermsAndUseFragment extends Fragment {
    private Fragment fragment;
    private FragmentTermsAndUseBinding binding;
    private ArrayList<ElementA> listEl;
    private FirebaseFirestore firebaseFirestore;
    private Fragment f;
    private myAdapter mPIAdapter;

    public TermsAndUseFragment(Fragment fragment) {
        this.fragment = fragment;
        listEl = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        f = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTermsAndUseBinding.inflate(inflater, container, false);
        initComponent();
        return binding.getRoot();
    }

    private void initComponent(){
        binding.btnBackFragmentTermsAndUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
        Query query = firebaseFirestore.collection("terms");
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    Log.d("debug", "Empty");

                    if (f.getContext() != null)
                        Toast.makeText( f.getContext(), "No data found in Database", Toast.LENGTH_SHORT).show();

                    return;
                }

                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                if(list.isEmpty()){
                    Log.d("debug", "EmptyList");
                    Toast.makeText(f.getContext(), "Error with download from Database", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (DocumentSnapshot d : list) {
                    ElementA a = d.toObject(ElementA.class);
                    switch (a.getCategory()){
                        case "offer":
                            a.num = 0;
                            break;
                        case "one":
                            a.num = 1;
                            break;
                        case "two":
                            a.num = 2;
                            break;
                        case "three":
                            a.num = 3;
                            break;
                        case "four":
                            a.num = 4;
                            break;
                        case "five":
                            a.num = 5;
                            break;
                        case "six":
                            a.num = 6;
                            break;
                    }
                    listEl.add(a);

                }

                mPIAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        ArrayList<String> TitleList = new ArrayList<>();

        TitleList.add("Соглашение");
        TitleList.add("1. Общие положения");
        TitleList.add("2. Предмет соглашения");
        TitleList.add("3. Обязанности сторон соглашения");
        TitleList.add("4. Права сторон соглашения");
        TitleList.add("5. Обратная связь и порядок рассмотрения претензий");
        TitleList.add("6. Заключительные положения");

        mPIAdapter = new myAdapter(TitleList, listEl);
        binding.list.setAdapter(mPIAdapter);
    }
    //adapter
    class myAdapter extends ExrGroupAdapter{

        public myAdapter(ArrayList<String> TitleList, ArrayList<ElementA> ElementList) {
            super(TitleList, ElementList);
        }

        @Override
        public String getChildItem(int group, int child) {
            if(ElementList.size() == 0) return " ";

            String res = " ";
            for(ElementA a : ElementList){

                if(a.num == group)
                    res += "\t"+a.getNumber()+" "+a.getText()+"\n";

            }
            return res;
        }
    }
}