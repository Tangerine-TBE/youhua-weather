package com.example.tianqi.presenter.views;

import com.example.tianqi.model.bean.LocationBean;

import java.util.List;

/**
 * @author: Administrator
 * @date: 2020/7/4 0004
 */
public interface ICityCallback {

    void addState(boolean state);

    void deleteState(boolean state);

    void updateState(boolean state);

    void onLoadSuccess(List<LocationBean> albumList);

    void onOverLimit();

    void onAgainAdd();
}
