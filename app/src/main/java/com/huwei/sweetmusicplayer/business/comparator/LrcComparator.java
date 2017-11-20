package com.huwei.sweetmusicplayer.business.comparator;

import java.util.Comparator;

import com.huwei.sweetmusicplayer.business.models.LrcContent;
 
 

public class LrcComparator implements Comparator<LrcContent>{

	@Override
	public int compare(LrcContent o1, LrcContent o2) {
		// TODO Auto-generated method stub
		 
		return o1.getLrcTime()-o2.getLrcTime();
	}

}
