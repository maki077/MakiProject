package com.maki.project.ui.iview;

import com.maki.project.base.iview.IBaseView;
import com.maki.project.model.bean.Meizi;

/**
 * Administrator on 2016/8/8.
 */
public interface IMeiziMainView extends IBaseView{
    void showProgress();
    void hideProgress();
    void showErrorView();
    void showNoMoreData();
    void showMeiziList(Meizi meiziResult);
}
