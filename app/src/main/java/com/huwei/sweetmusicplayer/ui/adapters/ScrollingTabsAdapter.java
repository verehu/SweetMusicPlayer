/**
 * 
 */
package com.huwei.sweetmusicplayer.ui.adapters;

 
import com.huwei.sweetmusicplayer.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * @author Jayce
 * @date  2014-9-10
 *
 */
public class ScrollingTabsAdapter implements TabAdapter {
	private final Activity activity;
	
	public ScrollingTabsAdapter(Activity ac){
		this.activity=ac;
	}

	/* (non-Javadoc)
	 * @see com.huwei.sweetmusicplayer.ui.adapters.TabAdapter#getView(int)
	 */
	@Override
	public View getView(int position) {
		// TODO Auto-generated method stub
		LayoutInflater inflater=activity.getLayoutInflater();
		final Button button=(Button) inflater.inflate(R.layout.tabs, null);
		
		final String[] mTitles = activity.getResources().getStringArray(R.array.tab_titles);
		
		button.setText(mTitles[position]);
		return button;
	}

}
