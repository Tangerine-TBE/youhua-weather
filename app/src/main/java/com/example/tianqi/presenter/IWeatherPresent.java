package com.example.tianqi.presenter;

import com.example.tianqi.base.IBasePresent;
import com.example.tianqi.presenter.views.IWeatherCallback;

public interface IWeatherPresent extends IBasePresent<IWeatherCallback> {

    //获取实时天气
    void getRealTimeWeatherInfo(double Longitude,double Latitude);

    //获取天级天气
    void getDayWeatherInfo(double Longitude,double Latitude);

    //获取小时级天气
    void getHourWeatherInfo(double Longitude,double Latitude);

    //获取降雨信息
    void getRainWeatherInfo(double Longitude,double Latitude);

    //获取预警信息
    void getWaringWeatherInfo(double Longitude,double Latitude);


    //获取生活指数
    void getLifeWeatherInfo(double Longitude,double Latitude);

    void pullToRefresh();

}
