package com.example.firebasepicture;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FragmentMenu extends Fragment implements GetDataFromFragment{
    private static final String TAG = "FragmentDebag";
    private RecyclerView dataList;
    private CustomGridAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "enter the onCreateView");
        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        initComponents(v);
        Log.d(TAG, "out from onCreateView");
        return v;
    }

    private void initComponents(View v){
        Log.d(TAG, "enter the initComponents");
        dataList = (RecyclerView) v.findViewById(R.id.menuList);
        temp(v);
        Log.d(TAG, "out from initComponents");
    }

    private void temp(View v){
        Log.d(TAG, "enter the temp");
        List<String> strList = new ArrayList<>();
        List<String> btnList = new ArrayList<>();
        List<Integer> imgList = new ArrayList<>();

        strList.add("Des1");
        strList.add("Des2");
        strList.add("Des3");
        strList.add("Des4");
        strList.add("Des5");

        imgList.add(R.drawable.ic_launcher_foreground);
        imgList.add(R.drawable.ic_launcher_foreground);
        imgList.add(R.drawable.ic_launcher_foreground);
        imgList.add(R.drawable.ic_launcher_foreground);
        imgList.add(R.drawable.ic_launcher_foreground);

        btnList.add("Des1");
        btnList.add("Des2");
        btnList.add("Des3");
        btnList.add("Des4");
        btnList.add("Des5");

        adapter = new CustomGridAdapter(v.getContext(), imgList, strList, btnList);

        //Версия сверху вниз
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(v.getContext(), 2, GridLayoutManager.VERTICAL, false);
        //Версия слева вправо
        GridLayoutManager gridLayoutManager = new GridLayoutManager(v.getContext(), 2, GridLayoutManager.HORIZONTAL, false);

        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);
        Log.d(TAG, "out from temp");
    }

    @Override
    public void GetData(String data) {
        Log.d(TAG, "GetData at FragmentMenu: "+data );
    }
}
