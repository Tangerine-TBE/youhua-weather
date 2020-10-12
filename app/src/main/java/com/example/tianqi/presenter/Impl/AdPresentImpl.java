package com.example.tianqi.presenter.Impl;


import com.example.module_ad.bean.AdBean;
import com.example.module_ad.utils.LogUtils;
import com.example.tianqi.base.BaseApplication;
import com.example.tianqi.cache.Repository;
import com.example.tianqi.presenter.IAdPresent;
import com.example.tianqi.utils.Contents;
import com.example.tianqi.utils.PackageUtil;
import com.example.tianqi.presenter.views.IAdCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache2.Reply;

public class AdPresentImpl implements IAdPresent {

    private static AdPresentImpl  sInstance;
    private final Repository mRepository;
    private Map<String, String> mMap;

    public static AdPresentImpl getInstance() {
        if (sInstance == null) {
            sInstance = new AdPresentImpl();
        }
        return sInstance;
    }
     private AdPresentImpl() {
         mRepository = Repository.init(BaseApplication.getAppContext().getFilesDir());
         mMap = new HashMap<>();
         mMap.put(Contents.AD_NAME, Contents.APP_PACKAGE);
         mMap.put(Contents.AD_VERSION, Contents.AD_VERSION_VALUES);
         mMap.put(Contents.AD_CHANNEL, PackageUtil.getAppMetaData(BaseApplication.getAppContext(), Contents.PLATFORM_KEY));
     }

    @Override
    public void toRequestAd() {
        mRepository.getAdMsg(mMap,false)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<Reply<AdBean>>() {
                    @Override
                    public void accept(Reply<AdBean> adBeanReply) throws Exception {
                        LogUtils.i(AdPresentImpl.this,"accept------------------->"+Thread.currentThread().getName());
                        if (adBeanReply != null) {
                            AdBean data = adBeanReply.getData();
                            for (IAdCallback callback : mCallbacks) {
                                callback.onLoadAdMsgSuccess(data);
                            }
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.i(AdPresentImpl.this,"Throwable------------------->"+throwable.toString());
                        for (IAdCallback callback : mCallbacks) {
                            callback.onLoadAdMsgError();
                        }
                    }
                });
    }

    private List<IAdCallback> mCallbacks=new ArrayList<>();
    @Override
    public void registerCallback(IAdCallback iAdCallback) {
        if (!mCallbacks.contains(iAdCallback)) {
            mCallbacks.add(iAdCallback);
        }
    }

    @Override
    public void unregisterCallback(IAdCallback iAdCallback) {
        mCallbacks.remove(iAdCallback);
    }
}
