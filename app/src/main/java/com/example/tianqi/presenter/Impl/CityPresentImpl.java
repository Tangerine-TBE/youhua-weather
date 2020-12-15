package com.example.tianqi.presenter.Impl;

import android.text.TextUtils;

import com.example.tianqi.base.BaseApplication;
import com.example.tianqi.db.LocationDao;
import com.example.tianqi.model.bean.LocationBean;
import com.example.tianqi.presenter.ICityPresent;
import com.example.tianqi.utils.Contents;
import com.example.tianqi.presenter.views.ICityCallback;
import com.example.tianqi.presenter.views.ILocationCallback;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: Administrator
 * @date: 2020/7/4 0004
 */
public class CityPresentImpl implements ICityPresent, ILocationCallback {

    private static volatile CityPresentImpl singleton = null;
    private final LocationDao mDao;
    private List<LocationBean> mLimitSize=null;
    private boolean mIsHave;
    private boolean mScroll=true;

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    private int mPosition;


    private CityPresentImpl() {
        mDao = LocationDao.getInstance();
        mDao.registerCallback(this);
        mLimitSize =new ArrayList<>();

    }

    public static CityPresentImpl getInstance() {
                if (singleton == null) {
                    singleton = new CityPresentImpl();
                }
        return singleton;
    }


    public boolean isScroll() {
        return mScroll;
    }

    public void setScroll(boolean scroll) {
        mScroll = scroll;
    }


    @Override
    public void addDataToSQLite(LocationBean bean) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                mIsHave = false;
                if (mDao != null&bean!=null) {

                        for (LocationBean locationBean : mDao.getCityList()) {
                            if (locationBean==null) {
                                return;
                            }
                            String city = locationBean.getCity();
                            if (TextUtils.isEmpty(city)) {return;}
                          //  LogUtils.i(CityPresentImpl.this,"---------------------->"+city);
                                if (city.equals(bean.getCity())) {
                                    mIsHave = true;

                                }
                        }
                        if (!mIsHave) {
                            if (mLimitSize.size() >= Contents.LIMIT_SIZE) {
                                onLimitAdd();
                            } else {
                                mDao.addCity(bean);
                                mScroll=false;
                            }
                          //  LogUtils.i(CityPresentImpl.this,"-----------添加成功----------->"+mIsHave);
                        }else {
                         //   LogUtils.i(CityPresentImpl.this,"-----------添加過了----------->"+mIsHave);
                            againAdd();
                        }
                }
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }


    @Override
    public void deleteDataFromSQLite(LocationBean bean,int position) {
        this.mPosition=position;
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                if (mDao != null) {
                    mDao.deleteCity(bean);
                }
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public void updateDataToSQLite(LocationBean bean) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                if (mDao != null) {
                    mDao.updateCity(bean);
                }
            }
        }).subscribeOn(Schedulers.io()).subscribe();

    }

    @Override
    public void updateLocationToSQLite(LocationBean bean,LocationBean lastCity) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                if (mDao != null) {
                    mDao.updateLocationCity(bean,lastCity);
                }
            }
        }).subscribeOn(Schedulers.io()).subscribe();

    }

    @Override
    public void getCityData() {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                if (mDao != null) {
                    mDao.getSelectCity();
                }
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public void onLimitAdd() {
        BaseApplication.getHandler().post(new Runnable() {
            @Override
            public void run() {
                for (ICityCallback callback : mCallbacks) {
                    callback.onOverLimit();
                }
            }
        });
    }

    @Override
    public void againAdd() {
        BaseApplication.getHandler().post(new Runnable() {
            @Override
            public void run() {
                for (ICityCallback callback : mCallbacks) {
                    callback.onAgainAdd();
                }
            }
        });
    }


    private List<ICityCallback> mCallbacks=new ArrayList<>();

    @Override
    public void registerCallback(ICityCallback iCityCallback) {
        if (!mCallbacks.contains(iCityCallback)) {
            mCallbacks.add(iCityCallback);
        }
    }

    @Override
    public void unregisterCallback(ICityCallback iCityCallback) {
        mCallbacks.remove(iCityCallback);
    }

    @Override
    public void addSuccess(boolean isSuccess) {
        getCityData();
        BaseApplication.getHandler().post(new Runnable() {
            @Override
            public void run() {
                for (ICityCallback callback : mCallbacks) {
                    callback.addState(isSuccess);
                }
            }
        });

    }

    @Override
    public void deleteSuccess(boolean isSuccess) {
        getCityData();
        BaseApplication.getHandler().post(new Runnable() {
            @Override
            public void run() {
                for (ICityCallback callback : mCallbacks) {
                    callback.deleteState(isSuccess);
                }
            }
        });
    }

    @Override
    public void onCityList(List<LocationBean> list) {
        this.mLimitSize =list;
        BaseApplication.getHandler().post(new Runnable() {
            @Override
            public void run() {
                for (ICityCallback callback : mCallbacks) {
                    callback.onLoadSuccess(list);
                }
            }
        });
    }

    @Override
    public void updateSuccess(boolean isSuccess) {
       // getCityData();
    }

    @Override
    public void updateLocationSuccess(boolean isSuccess) {
        getCityData();
        BaseApplication.getHandler().post(new Runnable() {
            @Override
            public void run() {
                for (ICityCallback callback : mCallbacks) {
                    callback.updateState(isSuccess);
                }
            }
        });
    }


}
