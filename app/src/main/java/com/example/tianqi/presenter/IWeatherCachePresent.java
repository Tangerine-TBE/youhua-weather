package com.example.tianqi.presenter;

import com.example.tianqi.base.IBasePresent;
import com.example.tianqi.model.bean.WeatherCacheBean;
import com.example.tianqi.presenter.views.IWeatherCacheCallback;

public interface IWeatherCachePresent extends IBasePresent<IWeatherCacheCallback> {

    //添加天气缓存
    void  addWeatherCache(WeatherCacheBean weatherCacheBean);


    // 删除天气缓存
    void deleteWeatherCache(String city);

    //获取天气缓存
    void getWeatherCache();



}
