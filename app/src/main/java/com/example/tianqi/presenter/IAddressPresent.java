package com.example.tianqi.presenter;

import com.example.tianqi.base.IBasePresent;
import com.example.tianqi.presenter.views.IAddressCallback;

/**
 * @author: Administrator
 * @date: 2020/7/4 0004
 */
public interface IAddressPresent extends IBasePresent<IAddressCallback> {
    void getLocationAddress(String city);

}
