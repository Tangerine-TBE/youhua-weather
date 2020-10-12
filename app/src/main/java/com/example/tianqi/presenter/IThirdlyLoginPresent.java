package com.example.tianqi.presenter;

import com.example.tianqi.base.IBasePresent;
import com.example.tianqi.presenter.views.IThirdlyLoginCallback;

import java.util.Map;

public interface IThirdlyLoginPresent extends IBasePresent<IThirdlyLoginCallback> {

    void toThirdlyRegister(Map<String, String> userInfo);

    void checkRegister(Map<String, String> userInfo);

    void toThirdlyLogin(Map<String, String> userInfo);



}
