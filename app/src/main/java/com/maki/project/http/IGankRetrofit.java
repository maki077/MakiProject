package com.maki.project.http;

import com.maki.project.model.bean.GankResultBean;
import com.maki.project.model.bean.Meizi;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * gank.io http接口
 */
public interface IGankRetrofit {
    public static final String HOST = "http://gank.io/api/";
    public static final int MEIZI_SIZE = 20;
    public static final int GANK_SIZE = 40;


    // http://gank.io/api/data/数据类型/请求个数/第几页
    @GET("data/福利/"+MEIZI_SIZE+"/{page}")
    Observable<GankResultBean<Meizi>> getMeiziData(@Path("page") int page);
}
