package com.huwei.sweetmusicplayer.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jayce
 * @date 2015/05/24
 * Add parameter for http request , can only be the kind included in bases[] , or throw exception !
 */
public class HttpParams {
    Map<String, String> params = new HashMap<>();

    Class bases[] = {Integer.class, Long.class, Short.class, Float.class, Double.class, String.class};

    public void add(String key,Object param) {
        try {
            if(isValidate(param)){
                params.put(key,param +"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getParams() {
        return params;
    }

    public boolean isValidate(Object param) throws Exception{
        for (Class c : bases) {
            if(param.getClass() == c)
                return true;
        }
        throw new Exception("param "+param+" is not allowed.");
    }
}
