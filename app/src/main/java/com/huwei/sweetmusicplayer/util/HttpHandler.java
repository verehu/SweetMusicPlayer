package com.huwei.sweetmusicplayer.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.huwei.sweetmusicplayer.SweetApplication;
import com.huwei.sweetmusicplayer.business.baidumusic.resp.BaseResp;
import com.huwei.sweetmusicplayer.contants.Contants;

/**
 * @author jayce
 * @date 2015/05/24
 */
public abstract class HttpHandler implements Response.Listener<String>,Response.ErrorListener{

    public static final String TAG="HttpHandler";

    private Context context;

    public HttpHandler(){
        this.context = SweetApplication.CONTEXT;
    }

    public HttpHandler(Context context) {
        this.context = context;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
        Log.e(Contants.HTTP,"error:"+error.getMessage());


        if(error instanceof NoConnectionError) {
            Toast.makeText(context,"网络连接异常",Toast.LENGTH_LONG).show();
        }
        onFinish();
    }

    @Override
    public void onResponse(String response) {
        Log.i(Contants.HTTP,"response:"+response);

        onFinish();
        BaseResp resp = new Gson().fromJson(response,BaseResp.class);
        if(resp!=null){
            switch (resp.getError_code()){
                case BaseResp.ERROR_CODE_ERROR:
                    onErrorResponse(new VolleyError("ERROR CODE:"+BaseResp.ERROR_CODE_ERROR));
                    break;
            }
        }
        onSuccess(response);
    }

    public abstract void onSuccess(String response);

    public void onStart(){};

    public void onFinish(){};
}
