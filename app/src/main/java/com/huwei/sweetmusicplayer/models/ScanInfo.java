package com.huwei.sweetmusicplayer.models;

import com.huwei.sweetmusicplayer.enums.ScanInfoType;

public class ScanInfo {
	private ScanInfoType type;
	private String info;
	
	public ScanInfoType getType() {
		return type;
	}
	public void setType(ScanInfoType type) {
		this.type = type;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	
}
