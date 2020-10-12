package com.example.tianqi.presenter;

import com.example.tianqi.base.IBasePresent;
import com.example.tianqi.presenter.views.IRegisterCallback;

import java.util.Map;

public interface IRegisterPresent extends IBasePresent<IRegisterCallback> {

    void getVerificationCode(String phoneNumber);

    void registerNumber(Map<String, String> userInfo);


}
