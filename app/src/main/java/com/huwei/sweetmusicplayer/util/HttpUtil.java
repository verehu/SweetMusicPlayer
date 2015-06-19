package com.huwei.sweetmusicplayer.util;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.huwei.sweetmusicplayer.SweetApplication;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * 对于网络请求框架volley的封装
 * @author jayce
 * @date 2015/05/24
 */
public class HttpUtil {
    public static final String URLROOT="http://192.168.43.154:8000/pandora/";

    /**
     * 封装的Post请求
     * @param url
     * @param params
     * @param handler
     */
    public static void post(String url, final HttpParams params,HttpHandler handler) {
        url=handleurl(url);

        RequestQueue mQueue = SweetApplication.getQueue();
        StringRequest request = new StringRequest(Request.Method.POST,url,handler,handler){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> p=params.getParams();
                return p;
            }
        };

        mQueue.add(request);
        mQueue.start();
        notifyHandlerStart(handler);
    }

    /**
     * 封装的get请求
     * @param url
     * @param params
     * @param handler
     */
    public static void get(String url, final HttpParams params, final HttpHandler handler) {
        url=handleurl(url);

        RequestQueue mQueue = SweetApplication.getQueue();
        StringRequest request = new StringRequest(Request.Method.GET,addParamsToUrl(url,params),handler,handler){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers=new HashMap<>();
                headers.put("Content-Type","text/html; charset=utf-8");
                return headers;
            }
        };

        mQueue.add(request);
        mQueue.start();
        notifyHandlerStart(handler);
    }

    public static String getCompleteUrl(String addurl){
        return URLROOT+addurl;
    }

    public static String handleurl(String url){
        //判断是否为完整地址，如果不是，自动指向app服务器并补全
        if(!url.contains("http")){
            url=getCompleteUrl(url);
        }

        //自动在地址末尾添加"/"
        if(!url.endsWith("/")){
            url+="/";
        }
        return url;
    }

    /**
     * 通知handler请求已经开始
     * @param handler
     */
    static void notifyHandlerStart(HttpHandler handler){
        if(handler!=null){
            handler.onStart();
        }
    }

    /**
     * 拼接参数到请求地址
     * @param params
     */
    static String addParamsToUrl(String url,HttpParams params){
        boolean isFirst=true;
        url+="?";
        Set<String> keys=params.getParams().keySet();
        for(String key:keys) {
            if (!isFirst){
                url += "&";
            }
            url+=key+"="+params.getParams().get(key);
            isFirst=false;
        }
        String spaceStr=url.replaceAll(" ", "%20");

        try {
            return new String(spaceStr.getBytes(),"ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return spaceStr;
    }

    static String paramsToStr(HttpParams params){
        StringBuffer buffer=new StringBuffer();
        Set<String> keys=params.getParams().keySet();
        for(String key:keys){
            buffer.append("&"+key+"="+params.getParams().get(key));
        }
        return buffer.toString();
    }
}
