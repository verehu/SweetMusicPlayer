package com.huwei.sweetmusicplayer.business.ui.widgets;

import com.huwei.sweetmusicplayer.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
 

public class NotificationView extends RelativeLayout {
	View root;
	public NotificationView(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}
	
	public NotificationView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO Auto-generated constructor stub
	}


	public NotificationView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		
		root=inflate(getContext(), R.layout.notification_play, null);
		
		addView(root);
	}
 




}
