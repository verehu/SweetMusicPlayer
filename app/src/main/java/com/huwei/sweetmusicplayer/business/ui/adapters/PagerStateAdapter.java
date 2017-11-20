package com.huwei.sweetmusicplayer.business.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jerry
 * @date 2016/01/17
 */
public class PagerStateAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments = new ArrayList<Fragment>();

    public PagerStateAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    public PagerStateAdapter(FragmentManager fm, List<Fragment> fragmentList) {
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
