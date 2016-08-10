package com.app.learn.HttpUtil;

import com.app.learn.model.ChinaCalendar;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Codpoe on 2016/5/5.
 */
public class HttpMethods {

    public static final String BASE_URL = "http://v.juhe.cn/laohuangli/";
    public static final String APP_KEY = "743692bc90246c4f89643e9026c6f707";

    private Retrofit mRetrofit;
    private ChinaCalendarApi mChinaCalendarApi;

    //构造方法私有化
    private HttpMethods() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mChinaCalendarApi = mRetrofit.create(ChinaCalendarApi.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    /**
     * 给外部调用的方法
     */
    //获取单例
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取老黄历
     * @param subscriber 由调用者传过来的观察者对象
     * @param date 由调用者传过来的日期
     */
    public void getChinaCalendar(Subscriber<ChinaCalendar> subscriber, String date) {
        mChinaCalendarApi.getChinaCalendar(date, APP_KEY)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
