package com.example.yujie.monthexamdemo.base;

public abstract class BasePresenter<IPre> {
    public IPre iPresenter;

    public void attach(IPre iPresenter){
        this.iPresenter = iPresenter;
		initModel();
    }

    public void detach(){
        if(iPresenter != null){
            iPresenter = null;
        }
    }

    protected abstract void initModel();
}
