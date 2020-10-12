package com.example.tianqi.presenter.Impl;

import com.example.tianqi.base.BaseApplication;
import com.example.tianqi.cache.Repository;
import com.example.tianqi.model.WeatherData;
import com.example.tianqi.model.bean.HuangLiBean;
import com.example.tianqi.presenter.IHuangLiPresent;
import com.example.tianqi.presenter.views.IHuangLiCallback;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HuangLiPresentImpl implements IHuangLiPresent {

    private final Repository mRepository;
    private final WeatherData mData;

     public HuangLiPresentImpl() {
         mData = WeatherData.getInstance();
         mRepository = Repository.init(BaseApplication.getAppContext().getFilesDir());
     }

    @Override
    public void getHuangLi(String day,String month,String year) {

        mData.doRequestHl(day,month,year,new Callback<HuangLiBean>() {
            @Override
            public void onResponse(Call<HuangLiBean> call, Response<HuangLiBean> response) {
                if (response.code()== HttpURLConnection.HTTP_OK) {
                    HuangLiBean body = response.body();
                    if (body != null) {
                        for (IHuangLiCallback callback : mCallbacks) {
                            callback.onLoadHuangLi(body);
                        }

                }
                }

            }

            @Override
            public void onFailure(Call<HuangLiBean> call, Throwable t) {
                for (IHuangLiCallback callback : mCallbacks) {
                    callback.onLoadHuangLiError();
                }

            }
        });


    }
    private List<IHuangLiCallback> mCallbacks=new ArrayList<>();

    @Override
    public void registerCallback(IHuangLiCallback iHuangLiCallback) {
        if (!mCallbacks.contains(iHuangLiCallback)) {
            mCallbacks.add(iHuangLiCallback);
        }

    }

    @Override
    public void unregisterCallback(IHuangLiCallback iHuangLiCallback) {
        mCallbacks.remove(iHuangLiCallback);
    }
}
