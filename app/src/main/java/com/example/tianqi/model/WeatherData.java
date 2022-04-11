package com.example.tianqi.model;

import com.example.tianqi.model.bean.DayWeatherBean;
import com.example.tianqi.model.bean.HourWeatherBean;
import com.example.tianqi.model.bean.HuangLiBean;
import com.example.tianqi.model.bean.RainWeatherBean;
import com.example.tianqi.model.bean.RealtimeWeatherBean;
import com.example.tianqi.model.bean.WarningBean;
import com.example.tianqi.utils.Contents;
import com.example.tianqi.utils.RetrofitManager;
import com.example.tianqi.utils.WeatherUrl;

import java.text.SimpleDateFormat;

import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class WeatherData {

    private static WeatherData  sInstance;
    private final Api mApi;
    private double mLatitude;
    private double mLongitude;
    private final Api mApiHl;
    private final Api mApiMj;

    public static WeatherData getInstance() {
        if (sInstance == null) {
            sInstance = new WeatherData();
        }
        return sInstance;
    }
     private WeatherData() {
         Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
         mApi = retrofit.create(Api.class);
         Retrofit retrofitHuangLi = RetrofitManager.getInstance().getRetrofitHuangLi();
         mApiHl = retrofitHuangLi.create(Api.class);
         Retrofit retrofitMjWeather = RetrofitManager.getInstance().getRetrofitMjWeather();
         mApiMj = retrofitMjWeather.create(Api.class);
     }


     public void doRequestTem(double Longitude,double Latitude,Callback<RealtimeWeatherBean> callback) {

         String format = String.format(WeatherUrl.WEATHER_REAL_TIME, Longitude, Latitude);
         mApi.getRealtimeWeather(format).enqueue(callback);
     }

    public void doRequestHighAndLow(double Longitude,double Latitude,Callback<DayWeatherBean> callback) {

        String format = String.format(WeatherUrl.WEATHER_PREDICTION_DAILY, Longitude, Latitude);
        mApi.getDayWeather(format).enqueue(callback);
    }

    public void doRequestHour(double Longitude,double Latitude,Callback<HourWeatherBean> callback) {

        String format = String.format(WeatherUrl.WEATHER_PREDICTION_HOURLY, Longitude, Latitude);
        mApi.getHourWeather(format).enqueue(callback);
    }

    public void  doRequestRain(double longitude,double latitude,Callback<RainWeatherBean> callback) {
        String format = String.format(WeatherUrl.WEATHER_PREDICTION_RAIN, longitude, latitude);
        mApi.getRainWeather(format).enqueue(callback);
    }

    public void  doRequestWarning(double longitude,double latitude,Callback<WarningBean> callback) {
        mApiMj.getWarningWeather(longitude+"",latitude+"").enqueue(callback);
    }


    public void doRequestLife(double Longitude, double Latitude, Callback<ResponseBody> callback) {
        mApiMj.getMjLifeWeather(Longitude+"",Latitude+"").enqueue(callback);
    }

    public void doRequestHl(Callback<HuangLiBean> callback) {
        mApiHl.getHuangLi(Contents.HUANG_LI_KEY,new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis())).enqueue(callback);
    }


}
