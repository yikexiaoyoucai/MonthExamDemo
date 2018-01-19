package com.example.yujie.monthexamdemo.presenter;

import com.example.yujie.monthexamdemo.model.DataDataBean;

/**
 * Created by yujie on 2018/1/18.
 */

public interface IPresenter {
    void onPreSuccess(DataDataBean dataDataBean);
    void onPreError();
}
