package com.example.tianqi.presenter.views;

import com.example.tianqi.base.IBaseCallback;
import com.example.tianqi.model.bean.LoginBean;

public interface ILoginCallback extends IBaseCallback {

    void onLoginSuccess(LoginBean loginBean);

    void onLoginError();
}
