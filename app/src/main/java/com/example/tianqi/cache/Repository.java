package com.example.tianqi.cache;


import com.example.module_ad.bean.AdBean;
import com.example.tianqi.model.Api;
import com.example.tianqi.model.bean.HuangLiBean;
import com.example.tianqi.utils.WeatherUrl;

import java.io.File;
import java.util.Map;

import io.reactivex.Observable;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.Reply;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author: Administrator
 * @date: 2020/7/12 0012
 */
public class Repository {
    //调用此处，用来生成一个缓存文件
    public static Repository init(File cacheDir) {
        return new Repository(cacheDir);
    }

    private final CacheProvider cacheProvider;
    private final Api restApi;
    private final Api restApiHL;

    public Repository(File cacheDir) {

        cacheProvider = new RxCache.Builder()
                .persistence(cacheDir, new GsonSpeaker())
                .using(CacheProvider.class);


        restApi = new Retrofit.Builder()
                .baseUrl(WeatherUrl.AD_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Api.class);


        restApiHL = new Retrofit.Builder()
                .baseUrl(WeatherUrl.HUANG_LI)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Api.class);
    }


    //真正进行数据请求和缓存的接口
    public Observable<Reply<AdBean>> getAdMsg(Map<String,String> params, final boolean update) {
        return cacheProvider.getRepos(restApi.getAdMessage(params),new EvictProvider(update));
    }

    public Observable<Reply<HuangLiBean>> getHlMsg(String key,String day,String month,String year, final boolean update) {
        return cacheProvider.getReposHl(restApiHL.getHuangLiCache(key,day,month,year),new EvictProvider(update));
    }


}
