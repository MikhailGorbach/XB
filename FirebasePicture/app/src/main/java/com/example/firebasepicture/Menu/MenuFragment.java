package com.example.firebasepicture.Menu;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.firebasepicture.Menu.MenuFragmentSource.FragmentForButton;
import com.example.firebasepicture.Menu.MenuFragmentSource.FragmentForCard;
import com.example.firebasepicture.Menu.MenuFragmentSource.Model;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu, container, false);

        initComponenet(v);

        return v;
    }

    void initComponenet(View v){
        listOfItemSetting = v.findViewById(R.id.listOfItemSetting);

        List<String> arrayList = new ArrayList<String>();
        arrayList.add(getString(R.string.InfoAtFragmentMenuTables));
        arrayList.add(getString(R.string.InfoAtFragmentMenuChairs));
        arrayList.add(getString(R.string.InfoAtFragmentMenuLights));
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
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentForButton(userModelList.get(position))).commit();
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