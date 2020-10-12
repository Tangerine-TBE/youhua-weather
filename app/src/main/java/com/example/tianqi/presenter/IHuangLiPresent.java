package com.example.tianqi.presenter;

import com.example.tianqi.base.IBasePresent;
import com.example.tianqi.presenter.views.IHuangLiCallback;

public interface IHuangLiPresent extends IBasePresent<IHuangLiCallback> {

    void getHuangLi(String day,String month,String year);
}
