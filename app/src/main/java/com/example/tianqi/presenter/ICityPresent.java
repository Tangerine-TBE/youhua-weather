package com.example.tianqi.presenter;

import com.example.tianqi.base.IBasePresent;
import com.example.tianqi.model.bean.LocationBean;
import com.example.tianqi.presenter.views.ICityCallback;


/**
 * @author: Administrator
 * @date: 2020/7/4 0004
 */
public interface ICityPresent extends IBasePresent<ICityCallback> {

    //添加城市数据
    void addDataToSQLite(LocationBean bean);

    //删除城市数据
    void deleteDataFromSQLite(LocationBean bean,int position);

    //更新数据
    void updateDataToSQLite(LocationBean bean);

    //更新本地城市
    void updateLocationToSQLite(LocationBean bean,LocationBean lastCity);



    //获取城市数据
    void getCityData();

    //限制城市添加数量
    void onLimitAdd();

    //重复添加城市
    void againAdd();

}
