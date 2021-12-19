package com.example.firebasepicture.Menu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasepicture.Menu.MenuFragmentSource.FragmentForButton;
import com.example.firebasepicture.R;
import com.example.firebasepicture.databinding.FragmentListBinding;
import com.example.firebasepicture.databinding.FragmentMenuBinding;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment{

    private RecyclerView listOfItemSetting;
    private ArrayList<String> arrayList;
    private FragmentMenuBinding binding;

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
        arrayList.clear();

        arrayList.add("Диваны");
        arrayList.add("Кресла");
        arrayList.add("Стулья");
        arrayList.add("Кровати");
        arrayList.add("Столы");
        arrayList.add("Двери");
        arrayList.add("Тумбы");
        arrayList.add("Комоды");
        arrayList.add("Банкетки");
        arrayList.add("Пуфы");
        arrayList.add("Шкафы");
        arrayList.add("Раковины");
        arrayList.add("Оттоманки");
        arrayList.add("Стеллажи");
        arrayList.add("Настенное освещение");
        arrayList.add("Напольное освещение");
        arrayList.add("Потолочное освещение");
        arrayList.add("Накладные светильники");
    }

    void initComponent() {
        arrayList = new ArrayList<>();
        fillDef();
        UsersAdapter adapter = new UsersAdapter(arrayList, this);
        binding.listOfItemSetting.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.listOfItemSetting.setAdapter(adapter);
    }


}
class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserAdapterViewHolder>{
    public Fragment fragment;
    private List<String> userModelList;

    public UsersAdapter(List<String> userModelList, Fragment fragment) {
        this.userModelList = userModelList;
        this.fragment = fragment;
    }

    @Override
    public UsersAdapter.UserAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UsersAdapter.UserAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_uses,null));
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.UserAdapterViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtTitle.setText(userModelList.get(position));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userModelList.get(position);
                fragment.getFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, new FragmentForButton(name, fragment)).commit();
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
            layout = (RelativeLayout) itemView.findViewById(R.id.layout_title);
        }
    }
}
