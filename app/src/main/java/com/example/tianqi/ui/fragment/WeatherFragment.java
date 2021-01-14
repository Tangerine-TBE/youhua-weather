package com.example.tianqi.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.module_ad.advertisement.FeedHelper;
import com.example.module_ad.utils.CommonUtil;
import com.example.module_ad.utils.LogUtils;
import com.example.tianqi.base.BaseApplication;
import com.example.tianqi.base.BaseFragment;
import com.example.tianqi.model.bean.DayWeatherBean;
import com.example.tianqi.model.bean.DescribeBean;
import com.example.tianqi.model.bean.HourWeatherBean;
import com.example.tianqi.model.bean.HuangLiBean;
import com.example.tianqi.model.bean.LocationBean;
import com.example.tianqi.model.bean.MjDesBean;
import com.example.tianqi.model.bean.MjLifeBean;
import com.example.tianqi.model.bean.RainWeatherBean;
import com.example.tianqi.model.bean.WarningBean;
import com.example.tianqi.model.bean.WeatherCacheBean;
import com.example.tianqi.presenter.Impl.HuangLiPresentImpl;
import com.example.tianqi.presenter.Impl.WeatherCachePresentImpl;
import com.example.tianqi.presenter.Impl.WeatherPresentImpl;
import com.example.tianqi.presenter.views.IHuangLiCallback;
import com.example.tianqi.presenter.views.IWeatherCacheCallback;
import com.example.tianqi.presenter.views.IWeatherCallback;
import com.example.tianqi.ui.activity.AirActivity;
import com.example.tianqi.ui.activity.Day15Activity;
import com.example.tianqi.ui.activity.HuangLiActivity;
import com.example.tianqi.ui.adapter.LifeAdapter;
import com.example.tianqi.ui.adapter.TwentyFourAdapter;
import com.example.tianqi.ui.adapter.WeatherDescribeAdapter;
import com.example.tianqi.ui.custom.mj15day.WeatherItemView;
import com.example.tianqi.ui.custom.mj15day.WeatherModel;
import com.example.tianqi.ui.custom.mj15day.ZzWeatherView;
import com.example.tianqi.utils.ChangeBgUtil;
import com.example.tianqi.utils.ColorUtil;
import com.example.tianqi.utils.Contents;
import com.example.tianqi.utils.DateUtil;
import com.example.tianqi.utils.SpUtils;
import com.example.tianqi.utils.SpeakUtil;
import com.example.tianqi.utils.WeatherUtils;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.tamsiree.rxkit.view.RxToast;
import com.tiantian.tianqi.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

/**
 * @author: Administrator
 * @date: 2020/6/29 0029
 */
public class WeatherFragment extends BaseFragment implements IWeatherCallback, IWeatherCacheCallback, IHuangLiCallback {


