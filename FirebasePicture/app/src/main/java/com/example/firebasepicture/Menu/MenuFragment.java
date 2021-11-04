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

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment{

    private RecyclerView listOfItemSetting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        initComponent(v);
        return v;
    }

    void initComponent(View v) {
        listOfItemSetting = v.findViewById(R.id.listOfItemSetting);
        ArrayList<String> arrayList = new ArrayList<>();
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
        arrayList.add("Кушетки");
        arrayList.add("Стеллажи");
        arrayList.add("Настенное освещение");
        arrayList.add("Напольное освещение");
        arrayList.add("Люстры");
        UsersAdapter adapter = new UsersAdapter(arrayList);

        listOfItemSetting.setLayoutManager(new LinearLayoutManager(getContext()));
        listOfItemSetting.setAdapter(adapter);
    }


    public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserAdapterViewHolder>{
        private List<String> userModelList;

        public UsersAdapter(List<String> userModelList) {
            this.userModelList = userModelList;
        }

        @Override
        public UserAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new UserAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_uses,null));
        }

        @Override
        public void onBindViewHolder(@NonNull UserAdapterViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.txtTitle.setText(userModelList.get(position));
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = userModelList.get(position);
                    switch (name) {
                        case "Диваны":
                            name = "sofa";
                            break;
                        case "Кресла":
                            name = "armchair";
                            break;
                        case "Стулья":
                            name = "chair";
                            break;
                        case "Кровати":
                            name = "bed";
                            break;
                        case "Столы":
                            name = "table";
                            break;
                        case "Двери":
                            name = "door";
                            break;
                        case "Тумбы":
                            name = "pedestal";
                            break;
                        case "Комоды":
                            name = "dresser";
                            break;
                        case "Банкетки":
                            name = "banquette";
                            break;
                        case "Пуфы":
                            name = "poof";
                            break;
                        case "Шкафы":
                            name = "wardrobe";
                            break;
                        case "Кушетки":
                            name = "couch";
                            break;
                        case "Стеллажи":
                            name = "shelf";
                            break;
                        case "Настенное освещение":
                            name = "lightWall";
                            break;
                        case "Напольное освещение":
                            name = "lightFloor";
                            break;
                        case "Люстры":
                            name = "chandelier";
                            break;
                    }
                    getFragmentManager().beginTransaction().
                            replace(R.id.fragment_container, new FragmentForButton(name)).commit();
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

}