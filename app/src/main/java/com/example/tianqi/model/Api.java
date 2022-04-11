package com.example.tianqi.model;


import com.example.module_ad.bean.AdBean;
import com.example.tianqi.model.bean.AddressBean;
import com.example.tianqi.model.bean.DayWeatherBean;
import com.example.tianqi.model.bean.HourWeatherBean;
import com.example.tianqi.model.bean.HuangLiBean;
import com.example.tianqi.model.bean.LoginBean;
import com.example.tianqi.model.bean.RainWeatherBean;
import com.example.tianqi.model.bean.RealtimeWeatherBean;
import com.example.tianqi.model.bean.RegisterBean;
import com.example.tianqi.model.bean.ThirdlyRegisterBean;
import com.example.tianqi.model.bean.WarningBean;
import com.example.tianqi.model.bean.WeiXinBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface Api {

        @GET
        Call<RealtimeWeatherBean> getRealtimeWeather(@Url String url);

        @GET
        Call<DayWeatherBean> getDayWeather(@Url String url);

        @GET
        Call<AddressBean> getAddress(@Url String city);

        @GET
        Call<HourWeatherBean> getHourWeather(@Url String hour);

        @GET
        Call<RainWeatherBean> getRainWeather(@Url String rain);



        @POST("/api.php")
        Call<RegisterBean> getVerCode(@QueryMap Map<String,Object> params);

        @POST("/api.php")
        Call<RegisterBean> toRegister(@QueryMap Map<String,Object> params);

        @POST("/api.php")
        Call<LoginBean> toLogin(@QueryMap Map<String,Object> params);

        @POST("/api.php")
        Call<RegisterBean> toFindPwd(@QueryMap Map<String,Object> params);

        @POST("/api.php")
        Call<LoginBean> toThirdlyLogin(@QueryMap Map<String,Object> params);

        @POST("/api.php")
        Call<ThirdlyRegisterBean> toThirdlyRegister(@QueryMap Map<String,Object> params);

        @POST("/api.php")
        Call<RegisterBean> toLogout(@QueryMap Map<String,Object> params);


        @POST("/api.php")
        Call<RegisterBean> checkRegister(@QueryMap Map<String,Object> params);

        @GET("access_token")
        Call<WeiXinBean> toWxAccredit(@QueryMap Map<String,String> params);


        @GET("youhuaWeather")
        Observable<AdBean> getAdMessage(@QueryMap  Map<String,String> params);

        @GET("laohuangli/d")
        Call<HuangLiBean> getHuangLi(@Query("key") String key,@Query("date") String day);

        @GET("huangli/date")
        Observable<HuangLiBean> getHuangLiCache(@Header("Authorization") String key, @Query("day") String day, @Query("month") String month, @Query("year") String year);

        //生活指数
        @POST("index")
        Call<ResponseBody> getMjLifeWeather(@Query("lon") String lon, @Query("lat") String lat);

        @POST("alert")
        Call<WarningBean> getWarningWeather(@Query("lon") String lon, @Query("lat") String lat);


}