    @BindView(R.id.wea)
    TextView mWea;
    @BindView(R.id.high_low)
    TextView mHigh_low;
    @BindView(R.id.iv_report)
    ImageView mReport_iv;
    @BindView(R.id.rl_air)
    RelativeLayout rl_air;
    @BindView(R.id.rv_life_container)
    RecyclerView rv_life_container;
    @BindView(R.id.four_describe)
    RecyclerView mDesContainer;
    @BindView(R.id.SmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.feedAd_container)
    FrameLayout mFeedAdContainer;
    @BindView(R.id.air_title)
    TextView air_title;
    @BindView(R.id.tv_detail)
    TextView tv_detail;
    @BindView(R.id.tv_nongli)
    TextView tv_nongli;
    @BindView(R.id.tv_suici)
    TextView tv_suici;
    @BindView(R.id.tv_yi)
    TextView tv_yi;
    @BindView(R.id.tv_ji)
    TextView tv_ji;
    @BindView(R.id.tv_disaster_title)
    TextView tv_disaster_title;
    @BindView(R.id.tv_update)
    TextView tv_update;
    @BindView(R.id.tv_air)
    TextView tv_air;
    @BindView(R.id.tv_windy_speed)
    TextView tv_windy_speed;
    @BindView(R.id.rv_container24Hours)
    RecyclerView rv_container24Hours;
    @BindView(R.id.rl_zaiHai)
    RelativeLayout rl_zaiHai;
    @BindView(R.id.weather15_view)
    ZzWeatherView weatherView;
    @BindView(R.id.iv_bg)
    ImageView iv_bg;
    @BindView(R.id.iv_wea_icon)
    ImageView iv_wea_icon;
    @BindView(R.id.NestedScrollView)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.tv_forecast)
    TextView tv_forecast;
    @BindView(R.id.five_forecast)
    LinearLayout five_forecast;
    private WeatherPresentImpl mWeatherPresent;
    private String mCity;
    private double mLongitude;
    private double mLatitude;
    private WeatherCachePresentImpl mCachePresent;
    private DayWeatherBean.ResultBean mFiveWeatherData;
    private DescribeBean mDescribes;
    private int mPos;
    private List<MjDesBean> mLifeIndexData = new ArrayList<>();
    private int mMin;
    private int mMax;
    private String mWeaType;
    private WeatherDescribeAdapter mDescribeAdapter;
    private FeedHelper mFeedHelper;
    private LifeAdapter mLifeAdapter;
    private TwentyFourAdapter mTwentyFourAdapter;
    private HuangLiPresentImpl mHuangLiPresent;
    private HuangLiBean mHuangLiData;
    private WarningBean mWarningBean;
    private HourWeatherBean mHourWeatherBean;
    private AnimationDrawable mDrawable;


    public static WeatherFragment getInstance(LocationBean locationBean) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Contents.CITY, locationBean.getCity());
        bundle.putDouble(Contents.LATITUDE, locationBean.getLatitude());
        bundle.putDouble(Contents.LONGITUDE, locationBean.getLongitude());
        fragment.setArguments(bundle);
        return fragment;
    }

    //添加布局文件
    @Override
    public int getChildLayout() {

        return R.layout.fragment_weather;
    }

    //切换mNestedScrollView置顶
   public void setTopNestedScrollView() {
       mNestedScrollView.scrollTo(0,0);
       if (SpeakUtil.INSTANCE.isSpeaking()) {
           if (mDrawable != null) {
               mDrawable.selectDrawable(0);
               mDrawable.stop();
           }
       }
       SpeakUtil.INSTANCE.stopSpeak();


   }

    //初始化
    @Override
    protected void intView() {
        setViewState(ViewState.SUCCESS);
        init15DayWeather();
        //设置刷新头
        MaterialHeader classicsHeader = new MaterialHeader(getContext());
        mSmartRefreshLayout.setRefreshHeader(classicsHeader);
        //天气描述布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        mDesContainer.setLayoutManager(manager);
        mDescribeAdapter = new WeatherDescribeAdapter(false);
        mDesContainer.setAdapter(mDescribeAdapter);
        //生活指数
        GridLayoutManager lifeManager = new GridLayoutManager(getContext(), 4);
        rv_life_container.setLayoutManager(lifeManager);
        mLifeAdapter = new LifeAdapter();
        rv_life_container.setAdapter(mLifeAdapter);
        //24小时
        LinearLayoutManager hour24Manager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rv_container24Hours.setLayoutManager(hour24Manager);
        mTwentyFourAdapter = new TwentyFourAdapter();
        rv_container24Hours.setAdapter(mTwentyFourAdapter);

        mFeedHelper = new FeedHelper(getActivity(), mFeedAdContainer);
        mFeedHelper.showAd();

        iv_bg.setImageResource(ChangeBgUtil.selectIcon() ? R.mipmap.icon_day_bg : R.mipmap.icon_night_bg);
    }

    private void init15DayWeather() {
        //设置白天和晚上线条的颜色
        weatherView.setDayAndNightLineColor(ColorUtil.HIGH15, ColorUtil.LOW15);
        //画折线
        weatherView.setLineType(ZzWeatherView.LINE_TYPE_CURVE);
        //设置线宽
        weatherView.setLineWidth(6f);
        //设置一屏幕显示几列(最少3列)
        try {
            weatherView.setColumnNumber(6);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //注册
    @Override
    protected void intPresent() {
        mWeatherPresent = new WeatherPresentImpl();
        mWeatherPresent.registerCallback(this);

        mCachePresent = WeatherCachePresentImpl.getInstance();
        mCachePresent.registerCallback(this);

        mHuangLiPresent = new HuangLiPresentImpl();
        mHuangLiPresent.registerCallback(this);

        if (!CommonUtil.isNetworkAvailable(getContext())) {
            if (mCachePresent != null) {
                mCachePresent.getWeatherCache();
            }
            RxToast.warning(getContext(), getString(R.string.connect_error));
        }

    }

    //请求天气数据
    @Override
    protected void intLoad() {
        Bundle arguments = getArguments();
        mCity = arguments.getString(Contents.CITY);
        mLongitude = arguments.getDouble(Contents.LONGITUDE);
        mLatitude = arguments.getDouble(Contents.LATITUDE);
        if (mWeatherPresent != null & mHuangLiPresent != null) {
            mWeatherPresent.getLifeWeatherInfo(mLongitude, mLatitude);
            mWeatherPresent.getRealTimeWeatherInfo(mLongitude, mLatitude);
            mWeatherPresent.getDayWeatherInfo(mLongitude, mLatitude);
            mWeatherPresent.getHourWeatherInfo(mLongitude, mLatitude);
            mWeatherPresent.getRainWeatherInfo(mLongitude, mLatitude);
            mWeatherPresent.getWaringWeatherInfo(mLongitude, mLatitude);
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = (calendar.get(Calendar.MONTH)) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            mHuangLiPresent.getHuangLi(day + "", month + "", year + "");
        }


    }

    //事件监听
    @Override
    protected void intEvent() {
        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Fragment parentFragment = getParentFragment();
                if (parentFragment instanceof HomeFragment) {
                    ((HomeFragment) parentFragment).setToolbarBg(scrollY);
                }
            }
        });


        tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mHuangLiData != null) {
                    startActivity(new Intent(getActivity(), HuangLiActivity.class).putExtra(Contents.HL_DATA, JSON.toJSONString(mHuangLiData)));
                }
            }
        });

        mReport_iv.setOnClickListener(view -> {
            if (CommonUtil.isNetworkAvailable(getActivity())) {
                String report = mCity + ",今天天气%s,气温%s到%s摄氏度";
                SpeakUtil.INSTANCE.speakText(String.format(report, mWeaType, mMin, mMax));
                mDrawable = (AnimationDrawable) mReport_iv.getDrawable();
                SpeakUtil.INSTANCE.setOnSpeechListener(new SpeakUtil.OnSpeechListener() {
                    @Override
                    public void onStart() {
                        mDrawable.start();
                    }

                    @Override
                    public void onStop() {
                        mDrawable.selectDrawable(0);
                        mDrawable.stop();

                    }
                });
            }
        });

        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (mWeatherPresent != null) {
                    mWeatherPresent.pullToRefresh();
                    mWeatherPresent.getRainWeatherInfo(mLongitude, mLatitude);
                    mWeatherPresent.getLifeWeatherInfo(mLongitude, mLatitude);
                }
            }
        });
        rl_air.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAirActivity(AirActivity.class);
            }
        });

        weatherView.setOnWeatherItemClickListener(new ZzWeatherView.OnWeatherItemClickListener() {
            @Override
            public void onItemClick(WeatherItemView itemView, int position, WeatherModel weatherModel) {
                toDay15Activity(position);
            }
        });

    }

    private void toDay15Activity(int position) {
        if (mFiveWeatherData != null) {
            startActivity(new Intent(getActivity(), Day15Activity.class)
                    .putExtra(Contents.FIVE_DATA, JSON.toJSONString(mFiveWeatherData))
                    .putExtra(Contents.CITY, mCity)
                    .putExtra(Contents.CURRENT_POSITION, position)
            );
            SpUtils.getInstance().putString(Contents.FIVE_WEA, JSON.toJSONString(mFiveWeatherData));
        }

    }

    private void startAirActivity(Class aClass) {
        if (mDescribes != null & !TextUtils.isEmpty(mCity) & mFiveWeatherData != null) {
            Intent intent = new Intent(getActivity(), aClass);
            intent.putExtra(Contents.CITY, mCity);
            intent.putExtra(Contents.FIVE_DATA, JSON.toJSONString(mFiveWeatherData));
            intent.putExtra(Contents.DESCRIBE_DATA, JSON.toJSONString(mDescribes));
            startActivity(intent);
        }
    }


    //实时天气回调
    @Override
    public void onLoadRealtimeWeatherData(DescribeBean resultBean) {
        // LogUtils.i(this,"onLoadRealtimeWeatherData--------------------1111111111111111>");
        setViewState(ViewState.SUCCESS);
        if (resultBean != null) {
            this.mDescribes = resultBean;
            List<DescribeBean.Des> des = resultBean.getDes();
            setDescribeUi(des);

            BaseApplication.getAppContext().getSharedPreferences(Contents.NO_BACK_SP, Context.MODE_PRIVATE).edit().putBoolean(Contents.NO_BACK, false).apply();

        }
    }
    List<DescribeBean.Des> list = new ArrayList<>();
    //天气描述
    private void setDescribeUi(List<DescribeBean.Des> des) {
        list.clear();
        list.add(new DescribeBean.Des(R.mipmap.icon_humidity, des.get(0).getTitle(), des.get(0).getValues()));
        list.add(new DescribeBean.Des(R.mipmap.icon_zwx, des.get(5).getTitle(), des.get(5).getValues()));
        list.add(new DescribeBean.Des(R.mipmap.icon_qiya, des.get(1).getTitle(), des.get(1).getValues()));
        tv_windy_speed.setText(des.get(4).getTitle() + "  " + des.get(4).getValues());

        mDescribeAdapter.setData(list);

    }

    //缓存天气
    @Override
    public void onLoadCacheSuccess(List<WeatherCacheBean> cacheBeanList) {
        for (WeatherCacheBean weatherCacheBean : cacheBeanList) {
            if (weatherCacheBean != null) {
                if (weatherCacheBean.getDescribe() == null && weatherCacheBean.getFiveWea() == null
                        && weatherCacheBean.getLifeIndex() == null && weatherCacheBean.getTfRainState() == null) {
                    return;
                }
            }
        }
        LogUtils.i(this, mPos+"onLoadCacheSuccess-------缓存----------->"+cacheBeanList.size());
        setViewState(ViewState.SUCCESS);
        if (mPos >=cacheBeanList.size()) {
            return;
        }
        if (mPos >= 0) {
            WeatherCacheBean weatherCacheBean = cacheBeanList.get(mPos);
            String describe = weatherCacheBean.getFiveWea();
            DayWeatherBean.ResultBean resultBean = JSON.parseObject(describe, DayWeatherBean.ResultBean.class);
            if (resultBean != null) {
                String value1 = resultBean.getDaily().getSkycon().get(0).getValue();
                String weatherPhenomena = WeatherUtils.weatherPhenomena(value1);
                iv_wea_icon.setImageResource(WeatherUtils.weatherIcon(value1));
                //实时天气
                mWea.setText(weatherPhenomena);

                //空气质量
                int chn = (int) resultBean.getDaily().getAir_quality().getAqi().get(0).getAvg().getChn();
                air_title.setText(WeatherUtils.aqiType(chn));
            }
            //天气描述
            String cacheBeanDescribe = weatherCacheBean.getDescribe();
            DescribeBean describeBean = JSON.parseObject(cacheBeanDescribe, DescribeBean.class);
            if (describeBean != null) {
                List<DescribeBean.Des> des = describeBean.getDes();
                mDescribes = describeBean;
                setDescribeUi(des);
            }


            //五天天气
            if (resultBean != null) {
                show15Weather(resultBean);
            }

            //24小时
            String date24WeatherCache = weatherCacheBean.getTfData();
            if (!TextUtils.isEmpty(date24WeatherCache)) {
                HourWeatherBean cache24Hour = JSON.parseObject(date24WeatherCache, HourWeatherBean.class);
                if (cache24Hour != null) {
                    show24Weather(cache24Hour);
                }
            }

            //预警信息
            String tfQuality = weatherCacheBean.getTfQuality();
            if (!TextUtils.isEmpty(tfQuality)) {
                WarningBean warningBean = JSON.parseObject(tfQuality, WarningBean.class);
                if (warningBean != null) {
                    showWarning(warningBean);
                }

            }


            //生活指数
            String lifeIndex = weatherCacheBean.getLifeIndex();
            List<MjDesBean> list = JSON.parseArray(lifeIndex, MjDesBean.class);
            if (list.size() != 0) {
                mLifeAdapter.setData(list);
                rv_life_container.setVisibility(View.VISIBLE);
            }

            //黄历
            String huangLiData = SpUtils.getInstance().getString(Contents.HUANGLI_DATA, "");
            if (!TextUtils.isEmpty(huangLiData)) {
                HuangLiBean huagnli = JSON.parseObject(huangLiData, HuangLiBean.class);
                if (resultBean != null) {
                    showHuangLi(huagnli);
                }
            }


        }
    }



    //天级天气回调
    @Override
    public void onLoadDayWeatherData(DayWeatherBean.ResultBean resultBean, List<WeatherModel> day15List) {
        //LogUtils.i(this,"onLoadDayWeatherData--------------------2222222222222222>");
        if (resultBean == null) {
            return;
        }
        show15Weather(resultBean);
        saveSQLite();
    }
    private List<WeatherModel>  m15DayWeatherList = new ArrayList<>();
    private void show15Weather(DayWeatherBean.ResultBean resultBean) {
        m15DayWeatherList.clear();
        this.mFiveWeatherData = resultBean;
        DayWeatherBean.ResultBean.DailyBean daily = resultBean.getDaily();

        String week;
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
        }


        weatherView.setList(m15DayWeatherList);
                five_forecast.setVisibility(View.VISIBLE);




        if (mCity!=null&&mCity.equals(SpUtils.getInstance().getString(Contents.CURRENT_CITY))) {
            List<DayWeatherBean.ResultBean.DailyBean.AstroBean> astro = daily.getAstro();
            DayWeatherBean.ResultBean.DailyBean.AstroBean sunriser = astro.get(0);
            String sunrise = sunriser.getSunrise().getTime();
            String sunset = sunriser.getSunset().getTime();
            String riseHour = sunrise.substring(0, 2);
            String riseMin = sunrise.substring(3, 5);
            String setHour = sunset.substring(0, 2);
            String setMin = sunset.substring(3, 5);
            String time = "日出  " + riseHour + ":" + riseMin + "  " + "日落  " + setHour + ":" + setMin;
            BaseApplication.getAppContext().getSharedPreferences("sun_sp", Context.MODE_PRIVATE).edit().putString("sun", time).commit();
        }

        //高低温
        DayWeatherBean.ResultBean.DailyBean.TemperatureBean temperatureBean = daily.getTemperature().get(0);
        mMin = (int) temperatureBean.getMin();
        mMax = (int) temperatureBean.getMax();
        int chn = (int) daily.getAir_quality().getAqi().get(0).getAvg().getChn();
        tv_air.setBackground(WeatherUtils.aqiTypeBg2(chn));
        String aqiType = WeatherUtils.aqiType(chn);
        tv_air.setText("空气质量：" + aqiType);
        air_title.setText(aqiType);
        List<DayWeatherBean.ResultBean.DailyBean.SkyconBean> skycon = daily.getSkycon();
        String value = skycon.get(0).getValue();
        mWeaType = WeatherUtils.weatherPhenomena(value);
        iv_wea_icon.setImageResource(WeatherUtils.weatherIcon(value));
        mWea.setText(mWeaType);
        EventBus.getDefault().post(new LocationBean(mCity, mLongitude, mLatitude, value, mMax, mMin));



    }

    private List<MjDesBean> mHour24Data = new ArrayList<>();

    //小时天气回调
    @Override
    public void onLoadHourWeatherData(HourWeatherBean weatherBean) {
        if (weatherBean == null) {
            return;
        }
        show24Weather(weatherBean);
        saveSQLite();

    }

    private void show24Weather(HourWeatherBean weatherBean) {
        dismissLoading();
        if (weatherBean.getResult() != null) {
            mHour24Data.clear();
            this.mHourWeatherBean = weatherBean;

            HourWeatherBean.ResultBean.HourlyBean hourly = weatherBean.getResult().getHourly();
            mHigh_low.setText((int) hourly.getTemperature().get(0).getValue() + "℃");
            List<HourWeatherBean.ResultBean.HourlyBean.SkyconBean> skycon = hourly.getSkycon();
            List<HourWeatherBean.ResultBean.HourlyBean.TemperatureBean> temperature = hourly.getTemperature();
            for (int i = 0; i < skycon.size() / 2; i++) {
                String time = skycon.get(i).getDatetime().substring(11, 16);

                int tem = (int) temperature.get(i).getValue();

                String value = skycon.get(i).getValue();
                int weatherIcon = WeatherUtils.weatherIcon(value);

                mHour24Data.add(new MjDesBean(tem + "", weatherIcon, time));
            }
            mTwentyFourAdapter.setNewData(mHour24Data);
            rv_container24Hours.setVisibility(View.VISIBLE);
        }

    }

    private void saveSQLite() {

            if (mCachePresent != null) {
                mCachePresent.addWeatherCache(new WeatherCacheBean(mCity, JSON.toJSONString(mDescribes)
                        , JSON.toJSONString(mHourWeatherBean)
                        , JSON.toJSONString(mWarningBean)
                        , JSON.toJSONString(mFiveWeatherData)
                        , JSON.toJSONString(mLifeIndexData)
                ));
        }
    }

    //降雨信息
    @Override
    public void onLoadRainWeatherData(RainWeatherBean rainWeatherBean) {
        if (rainWeatherBean.getResult() != null) {
            showRainForecast(rainWeatherBean);
        }
    }

    //预警信息
    @Override
    public void onLoadWarningWeatherData(WarningBean warningBean) {
        showWarning(warningBean);
    }
    private void showWarning(WarningBean warningBean) {
        this.mWarningBean = warningBean;
        WarningBean.DataBean data = warningBean.getData();
        if (data != null) {
            List<WarningBean.DataBean.AlertBean> alert = data.getAlert();
            if (alert != null) {
                rl_zaiHai.setVisibility(View.VISIBLE);
                WarningBean.DataBean.AlertBean alertBean = alert.get(0);
                tv_disaster_title.setText(alertBean.getType() + "预警");
                LogUtils.i(this, "---------getPub_time-------------" + alertBean.getPub_time());
                String pub_time = alertBean.getPub_time();
                String realTime = pub_time.substring(11, 16);
                tv_update.setText("更新于" + realTime);
            }
        }
    }

    //黄历
    @Override
    public void onLoadHuangLi(HuangLiBean huangLiBean) {
        if (huangLiBean.getResult() == null) {
            return;
        }
        showHuangLi(huangLiBean);
    }


    private void showHuangLi(HuangLiBean huangLiBean) {
        SpUtils.getInstance().putString(Contents.HUANGLI_DATA, JSON.toJSONString(huangLiBean));
        this.mHuangLiData = huangLiBean;
        HuangLiBean.ResultBean result = huangLiBean.getResult();
        String nongli = result.getNongli();
        tv_nongli.setText(nongli.substring(7, nongli.length()));
        //岁次
        StringBuffer stringBuffer = new StringBuffer();
        List<String> suici = result.getSuici();
        for (String s : suici) {
            stringBuffer.append(s + "  ");
        }
        tv_suici.setText(stringBuffer);

        // 宜
        StringBuffer stringBuffer1 = new StringBuffer();
        for (String s : result.getYi()) {
            stringBuffer1.append(s + "  ");
        }
        tv_yi.setText(stringBuffer1);

        //忌
        StringBuffer stringBuffer2 = new StringBuffer();
        for (String s : result.getJi()) {
            stringBuffer2.append(s + "  ");
        }
        tv_ji.setText(stringBuffer2);


    }

    @Override
    public void onLoadLifeWeatherData(List<MjLifeBean> lifeData) {
        showLife(lifeData);
        saveSQLite();
    }

    private void showLife(List<MjLifeBean> lifeData) {
        mLifeIndexData.clear();
        if (lifeData.size() != 0) {
            for (MjLifeBean lifeBean : lifeData) {
                LogUtils.i(this, "--------onLoadLifeWeatherData-------------->" + lifeBean.getName());
                if (lifeBean.getName().equals("紫外线指数")) {
                    mLifeIndexData.add(new MjDesBean(lifeBean.getName(), R.mipmap.home_icon_zwx, lifeBean.getStatus()));
                } else if (lifeBean.getName().equals("穿衣指数")) {
                    mLifeIndexData.add(new MjDesBean("舒适度指数", R.mipmap.home_icon_ssd, lifeBean.getStatus()));
                    mLifeIndexData.add(new MjDesBean(lifeBean.getName(), R.mipmap.home_icon_cy, lifeBean.getStatus()));
                } else if (lifeBean.getName().equals("洗车指数")) {
                    mLifeIndexData.add(new MjDesBean(lifeBean.getName(), R.mipmap.home_icon_xc, lifeBean.getStatus()));
                } else if (lifeBean.getName().equals("感冒指数")) {
                    mLifeIndexData.add(new MjDesBean(lifeBean.getName(), R.mipmap.home_icon_gm, lifeBean.getStatus()));
                } else if (lifeBean.getName().equals("化妆指数")) {
                    mLifeIndexData.add(new MjDesBean(lifeBean.getName(), R.mipmap.home_icon_hz, lifeBean.getStatus()));
                } else if (lifeBean.getName().equals("钓鱼指数")) {
                    mLifeIndexData.add(new MjDesBean(lifeBean.getName(), R.mipmap.home_icon_dy, lifeBean.getStatus()));
                } else if (lifeBean.getName().equals("交通指数")) {
                    mLifeIndexData.add(new MjDesBean(lifeBean.getName(), R.mipmap.home_icon_jiaotong, lifeBean.getStatus()));
                } else if (lifeBean.getName().equals("旅游指数")) {
                    mLifeIndexData.add(new MjDesBean(lifeBean.getName(), R.mipmap.home_icon_lx, lifeBean.getStatus()));
                } else if (lifeBean.getName().equals("息斯敏过敏指数")) {
                    mLifeIndexData.add(new MjDesBean("过敏指数", R.mipmap.home_icon_guoming, lifeBean.getStatus()));
                } else if (lifeBean.getName().equals("运动指数")) {
                    mLifeIndexData.add(new MjDesBean(lifeBean.getName(), R.mipmap.home_icon_yundong, lifeBean.getStatus()));
                } else if (lifeBean.getName().equals("空气污染扩散指数")) {
                    mLifeIndexData.add(new MjDesBean("污染指数", R.mipmap.home_icon_wurang, lifeBean.getStatus()));
                }

            }

            mLifeAdapter.setData(mLifeIndexData);
            rv_life_container.setVisibility(View.VISIBLE);

        }
    }

    private void showRainForecast(RainWeatherBean rainWeatherBean) {
        RainWeatherBean.ResultBean result = rainWeatherBean.getResult();
        RainWeatherBean.ResultBean.MinutelyBean minutely = result.getMinutely();
        tv_forecast.setText(minutely.getDescription() + "");
    }


    //刷新成功回调
    @Override
    public void onRefreshSuccess() {
        mSmartRefreshLayout.finishRefresh(true);
        RxToast.normal("刷新成功");
    }

    //刷新失败回调
    @Override
    public void onRefreshError() {
        mSmartRefreshLayout.finishRefresh(false);
        RxToast.warning("网络连接失败，请检查网络");
    }


    //释放资源
    @Override
    protected void release() {
        if (mWeatherPresent == null) {
            mWeatherPresent.unregisterCallback(this);
        }

        if (mCachePresent != null) {
            mCachePresent.unregisterCallback(this);
        }


        if (mFeedHelper != null) {
            mFeedHelper.releaseAd();
        }

        if (mHuangLiPresent != null) {
            mHuangLiPresent.unregisterCallback(this);
        }


    }

    //网络请求等待回调
    @Override
    public void onLoading() {
     //   setViewState(ViewState.LOADING);
        showLoading();
    }

    //网络请求错误回调
    @Override
    public void onError() {
        LogUtils.i(this, "onError---------//////////////////////////-------------------->");
        dismissLoading();
    }


    @Override
    public void addCacheState(boolean state) {

    }

    @Override
    public void deleteCacheState(boolean state) {

    }


    public void updateArguments(int position) {
        this.mPos = position;
    }


    @Override
    public void onLoadHuangLiError() {

    }
}
