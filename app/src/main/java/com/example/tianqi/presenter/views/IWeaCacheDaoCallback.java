package com.example.tianqi.presenter.views;

import com.example.tianqi.model.bean.WeatherCacheBean;

import java.util.List;

public interface IWeaCacheDaoCallback {

    void addCacheSuccess(boolean isSuccess);

    void deleteCacheSuccess(boolean isSuccess);

    void onWeaCacheList(List<WeatherCacheBean> list);

}
