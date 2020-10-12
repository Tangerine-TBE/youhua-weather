package com.example.tianqi.presenter;

import com.example.tianqi.base.IBasePresent;
import com.example.tianqi.presenter.views.ILogoutCallback;

public interface ILogoutPresent extends IBasePresent<ILogoutCallback> {

    void toLogout(String id);
}
