package com.example.module_ad.base;

public interface IAdWatcher<T> {

     void showRealAd();

    void releaseAd();

    void setOnShowError(T t);

}
