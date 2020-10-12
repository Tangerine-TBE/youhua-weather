package com.example.tianqi.db;

import com.example.tianqi.base.IBasePresent;
import com.example.tianqi.model.bean.WeatherCacheBean;
import com.example.tianqi.presenter.views.IWeaCacheDaoCallback;

public interface IWeatherCacheDao extends IBasePresent<IWeaCacheDaoCallback> {

    void addWeaCache(WeatherCacheBean weatherCacheBean);

    void deleteWeaCache(String city);

    void queryWeaCache();

}
