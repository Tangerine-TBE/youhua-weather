package com.example.tianqi.presenter;

import com.example.tianqi.base.IBasePresent;
import com.example.tianqi.presenter.views.IAdCallback;

public interface IAdPresent extends IBasePresent<IAdCallback> {

    void toRequestAd();
}
