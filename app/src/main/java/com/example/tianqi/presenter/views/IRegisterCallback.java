package com.example.tianqi.presenter.views;

import com.example.tianqi.base.IBaseCallback;
import com.example.tianqi.model.bean.RegisterBean;

public interface IRegisterCallback extends IBaseCallback {

    void onLoadCode();

    void onRegisterSuccess(RegisterBean registerBean);

}
