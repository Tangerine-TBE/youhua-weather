package com.example.tianqi.presenter.views;

import com.example.tianqi.base.IBaseCallback;
import com.example.tianqi.model.bean.LoginBean;
import com.example.tianqi.model.bean.RegisterBean;
import com.example.tianqi.model.bean.ThirdlyRegisterBean;

public interface IThirdlyLoginCallback extends IBaseCallback {

      void onThirdlyLoginSuccess(LoginBean bean);

      void onThirdlyLoginError();


      void onCheckThirdlyRegisterSuccess(RegisterBean bean);

      void onCheckError();


      void onThirdlyRegisterSuccess(ThirdlyRegisterBean bean);

      void onThirdlyRegisterError();
}
