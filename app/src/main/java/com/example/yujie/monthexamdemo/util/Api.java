package com.example.yujie.monthexamdemo.util;

import java.util.HashMap;
import java.util.Map;

import retrofit2.http.PATCH;

/**
 * Created by yujie on 2018/1/18.
 */

public class Api {
    //http://h5test.newaircloud.com/api/getLayouts?sid=xkycs&cid=10024&date=
    public static final String BASE_URL = "http://h5test.newaircloud.com/api/";
    public static final String URL = "getLayouts";
    public static Map<String, String> getParams(){
        Map<String, String> params = new HashMap<>();
        params.put("sid", "xkycs");
        params.put("cid", "10024");
        return params;
    }
}
