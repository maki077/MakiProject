package com.maki.project.presenter;

import android.content.Context;

import com.maki.project.base.presenter.BasePresenter;
import com.maki.project.ui.iview.IMeiziMainView;

/**
 * Administrator on 2016/8/8.
 */
public class MeiziMainPresenter extends BasePresenter<IMeiziMainView>{
    public MeiziMainPresenter(Context context, IMeiziMainView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
        subscription.unsubscribe();
    }
}
