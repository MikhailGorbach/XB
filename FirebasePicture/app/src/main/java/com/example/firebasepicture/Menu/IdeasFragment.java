package com.example.firebasepicture.Menu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebasepicture.Menu.MenuFragmentSource.FragmentForButton;
import com.example.firebasepicture.Policy.Welcome.Adapter.PageAdapter;
import com.example.firebasepicture.R;
import com.example.firebasepicture.databinding.FragmentIdeasBinding;
import com.example.firebasepicture.databinding.FragmentPageBinding;
import com.example.firebasepicture.databinding.PageAdapterIdeasCardBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class IdeasFragment extends Fragment {
    private FragmentIdeasBinding binding;
    private ArrayList<String> links;

    public IdeasFragment(){
        links = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIdeasBinding.inflate(inflater, container, false);
        initComponents();
        return binding.getRoot();
    }

    private void initComponents(){
        links.add("https://homevis.tech/static/users/img/1.png");
        links.add("https://homevis.tech/static/users/img/2.png");
        links.add("https://homevis.tech/static/users/img/3.png");

        binding.listMyList.setAdapter(new UsersAdapter2(links,this));
    }

    public class UsersAdapter2 extends RecyclerView.Adapter<UsersAdapter2.UserAdapterViewHolder>{
        public Fragment fragment;
        private List<String> userModelList;

        public UsersAdapter2(List<String> userModelList, Fragment fragment) {
            this.userModelList = userModelList;
            this.fragment = fragment;
        }

        @NonNull
        @Override
        public UserAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new UsersAdapter2.UserAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_uses,null));
        }

        @Override
        public void onBindViewHolder(@NonNull UserAdapterViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.txtTitle.setText(userModelList.get(position));
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = userModelList.get(position);
                    getFragmentManager().beginTransaction().
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

}