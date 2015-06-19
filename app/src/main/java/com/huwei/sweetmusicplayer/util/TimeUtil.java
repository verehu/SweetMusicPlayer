package com.huwei.sweetmusicplayer.util;

import android.R.integer;
import android.util.Log;

 

public class TimeUtil {

	public static String mill2mmss(long duration){

		int m,s;
		String str = "";
		
		int x=(int)duration/1000;
		s=x%60;
		m=x/60;
		if(m<10){
			str+="0"+m;
		}else{
			str+=m;
		}
		
		if(s<10){
			str+=":0"+s;
		}else{
			str+=":"+s;
		}
		
		return str;
	}
	
	public static int getLrcMillTime(String time){
		int millTime=0;
		time=time.replace(".", ":");
		
 
		
		String timedata[]=time.split(":");
		
		//Log.i("min,second,mill", timedata[0]+","+timedata[1]+","+timedata[2]);
		int min=0;
		int second=0;
		int mill=0;
		try {
			min = Integer.parseInt(timedata[0]);
			second = Integer.parseInt(timedata[1]);
			mill = Integer.parseInt(timedata[2]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			

			return -1;
		 
		}
		
		
		millTime=(min*60+second)*1000+mill*10;
		return millTime;
	}
}
