package com.example.tianqi.presenter;

import com.example.tianqi.base.IBasePresent;
import com.example.tianqi.presenter.views.IFindPwdCallback;

import java.util.Map;

public interface IFindPwdPresent extends IBasePresent<IFindPwdCallback> {

    void getVerificationCode(String phoneNumber);

    void findPwd(Map<String, String> userInfo);

}
