package com.example.yujie.monthexamdemo.model;

import com.example.yujie.monthexamdemo.util.RetrofitHelper;
import com.google.gson.Gson;

import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yujie on 2018/1/18.
 */

public class Model {
    private IModel iModel;

    public Model(IModel iModel) {
        this.iModel = iModel;
    }

    public void getData(String baseUrl, String url, Map<String, String> params){
        RetrofitHelper.getApiService(baseUrl)
                .getData(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        iModel.onModelError();
                    }

                    @Override
                    public void onNext(String s) {
                        Gson gson = new Gson();
                        DataDataBean dataDataBean = gson.fromJson(s, DataDataBean.class);
                        iModel.onModelSuccess(dataDataBean);
                    }
                });
    }
}
