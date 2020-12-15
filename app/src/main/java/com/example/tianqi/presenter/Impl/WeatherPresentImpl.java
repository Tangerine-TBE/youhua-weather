package com.example.tianqi.presenter.Impl;

import com.alibaba.fastjson.JSON;
import com.example.module_ad.utils.LogUtils;
import com.example.tianqi.model.WeatherData;
import com.example.tianqi.model.bean.DayWeatherBean;
import com.example.tianqi.model.bean.DescribeBean;
import com.example.tianqi.model.bean.HourWeatherBean;
import com.example.tianqi.model.bean.LifeBean;
import com.example.tianqi.model.bean.MjLifeBean;
import com.example.tianqi.model.bean.RainWeatherBean;
import com.example.tianqi.model.bean.RealtimeWeatherBean;
import com.example.tianqi.model.bean.WarningBean;
import com.example.tianqi.presenter.IWeatherPresent;
import com.example.tianqi.presenter.views.IWeatherCallback;
import com.example.tianqi.ui.custom.mj15day.WeatherModel;
import com.example.tianqi.utils.DateUtil;
import com.example.tianqi.utils.WeatherUtils;
import com.tiantian.tianqi.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherPresentImpl implements IWeatherPresent {


    private final DescribeBean mDescribeBean;
    List<DescribeBean.Des> mDesList = new ArrayList<>();
    List<LifeBean> mLifeBeans = new ArrayList<>();
    List<WeatherModel> m15DayWeatherList = new ArrayList<>();
    private final WeatherData mWeatherData;
    private double mLongitude;
    private double mLatitude;


    public WeatherPresentImpl() {
        mDescribeBean = new DescribeBean();
        mWeatherData = WeatherData.getInstance();
    }


    //请求实时天气
    @Override
    public void getRealTimeWeatherInfo(double Longitude, double Latitude) {
        if (Longitude == 0) {
            return;
        }
        handlerLoading();
        this.mLongitude = Longitude;
        this.mLatitude = Latitude;
        doRequestRealTimeWeather(Longitude, Latitude, false);
    }

    private List<LifeBean> getLife(DayWeatherBean.ResultBean resultBean) {
        //生活指数数据
        mLifeBeans.clear();
        DayWeatherBean.ResultBean.DailyBean.LifeIndexBean life_index = resultBean.getDaily().getLife_index();
        String Ultraviolet = life_index.getUltraviolet().get(0).getDesc();
        String Comfort = life_index.getComfort().get(0).getDesc();
        String CarWashing = life_index.getCarWashing().get(0).getDesc();
        String ColdRisk = life_index.getColdRisk().get(0).getDesc();

        mLifeBeans.add(new LifeBean(R.mipmap.ultraviolet_ray, "紫外线", Ultraviolet));
        mLifeBeans.add(new LifeBean(R.mipmap.comfort_level, "舒适度", Comfort));
        mLifeBeans.add(new LifeBean(R.mipmap.wash_car, "洗车", CarWashing));
        mLifeBeans.add(new LifeBean(R.mipmap.cold, "感冒", ColdRisk));

        return mLifeBeans;
    }


    private List<WeatherModel>  get15Day(DayWeatherBean.ResultBean resultBean) {
     /*   DayWeatherBean.ResultBean.DailyBean daily = resultBean.getDaily();
        String week = null;
        for (int i = 0; i < daily.getSkycon().size(); i++) {
            DayWeatherBean.ResultBean.DailyBean.AstroBean astroBean = daily.getAstro().get(i);
            String date = astroBean.getDate().substring(0, 10);
            DayWeatherBean.ResultBean.DailyBean.SkyconBean skyconBean = daily.getSkycon().get(i);
            DayWeatherBean.ResultBean.DailyBean.TemperatureBean temperatureData = daily.getTemperature().get(i);
            DayWeatherBean.ResultBean.DailyBean.AirQualityBean.AqiBean aqiBean = daily.getAir_quality().getAqi().get(i);

            if (i == 0) {
                week = "今天";
            } else if (i == 1) {
                week = "明天";
            } else {
                week = DateUtil.getWeek(date);
            }
            WeatherModel weather15 = new WeatherModel();
            weather15.setDate(DateUtil.StrToData(date));
            weather15.setWeek(week);
            weather15.setDayWeather(WeatherUtils.weatherPhenomena(skyconBean.getValue()));
            weather15.setDayTemp(((int) temperatureData.getMax()));
            weather15.setNightTemp((int) temperatureData.getMin());
            weather15.setDayPic(WeatherUtils.weatherIcon(skyconBean.getValue()));
            weather15.setNightPic(WeatherUtils.weatherIcon(skyconBean.getValue()));
            weather15.setNightWeather(WeatherUtils.weatherPhenomena(skyconBean.getValue()));
            weather15.setAirLevel(WeatherUtils.aqiState((int) aqiBean.getAvg().getChn()));
            m15DayWeatherList.add(weather15);
        }*/
        return m15DayWeatherList;
    }


    //封装天气情况描述
    private DescribeBean getInfo(RealtimeWeatherBean.ResultBean resultBean) {
        mDesList.clear();
        //实时温度
        RealtimeWeatherBean.ResultBean.RealtimeBean realtime = resultBean.getRealtime();
        int temperature = (int) realtime.getTemperature();
        mDescribeBean.setTem(WeatherUtils.addTemSymbol(temperature));
        //天气情况
        String skycon = realtime.getSkycon();
        mDescribeBean.setSky((WeatherUtils.weatherPhenomena(skycon)));
        //湿度
        String humiditySymbol = WeatherUtils.addHumiditySymbol(realtime.getHumidity());
        mDesList.add(new DescribeBean.Des("湿度", humiditySymbol));
        //气压
        String pres = WeatherUtils.preType(realtime.getPressure());
       // LogUtils.i(this, "气压------------>" + pres);
        mDesList.add(new DescribeBean.Des("气压", pres));

        //舒适度
        RealtimeWeatherBean.ResultBean.RealtimeBean.LifeIndexBean life_index = realtime.getLife_index();
        String desc = life_index.getComfort().getDesc();
        mDesList.add(new DescribeBean.Des("舒适度", desc));

        //空气质量
        RealtimeWeatherBean.ResultBean.RealtimeBean.AirQualityBean air_quality = realtime.getAir_quality();
        String chn = air_quality.getDescription().getChn();
        mDesList.add(new DescribeBean.Des("空气质量", chn));


        //风力风向
        RealtimeWeatherBean.ResultBean.RealtimeBean.WindBean wind = realtime.getWind();
        double direction = wind.getDirection();
        double speed = wind.getSpeed();
        mDesList.add(new DescribeBean.Des( WeatherUtils.winDirection(direction), WeatherUtils.winType(speed,true) ));
      //  LogUtils.i(this, "风力风向------------>" + direction+"---------"+speed);

        //紫外线
        RealtimeWeatherBean.ResultBean.RealtimeBean.LifeIndexBean.UltravioletBean ultraviolet = life_index.getUltraviolet();
        String desc1 = ultraviolet.getDesc();
       // LogUtils.i(this, "紫外线------------>" + desc1);
        mDesList.add(new DescribeBean.Des("紫外线", desc1));

        //体感温度
        int apparent_temperature = (int)realtime.getApparent_temperature();
        mDesList.add(new DescribeBean.Des("体感温度", WeatherUtils.addTemSymbol(apparent_temperature)));

        //能见度
        double visibility = realtime.getVisibility();
        mDesList.add(new DescribeBean.Des("能见度", visibility+"km"));


        //空气质量详情
        mDescribeBean.setAirQuality(air_quality);


        mDescribeBean.setDes(mDesList);
        return mDescribeBean;
    }

    //请求天级天气
    @Override
    public void getDayWeatherInfo(double Longitude,double Latitude) {
        if (Longitude ==0) {
            return;
        }
        doRequestDayWeather( Longitude, Latitude,false);
    }

    //请求小时天气
    @Override
    public void getHourWeatherInfo(double Longitude,double Latitude) {
            mWeatherData.doRequestHour(Longitude,Latitude, new Callback<HourWeatherBean>() {
                @Override
                public void onResponse(Call<HourWeatherBean> call, Response<HourWeatherBean> response) {
                    if (response.code()== HttpURLConnection.HTTP_OK) {
                        HourWeatherBean body = response.body();
                        if (body != null) {
                            for (IWeatherCallback callback : mCallbacks) {
                                callback.onLoadHourWeatherData(body);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<HourWeatherBean> call, Throwable t) {
                    for (IWeatherCallback callback : mCallbacks) {
                        callback.onError();
                    }
                }
            });
    }

    //降雨信息
    @Override
    public void getRainWeatherInfo(double longitude, double latitude) {
        mWeatherData.doRequestRain(longitude, latitude, new Callback<RainWeatherBean>() {
            @Override
            public void onResponse(Call<RainWeatherBean> call, Response<RainWeatherBean> response) {
                if (response.code()== HttpURLConnection.HTTP_OK) {
                    RainWeatherBean body = response.body();
                    if (body != null) {
                        for (IWeatherCallback callback : mCallbacks) {
                            callback.onLoadRainWeatherData(body);
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<RainWeatherBean> call, Throwable t) {
            }
        });
    }

    @Override
    public void getWaringWeatherInfo(double Longitude, double Latitude) {
        mWeatherData.doRequestWarning(Longitude, Latitude, new Callback<WarningBean>() {
            @Override
            public void onResponse(Call<WarningBean> call, Response<WarningBean> response) {
                if (response.code()== HttpURLConnection.HTTP_OK) {
                    WarningBean body = response.body();
                    if (body != null) {
                        for (IWeatherCallback callback : mCallbacks) {
                            callback.onLoadWarningWeatherData(body);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<WarningBean> call, Throwable t) {

            }
        });
    }

    @Override
    public void getLifeWeatherInfo(double longitude, double latitude) {
        mWeatherData.doRequestLife(longitude, latitude, new Callback<ResponseBody>() {

            private List<MjLifeBean> mMjLifeBeans;

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code()== HttpURLConnection.HTTP_OK) {
                    try {
                        String string = response.body().string();
                        JSONObject jsonObject = new JSONObject(string);
                        JSONObject data = jsonObject.optJSONObject("data");
                        JSONObject liveIndex = data.getJSONObject("liveIndex");
                        JSONArray jsonArray = liveIndex.getJSONArray(DateUtil.getDate());
                        mMjLifeBeans = JSON.parseArray(jsonArray.toString(), MjLifeBean.class);
                        LogUtils.i(WeatherPresentImpl.this,"----getLifeWeatherInfo------------->"+jsonArray.toString());
                        for (IWeatherCallback callback : mCallbacks) {
                            callback.onLoadLifeWeatherData(mMjLifeBeans);
                        }
                    } catch (Exception e) {
                        LogUtils.i(WeatherPresentImpl.this,"----getLifeWeatherInfo------------->"+e .toString());
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    //下拉刷新
    @Override
    public void pullToRefresh() {
          LogUtils.i(this, "经度------------>" + mLongitude+"纬度---------"+mLatitude);
        doRequestRealTimeWeather(mLongitude,mLatitude,true);
        doRequestDayWeather(mLongitude,mLatitude,true);
        getHourWeatherInfo(mLongitude,mLatitude);
    }


    //请求实时天气
    private void doRequestRealTimeWeather(double Longitude,double Latitude,boolean isFresh) {

        mWeatherData.doRequestTem(Longitude,Latitude, new Callback<RealtimeWeatherBean>() {
            @Override
            public void onResponse(Call<RealtimeWeatherBean> call, Response<RealtimeWeatherBean> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    RealtimeWeatherBean body = response.body();
                    if (body != null) {
                        if (isFresh) {
                            handlerRefreshSuccess();
                            RealtimeWeatherBean.ResultBean result = body.getResult();
                            if (result != null) {
                                handlerSuccess(getInfo(result));
                            }
                        } else {
                            RealtimeWeatherBean.ResultBean result = body.getResult();
                            if (result != null) {
                                handlerSuccess(getInfo(result));
                            }
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<RealtimeWeatherBean> call, Throwable t) {
                if (isFresh) {
                    handlerRefreshError();
                } else {
                    for (IWeatherCallback callback : mCallbacks) {
                         callback.onError();
                    }
                }
                LogUtils.i(WeatherPresentImpl.this, "请求失败" + t.toString());
            }
        });
    }

    //请求天级天气
    private void doRequestDayWeather(double Longitude,double Latitude,boolean isFresh) {
        mWeatherData.doRequestHighAndLow(Longitude,Latitude, new Callback<DayWeatherBean>() {
            @Override
            public void onResponse(Call<DayWeatherBean> call, Response<DayWeatherBean> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    DayWeatherBean dayWeatherBean = response.body();
                    if (dayWeatherBean != null) {
                        if (isFresh) {
                            handlerRefreshSuccess();
                            DayWeatherBean.ResultBean result = dayWeatherBean.getResult();
                            handlerDaySuccess(result,get15Day(result));

                        } else {
                            DayWeatherBean.ResultBean result = dayWeatherBean.getResult();
                            handlerDaySuccess(result,get15Day(result));
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<DayWeatherBean> call, Throwable t) {
                if (isFresh) {
                    handlerRefreshError();
                } else {

                }

            }
        });
    }

    private void handlerLoading() {
        for (IWeatherCallback callback : mCallbacks) {
            callback.onLoading();
        }
    }

    private void handlerRefreshError() {
        for (IWeatherCallback callback : mCallbacks) {
            callback.onRefreshError();
        }
    }

    private void handlerRefreshSuccess() {
        for (IWeatherCallback callback : mCallbacks) {
            callback.onRefreshSuccess();
        }
    }


    private void handlerDaySuccess(DayWeatherBean.ResultBean result,List<WeatherModel> list) {

        for (IWeatherCallback callback : mCallbacks) {
            callback.onLoadDayWeatherData(result,list);
        }
    }


    private void handlerSuccess(DescribeBean body) {
        if (body != null) {
            for (IWeatherCallback callback : mCallbacks) {
                callback.onLoadRealtimeWeatherData(body);

            }
        }
    }

    private List<IWeatherCallback> mCallbacks = new ArrayList<>();

    @Override
    public void registerCallback(IWeatherCallback iWeatherCallback) {
        if (!mCallbacks.contains(iWeatherCallback)) {
            mCallbacks.add(iWeatherCallback);
        }
    }

    @Override
    public void unregisterCallback(IWeatherCallback iWeatherCallback) {
        mCallbacks.remove(iWeatherCallback);
    }

}
