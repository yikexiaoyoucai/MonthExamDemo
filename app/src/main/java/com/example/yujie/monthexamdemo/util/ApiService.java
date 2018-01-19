package com.example.yujie.monthexamdemo.util;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by yujie on 2018/1/18.
 */

public interface ApiService {
    @GET
    Observable<String> getData(@Url String url);

    @GET
    Observable<String> getData(@Url String url, @QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST
    Observable<String> postData(@Url String url);

    @FormUrlEncoded
    @POST
    Observable<String> postData(@Url String url, @FieldMap Map<String, String> params);
}
