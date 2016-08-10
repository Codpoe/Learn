package com.app.learn.HttpUtil;

import com.app.learn.model.ChinaCalendar;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Codpoe on 2016/5/5.
 */
public interface ChinaCalendarApi {
    @GET("d")
    Observable<ChinaCalendar> getChinaCalendar(@Query("date") String date, @Query("key") String key);
}
