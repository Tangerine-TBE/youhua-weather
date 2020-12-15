package com.example.tianqi.presenter.views;

import com.example.tianqi.base.IBaseCallback;
import com.example.tianqi.model.bean.DayWeatherBean;
import com.example.tianqi.model.bean.DescribeBean;
import com.example.tianqi.model.bean.HourWeatherBean;
import com.example.tianqi.model.bean.MjLifeBean;
import com.example.tianqi.model.bean.RainWeatherBean;
import com.example.tianqi.model.bean.WarningBean;
import com.example.tianqi.ui.custom.mj15day.WeatherModel;

import java.util.List;

public interface IWeatherCallback extends IBaseCallback {

    void onLoadRealtimeWeatherData(DescribeBean resultBean);

    void onLoadDayWeatherData(DayWeatherBean.ResultBean resultBean, List<WeatherModel> weatherModels);

    void onLoadHourWeatherData(HourWeatherBean weatherBean);

    void onLoadRainWeatherData(RainWeatherBean rainWeatherBean);

    void onLoadWarningWeatherData(WarningBean warningBean);

    void onLoadLifeWeatherData(List<MjLifeBean> beanList);

    void onRefreshSuccess();

    void onRefreshError();
}
