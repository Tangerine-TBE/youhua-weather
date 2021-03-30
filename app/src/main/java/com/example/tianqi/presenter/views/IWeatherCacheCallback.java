package com.example.tianqi.presenter.views;

import com.example.tianqi.model.bean.WeatherCacheBean;

import java.util.List;

public interface IWeatherCacheCallback {

    void addCacheState(boolean state);

    void deleteCacheState(boolean state);

    void onLoadCacheSuccess(List<WeatherCacheBean> cacheBeanList);


}
