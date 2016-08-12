package com.maki.project.presenter;

import android.content.Context;

import com.maki.project.base.presenter.BasePresenter;
import com.maki.project.http.IGankRetrofit;
import com.maki.project.model.bean.GankResultBean;
import com.maki.project.model.bean.Meizi;
import com.maki.project.ui.iview.IMeiziMainView;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * meizi主页的Presenter
 */
public class MeiziMainPresenter extends BasePresenter<IMeiziMainView> {
    public MeiziMainPresenter(Context context, IMeiziMainView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
        subscription.unsubscribe();
    }


    public void fetchMeiziData(int page) {
        OkHttpClient client;
        // Log信息
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        //loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // OkHttp3.0的使用方式
        client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IGankRetrofit.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        IGankRetrofit iGankRetrofit = retrofit.create(IGankRetrofit.class);
        Observable<GankResultBean<Meizi>> observable = iGankRetrofit.getMeiziData(page);

        subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GankResultBean<Meizi>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        iView.showErrorView();
                        iView.hideProgress();
                    }

                    @Override
                    public void onNext(GankResultBean<Meizi> meiziGankResultBean) {
                        if(!meiziGankResultBean.isError()){
                            if (meiziGankResultBean.getResults().size() == 0){
                                iView.showNoMoreData();
                            }else {
                                iView.showMeiziList(meiziGankResultBean.getResults());
                            }
                            iView.hideProgress();
                        }
                    }
                });
    }


}
