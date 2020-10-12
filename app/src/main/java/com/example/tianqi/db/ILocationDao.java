package com.example.tianqi.db;

import com.example.tianqi.base.IBasePresent;
import com.example.tianqi.model.bean.LocationBean;
import com.example.tianqi.presenter.views.ILocationCallback;

import java.util.List;

/**
 * @author: Administrator
 * @date: 2020/7/4 0004
 */
public interface ILocationDao extends IBasePresent<ILocationCallback> {

    void addCity(LocationBean locationBean);

    void deleteCity(LocationBean locationBean);

    void getSelectCity();

    void updateCity(LocationBean locationBean);

    void updateLocationCity(LocationBean locationBean);

   List<LocationBean> getCityList();







}
