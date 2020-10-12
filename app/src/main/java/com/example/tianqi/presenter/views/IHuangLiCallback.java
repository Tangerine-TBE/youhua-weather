package com.example.tianqi.presenter.views;

import com.example.tianqi.base.IBaseCallback;
import com.example.tianqi.model.bean.HuangLiBean;

public interface IHuangLiCallback extends IBaseCallback {

    void onLoadHuangLi(HuangLiBean huangLiBean);

    void onLoadHuangLiError();
}
