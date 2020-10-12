package com.example.tianqi.presenter.Impl;

import com.example.tianqi.base.BaseApplication;
import com.example.tianqi.db.WeatherCacheDao;
import com.example.tianqi.model.bean.WeatherCacheBean;
import com.example.tianqi.presenter.IWeatherCachePresent;
import com.example.tianqi.presenter.views.IWeaCacheDaoCallback;
import com.example.tianqi.presenter.views.IWeatherCacheCallback;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class WeatherCachePresentImpl implements IWeatherCachePresent, IWeaCacheDaoCallback {



    private static WeatherCachePresentImpl  sInstance;
    private final WeatherCacheDao mWeatherCacheDao;

    public static WeatherCachePresentImpl getInstance() {

        if (sInstance == null) {
            sInstance = new WeatherCachePresentImpl();
        }
        return sInstance;
    }
     private WeatherCachePresentImpl() {
         mWeatherCacheDao = WeatherCacheDao.getInstance();
         mWeatherCacheDao.registerCallback(this);
     }




    @Override
    public void addWeatherCache(WeatherCacheBean weatherCacheBean) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                if (mWeatherCacheDao != null) {
                    mWeatherCacheDao.addWeaCache(weatherCacheBean);
                }
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public void deleteWeatherCache(String city) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                if (mWeatherCacheDao != null) {
                    mWeatherCacheDao.deleteWeaCache(city);
                }
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public void getWeatherCache() {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                if (mWeatherCacheDao != null) {
                    mWeatherCacheDao.queryWeaCache();
                }
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }



    @Override
    public void addCacheSuccess(boolean isSuccess) {
        BaseApplication.getHandler().post(new Runnable() {
            @Override
            public void run() {
                for (IWeatherCacheCallback callback : mCallbacks) {
                    callback.addCacheState(isSuccess);
                }
            }
        });

    }

    @Override
    public void deleteCacheSuccess(boolean isSuccess) {

    }

    @Override
    public void onWeaCacheList(List<WeatherCacheBean> list) {
        BaseApplication.getHandler().post(new Runnable() {
            @Override
            public void run() {
                for (IWeatherCacheCallback callback : mCallbacks) {
                    callback.onLoadCacheSuccess(list);
                }
            }
        });
    }

    private List<IWeatherCacheCallback> mCallbacks=new ArrayList<>();

    @Override
    public void registerCallback(IWeatherCacheCallback iWeatherCacheCallback) {
        if (!mCallbacks.contains(iWeatherCacheCallback)) {
            mCallbacks.add(iWeatherCacheCallback);
        }
    }

    @Override
    public void unregisterCallback(IWeatherCacheCallback iWeatherCacheCallback) {
            mCallbacks.remove(iWeatherCacheCallback);
    }
}
