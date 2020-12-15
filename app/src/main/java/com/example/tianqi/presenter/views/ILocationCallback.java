package com.example.tianqi.presenter.views;

import com.example.tianqi.model.bean.LocationBean;

import java.util.List;

/**
 * @author: Administrator
 * @date: 2020/7/4 0004
 */
public interface ILocationCallback  {

    void addSuccess(boolean isSuccess);

    void deleteSuccess(boolean isSuccess);

    void onCityList(List<LocationBean> list);

    void updateSuccess(boolean isSuccess);

    void updateLocationSuccess(boolean isSuccess);

}
