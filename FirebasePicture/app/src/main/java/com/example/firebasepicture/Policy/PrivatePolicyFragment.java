package com.example.firebasepicture.Policy;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebasepicture.Menu.Settings.BrandListFragment;
import com.example.firebasepicture.Menu.Settings.ElementA;
import com.example.firebasepicture.Menu.Settings.Seller;
import com.example.firebasepicture.R;
import com.example.firebasepicture.databinding.FragmentPrivatePolicyBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ayalma.ir.expandablerecyclerview.ExpandableRecyclerView;

public class PrivatePolicyFragment extends Fragment {

    private Fragment fragment;
    private FragmentPrivatePolicyBinding binding;
    private ArrayList<ElementA> listEl;
    private FirebaseFirestore firebaseFirestore;
    private PrivatePolicyFragment f;
    private PurchaseItemRecyclerViewAdapter mPIAdapter;

    public PrivatePolicyFragment(Fragment fragment){
        this.fragment = fragment;
        firebaseFirestore = FirebaseFirestore.getInstance();
        listEl = new ArrayList<>();
        f = this;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPrivatePolicyBinding.inflate(inflater,container,false);
        initComponents();
        return binding.getRoot();
    }

    private void initComponents(){
        binding.btnBackFragmentPrivatePolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });

        Query query = firebaseFirestore.collection("privacy");
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
                        case "seven":
                            a.num = 7;
                            break;
                        case "eight":
                            a.num = 8;
                            break;
                        case "nine":
                            a.num = 9;
                            break;
                        case "ten":
                            a.num = 10;
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
        mPIAdapter = new PurchaseItemRecyclerViewAdapter(listEl); // purchaseItemAdapter
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

            TitleList.add("1. Общие положения");
            TitleList.add("2. Состав данных");
            TitleList.add("3. Цели обработки данных");
            TitleList.add("4. Условия и способы обработки данных");
            TitleList.add("5. Спроки обработки даных");
            TitleList.add("6. Порядок сбора, хранения, передачи и других видов обработки данных");
            TitleList.add("7. Меры по обеспечению безопасности персональных данных при их обработке, принимаемые Оператором");
            TitleList.add("8. Прекращение обработки персональных данных");
            TitleList.add("9. Ответственность сторон");
            TitleList.add("10. Разрешение споров");
        }

        @Override
        public int getGroupItemCount() {
            return TitleList.size()-1;
        }

        @Override
        public String getGroupItem(int i) {
            return TitleList.get(i);
        }

        @Override
        public int getChildItemCount(int i) {
            return 1;
        }

        @Override
        public int getChildItemViewType(int i, int i1) {
            return 1;
        }

        @Override
        public String getChildItem(int group, int child) {
            if(ElementList.size() == 0) return " ";
            String res = " ";
            for(ElementA a : ElementList){
                if(a.num == group+1){
                    res += "\t"+a.getNumber()+" "+a.getText()+"\n";
                }
            }
            return res;
        }

        @Override
        public void onBindChildViewHolder(ChildViewHolder holder, int group, int position)
        {
            super.onBindChildViewHolder(holder, group, position);
            holder.name.setText(getChildItem(group,position));
        }

        @Override
        protected ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType)
        {
            View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_item,parent,false);
            return new ChildViewHolder(rootView);
        }

        @Override
        public void onBindGroupViewHolder(ExpandableRecyclerView.SimpleGroupViewHolder holder, int group) {
            super.onBindGroupViewHolder(holder, group);
            holder.setText(getGroupItem(group));
        }

        public class ChildViewHolder extends RecyclerView.ViewHolder
        {
            private TextView name;
            public ChildViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.item_name);
            }
        }

        @Override
        protected ExpandableRecyclerView.SimpleGroupViewHolder onCreateGroupViewHolder(ViewGroup parent) {
            return new ExpandableRecyclerView.SimpleGroupViewHolder(parent.getContext());
        }

    }


}