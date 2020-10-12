package com.example.tianqi.presenter.views;

import com.example.tianqi.base.IBaseCallback;
import com.example.tianqi.model.bean.LocationBean;

/**
 * @author: Administrator
 * @date: 2020/7/4 0004
 */
public interface IAddressCallback extends IBaseCallback {
    void onLoadAddressSuccess(LocationBean addressBean);
}
