package com.huwei.sweetmusicplayer.data.models.baidumusic.resp;

/**
 * @author Jayce
 * @date 2015/6/13
 */
public class BaseResp {

    public static final int ERROR_CODE_OK=22000;
    public static final int ERROR_CODE_ERROR=22001;
    public int error_code = 0;
    public int errorCode = 0;

    public int getError_code() {
        return error_code!=0?error_code:errorCode;
    }

    public boolean isValid(){
        return error_code==0||error_code== ERROR_CODE_OK;
    }
}
