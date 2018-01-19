package com.example.yujie.monthexamdemo.util;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by yujie on 2018/1/18.
 */

public class RetrofitHelper {
    public static OkHttpClient okHttpClient;
    public static ApiService apiService;

    static {
        getOkHttpClient();
    }

    public static OkHttpClient getOkHttpClient(){
        if(okHttpClient == null){
            synchronized (OkHttpClient.class) {
                if(okHttpClient == null){
                    File fileDir = new File(Environment.getExternalStorageDirectory(), "cache");
                    long fileSize = 10 * 1024 * 1024;
                    okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(15, TimeUnit.SECONDS)
                            .cache(new Cache(fileDir, fileSize))
                            .build();
                }
            }
        }
        return okHttpClient;
    }

    public static ApiService getApiService(String baseUrl){
        if(apiService == null){
            synchronized (OkHttpClient.class) {
                apiService = createApiService(baseUrl, ApiService.class);
            }
        }
        return apiService;
    }

    private static <T>T createApiService(String baseUrl, Class<T> tClass) {
        T t = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(tClass);
        return t;
    }

}
