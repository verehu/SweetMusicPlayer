package com.huwei.sweetmusicplayer.business.ui.adapters;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments = new ArrayList<Fragment>();

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    public PagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragments = fragmentList;
    }


    public void addFragment(Fragment fg) {
        mFragments.add(fg);
        this.notifyDataSetChanged();

    }

    @Override
    public Fragment getItem(int position) {
        // TODO Auto-generated method stub
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mFragments.size();
    }

}
