package com.example.firebasepicture.Policy.Welcom.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class PageAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList;

    public PageAdapter(FragmentManager fm, List<Fragment> fragmentList){
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int i){
        return fragmentList.get(i);
    }

    @Override
    public int getCount(){
        return fragmentList.size();
    }

}
