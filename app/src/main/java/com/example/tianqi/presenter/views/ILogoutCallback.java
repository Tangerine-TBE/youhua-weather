package com.example.tianqi.presenter.views;

import com.example.tianqi.base.IBaseCallback;
import com.example.tianqi.model.bean.RegisterBean;

public interface ILogoutCallback extends IBaseCallback {

    void onLogoutSuccess(RegisterBean registerBean);

    void onLogoutError();
}
