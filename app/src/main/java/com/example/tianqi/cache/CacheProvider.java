package com.example.tianqi.cache;


import com.example.module_ad.bean.AdBean;
import com.example.tianqi.model.bean.HuangLiBean;

import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

/**
 * @author: Administrator
 * @date: 2020/7/12 0012
 */
public interface CacheProvider {

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<AdBean>> getRepos(Observable<AdBean> adMsg, EvictProvider evictProvider);


    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<HuangLiBean>> getReposHl(Observable<HuangLiBean> hlMsg , EvictProvider evictProvider);

}
