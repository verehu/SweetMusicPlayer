package com.huwei.sweetmusicplayer.ui.adapters;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter{

	private final ArrayList<Fragment> mFragments=new ArrayList<Fragment>();
	
	public PagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}
 
	 

	public void addFragment(Fragment fg){
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
