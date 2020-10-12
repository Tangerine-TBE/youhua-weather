package com.example.tianqi.presenter;

import com.example.tianqi.base.IBasePresent;
import com.example.tianqi.presenter.views.ILoginCallback;

import java.util.Map;

public interface ILoginPresent extends IBasePresent<ILoginCallback> {

    void toLogin(Map<String, String> userInfo);

}
