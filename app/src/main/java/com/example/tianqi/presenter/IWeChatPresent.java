package com.example.tianqi.presenter;

import com.example.tianqi.base.IBasePresent;
import com.example.tianqi.presenter.views.IWeChatCallback;

import java.util.Map;

public interface IWeChatPresent extends IBasePresent<IWeChatCallback> {

    void toWxAccredit(Map<String, String> map);

    void checkWxRegister(Map<String, String> userInfo);

    void toWxRegister(Map<String, String> map);

    void toWxLogin(Map<String, String> map);
}
