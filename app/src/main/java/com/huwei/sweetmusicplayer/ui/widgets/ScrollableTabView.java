/**
 * 
 */
package com.huwei.sweetmusicplayer.ui.widgets;

import java.util.ArrayList;

 
import com.huwei.sweetmusicplayer.ui.adapters.TabAdapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
 

/**
 * @author huwei
 * @date  2014-9-9
 *
 */
public class ScrollableTabView extends LinearLayout//HorizontalScrollView
		 implements ViewPager.OnPageChangeListener{
	private Context mContext;
	
	private TabAdapter mAdapter;
	
	private ViewPager mViewPager;
	
	private final LinearLayout mContainer;
	
	private final ArrayList<View> mTabsList=new ArrayList<View>();
	
	public ScrollableTabView(Context context) {
		// TODO Auto-generated constructor stub
		this(context,null);
	}
	
	public ScrollableTabView(Context context, AttributeSet attrs) {
		// TODO Auto-generated constructor stub
		this(context,attrs,0);
	}
	
	public ScrollableTabView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		
		this.mContext=context;
		
		this.setHorizontalScrollBarEnabled(false);
		this.setHorizontalFadingEdgeEnabled(false);
		
		mContainer=new LinearLayout(context);
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
	
		mContainer.setLayoutParams(params);
		mContainer.setOrientation(LinearLayout.HORIZONTAL);
		
		addView(mContainer);
		
		
		//test
		//initTabs();
	}


	public void setAdapter(TabAdapter mAdapter){
		this.mAdapter=mAdapter;
		
		//undo..  
		if(mAdapter!=null&&mViewPager!=null){
			initTabs();
		}
	}
	
	public void setViewPager(ViewPager mViewPager){
		this.mViewPager=mViewPager;
		if(mAdapter!=null&&mViewPager!=null){
			initTabs();
		}
		
		mViewPager.setOnPageChangeListener(this);
	}

	private void initTabs() {
		// TODO Auto-generated method stub
		mContainer.removeAllViews();

		if(mAdapter==null||mViewPager==null){
			return;
		}
		
		for(int i=0;i<mViewPager.getAdapter().getCount();i++){
			final int index=i;
			Button tabs= (Button) mAdapter.getView(index);
			LayoutParams layoutParams=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			layoutParams.weight=1;
			mContainer.addView(tabs,layoutParams);
			
			tabs.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(mViewPager.getCurrentItem()==index){
						selectTab(index);
					}else{
						mViewPager.setCurrentItem(index, true);
					}
				}
				
			});
		}
		selectTab(0);
	}
	
	
	private void selectTab(int position){
		for(int i=0;i<mContainer.getChildCount();i++)
			mContainer.getChildAt(i).setSelected(i==position);
		

		View selectedTab=mContainer.getChildAt(position);
		
		final int w=selectedTab.getMeasuredWidth();
		final int l=selectedTab.getLeft();
		
		int x=l-this.getWidth()/2+w/2;
		
//		smoothScrollTo(x, this.getScrollY());
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		selectTab(position);
	}

}
