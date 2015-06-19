package com.huwei.sweetmusicplayer.po;

/**
 * @author Jayce
 * @date 2015/6/13
 */
public class Error {

    public static final int ERROR_CODE_OK=22000;
    private int error_code;


    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
    public boolean isValid(){
        return error_code==0||error_code== ERROR_CODE_OK;
    }

}
