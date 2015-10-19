package com.huwei.sweetmusicplayer.util;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.huwei.sweetmusicplayer.SweetApplication;
import com.huwei.sweetmusicplayer.contains.IContain;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;


/**
 * 对于网络请求框架volley的封装
 * @author jayce
 * @date 2015/05/24
 */
public class HttpUtil {

    public static final String URLROOT="";
    public static final String HTTP ="http";
    public static final String HTTPS="https";

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

        Log.i(IContain.HTTP,"request post url:"+url+"\n"+new Gson().toJson(params.getParams()));

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
    public static void get(String url, final HttpParams params, final HttpHandler handler){
        get(url,params,handler,false);
    }

    /**
     * 封装的get请求
     * @param url
     * @param params
     * @param handler
     * @param isWindowsUserAgent 是否设置为IE WINDOWS 的userAgent   对于百度音乐API的某些接口需要特殊处理
     */
    public static void get(String url, final HttpParams params, final HttpHandler handler, final boolean isWindowsUserAgent) {
        url=handleurl(url);
        url=addParamsToUrl(url,params); //拼接参数

        RequestQueue mQueue = SweetApplication.getQueue();
        StringRequest request = new StringRequest(Request.Method.GET,url,handler,handler){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers=new HashMap<>();
                headers.put("Content-Type","text/html; charset=utf-8");

                if(isWindowsUserAgent){
                    headers.put("User-Agent","User-Agent:Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0;");
                }

                return headers;
            }
        };

        Log.i(IContain.HTTP,"request get url:"+url+"\n"+new Gson().toJson(params.getParams()));

        mQueue.add(request);
        mQueue.start();
        notifyHandlerStart(handler);
    }

    /**
     * 同步get请求
     * @param url   地址
     * @param params    参数
     * @return
     */
    public static String getSync(String url, final HttpParams params){
        RequestFuture<String> future = RequestFuture.newFuture();

        RequestQueue mQueue = SweetApplication.getQueue();
        StringRequest request = new StringRequest(addParamsToUrl(url, params),future,future){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers=new HashMap<>();
                headers.put("Content-Type","text/html; charset=utf-8");
                return headers;
            }
        };

        Log.i(IContain.HTTP,"request getSync url:"+url+"\n"+new Gson().toJson(params.getParams()));

        mQueue.add(request);
        mQueue.start();

        String response = null;
        try {
            response =  future.get();
            Log.i(IContain.HTTP,"response:"+url+"\n"+ response);
            return response;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Log.i(IContain.HTTP,"response:"+url+"\n"+ response);
        return null;
    }


    public static String getCompleteUrl(String addurl){
        return URLROOT+addurl;
    }

    public static String handleurl(String url){
        //判断是否为完整地址，如果不是，自动指向app服务器并补全
        if(!url.startsWith(HTTP)&&!url.startsWith(HTTPS)){
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
