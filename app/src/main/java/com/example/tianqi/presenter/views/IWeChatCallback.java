package com.example.tianqi.presenter.views;

import com.example.tianqi.base.IBaseCallback;
import com.example.tianqi.model.bean.LoginBean;
import com.example.tianqi.model.bean.RegisterBean;
import com.example.tianqi.model.bean.ThirdlyRegisterBean;
import com.example.tianqi.model.bean.WeiXinBean;

public interface IWeChatCallback extends IBaseCallback {

  void onWxAccreditSuccess(WeiXinBean weiXinBean);

  void onWxAccreditError();

  void onCheckWxRegisterSuccess(RegisterBean bean);

  void onCheckWxError();

  void onWxRegisterSuccess(ThirdlyRegisterBean thirdlyRegisterBean);

  void onWxRegisterError();

  void onWxLoginSuccess(LoginBean loginBean);

  void onWxLoginError();


}
