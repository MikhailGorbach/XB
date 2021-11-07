package com.example.firebasepicture.Policy.Welcome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.firebasepicture.Policy.Welcome.Adapter.PageAdapter;
import com.example.firebasepicture.R;

import java.util.ArrayList;
import java.util.List;

public class WelcomeFragment extends Fragment {
    private ViewPager pager;
    private PagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_welcome, container, false);
        initComponents(v);
        return v;
    }

    private void initComponents(View v){
        pager = v.findViewById(R.id.pager);

        List<Fragment> list = new ArrayList<>();
        list.add(new PageFragment(R.drawable.page_1,false, R.string.WelcomePage1Title, R.string.WelcomePage1Text));
        list.add(new PageFragment(R.drawable.page_2,false, R.string.WelcomePage2Title, R.string.WelcomePage2Text));
        list.add(new PageFragment(R.drawable.page_3,true, R.string.WelcomePage3Title, R.string.WelcomePage3Text));

        adapter = new PageAdapter(getFragmentManager(), list);

        pager.setAdapter(adapter);
    }
}