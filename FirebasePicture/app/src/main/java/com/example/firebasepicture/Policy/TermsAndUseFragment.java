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
    private PurchaseItemRecyclerViewAdapter mPIAdapter;

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
        mPIAdapter = new PurchaseItemRecyclerViewAdapter(listEl);
        binding.list.setAdapter(mPIAdapter);
    }
    //adapter
    public class PurchaseItemRecyclerViewAdapter extends ExpandableRecyclerView.Adapter<PurchaseItemRecyclerViewAdapter.ChildViewHolder,ExpandableRecyclerView.SimpleGroupViewHolder,String,String>
    {
        private ArrayList<ElementA> ElementList;
        private ArrayList<String> TitleList;

        public PurchaseItemRecyclerViewAdapter(ArrayList<ElementA> ElementList){
            this.ElementList = ElementList;
            TitleList = new ArrayList<>();

            TitleList.add("Соглашение");
            TitleList.add("1. Общие положения");
            TitleList.add("2. Предмет соглашения");
            TitleList.add("3. Обязанности сторон соглашения");
            TitleList.add("4. Права сторон соглашения");
            TitleList.add("5. Обратная связь и порядок рассмотрения претензий");
            TitleList.add("6. Заключительные положения");

        }

        @Override
        public int getGroupItemCount() {
            return TitleList.size()-1;
        }

        @Override
        public int getChildItemCount(int i) {
            return 1;
        }

        @Override
        public String getGroupItem(int i) {
            return TitleList.get(i);
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

        @Override
        protected ExpandableRecyclerView.SimpleGroupViewHolder onCreateGroupViewHolder(ViewGroup parent)
        {
            return new ExpandableRecyclerView.SimpleGroupViewHolder(parent.getContext());
        }

        @Override
        protected ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
            View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_item,parent,false);
            return new ChildViewHolder(rootView);
        }

        @Override
        public void onBindGroupViewHolder(ExpandableRecyclerView.SimpleGroupViewHolder holder, int group) {
            super.onBindGroupViewHolder(holder, group);
            holder.setText(getGroupItem(group));

        }

        @Override
        public void onBindChildViewHolder(PurchaseItemRecyclerViewAdapter.ChildViewHolder holder, int group, int position)
        {
            super.onBindChildViewHolder(holder, group, position);
            holder.name.setText(getChildItem(group,position));
        }

        @Override
        public int getChildItemViewType(int i, int i1) {
            return 1;
        }

        public class ChildViewHolder extends RecyclerView.ViewHolder
        {
            private TextView name;
            public ChildViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.item_name);
            }
        }
    }
}