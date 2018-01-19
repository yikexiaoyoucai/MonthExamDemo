package com.example.yujie.monthexamdemo.presenter;

import com.example.yujie.monthexamdemo.base.BasePresenter;
import com.example.yujie.monthexamdemo.model.DataDataBean;
import com.example.yujie.monthexamdemo.model.IModel;
import com.example.yujie.monthexamdemo.model.Model;

import java.util.Map;

/**
 * Created by yujie on 2018/1/18.
 */

public class PresenterImpl extends BasePresenter<IPresenter> implements IModel {
    private Model model;

    @Override
    protected void initModel() {
        model = new Model(this);
    }

    public void getData(String baseUrl, String url, Map<String, String> params){
        model.getData(baseUrl, url, params);
    }

    @Override
    public void onModelSuccess(DataDataBean dataDataBean) {
        iPresenter.onPreSuccess(dataDataBean);
    }

    @Override
    public void onModelError() {
        iPresenter.onPreError();
    }
}
