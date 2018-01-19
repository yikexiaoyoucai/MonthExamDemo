package com.example.yujie.monthexamdemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity<IPre, P extends BasePresenter<IPre>> extends AppCompatActivity{
    public P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResID());

        presenter = initPresenter();

        presenter.attach((IPre) this);

        initView();

        initData();
    }

    public abstract int setLayoutResID();

    public abstract P initPresenter();

    public abstract void initView();

    public abstract void initData();
}
