package com.maki.project.base.presenter;

import android.content.Context;

import com.maki.project.base.iview.IBaseView;

import rx.Subscription;

/**
 * 基础presenter  P持有view对象 构造方法内传值IView iv = View
 */
public abstract class BasePresenter<T extends IBaseView>  {

    protected Subscription subscription;
    protected Context context;
    protected T iView;

    public BasePresenter(Context context, T iView) {
        this.context = context;
        this.iView = iView;
    }

    public void init(){
        iView.initView();
    }

    public abstract void release();
}
