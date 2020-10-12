package com.example.tianqi.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.module_ad.advertisement.BanFeedHelper;
import com.example.module_ad.utils.LogUtils;
import com.example.module_ad.utils.MyStatusBarUtil;
import com.example.tianqi.base.BaseMainActivity;
import com.example.tianqi.model.bean.AirBean;
import com.example.tianqi.model.bean.DayWeatherBean;
import com.example.tianqi.model.bean.DescribeBean;
import com.example.tianqi.model.bean.RealtimeWeatherBean;
import com.example.tianqi.ui.adapter.AirAdapter;
import com.example.tianqi.ui.adapter.FiveAirAdapter;
import com.example.tianqi.ui.custom.DiyToolbar;
import com.example.tianqi.ui.custom.RadAqiView;
import com.example.tianqi.utils.ColorUtil;
import com.example.tianqi.utils.Contents;
import com.example.tianqi.utils.WeatherUtils;
import com.tiantian.tianqi.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AirActivity extends BaseMainActivity  {

    @BindView(R.id.air_toolbar)
    DiyToolbar mDiyToolbar;
    @BindView(R.id.air_container)
    RecyclerView mContainer_rv;
    @BindView(R.id.air_five_container)
    RecyclerView mFiveContainer_rv;
    @BindView(R.id.banner_container)
    FrameLayout mBannerContainer;
    @BindView(R.id.feed_container)
    FrameLayout mFeedContainer;
    @BindView(R.id.tv_air_number)
    TextView tv_air_number;
    @BindView(R.id.tv_air_lv)
    TextView tv_air_lv;
    @BindView(R.id.mRadAqiView)
    RadAqiView mRadAqiView;



    private static final String[] ALARM_LEVEL = { "优    ", "良    ", "轻度", "中度","重度", "严重"};
    private static final Drawable[] ALARM_GB =new Drawable[6];

    private DayWeatherBean.ResultBean mResultBean;
    private DescribeBean mDescribeBean;
    private BanFeedHelper mBanFeedHelper;

    @Override
    public int getLayoutId() {
        return R.layout.activity_air;
    }

    @Override
    protected void intView() {
        MyStatusBarUtil.setColor(this,ColorUtil.AIR_TOP);
        mBanFeedHelper = new BanFeedHelper(this, mBannerContainer, mFeedContainer);
        mBanFeedHelper.showAd(BanFeedHelper.AdType.AIRQUALITY_PAGE);


        Intent intent = getIntent();
        String city = intent.getStringExtra(Contents.CITY);
        String fiveData = intent.getStringExtra(Contents.FIVE_DATA);
        String describeData = intent.getStringExtra(Contents.DESCRIBE_DATA);
        mDiyToolbar.setTitle(city);
        mDiyToolbar.setTitleColor(ColorUtil.WHITE);
        mDiyToolbar.setColorBackground(ColorUtil.AIR_TOP);
        mDiyToolbar.setBackIcon(R.mipmap.icon_white_back);

        if (!TextUtils.isEmpty(fiveData)&&!TextUtils.isEmpty(describeData)) {
            mResultBean = JSON.parseObject(fiveData, DayWeatherBean.ResultBean.class);
            mDescribeBean = JSON.parseObject(describeData, DescribeBean.class);





            //指示标志
            ALARM_GB[0]=getResources().getDrawable(R.drawable.shape_air_a_bg);
            ALARM_GB[1]=getResources().getDrawable(R.drawable.shape_air_b_bg);
            ALARM_GB[2]=getResources().getDrawable(R.drawable.shape_air_c_bg);
            ALARM_GB[3]=getResources().getDrawable(R.drawable.shape_air_d_bg);
            ALARM_GB[4]=getResources().getDrawable(R.drawable.shape_air_e_bg);
            ALARM_GB[5]=getResources().getDrawable(R.drawable.shape_air_f_bg);


            //空气质量数据展示
            List<AirBean> list= new ArrayList<>();
            RealtimeWeatherBean.ResultBean.RealtimeBean.AirQualityBean airQuality = mDescribeBean.getAirQuality();
            int pm25 = (int)airQuality.getPm25();
            int pm10 = (int)airQuality.getPm10();
            int so2 = (int)airQuality.getSo2();
            int no2 = (int)airQuality.getNo2();
            int co = (int)airQuality.getCo();
            int o3 = (int)airQuality.getO3();

            LogUtils.i(this,"----------------------??"+pm25);

            list.add(new AirBean("细颗粒物","PM2.5",pm25));
            list.add(new AirBean("粗颗粒物","PM10",pm10));
            list.add(new AirBean("二氧化硫","SO2",so2));
            list.add(new AirBean("二氧化氮","NO2",no2));
            list.add(new AirBean("一氧化碳","CO",co));
            list.add(new AirBean("臭氧","O3",o3));

            GridLayoutManager manager = new GridLayoutManager(this, 3);
            mContainer_rv.setLayoutManager(manager);
            AirAdapter airAdapter = new AirAdapter();
            airAdapter.setData(list);
            mContainer_rv.setAdapter(airAdapter);


            //五天空气质量数据

            GridLayoutManager layoutManager = new GridLayoutManager(this,5);
            mFiveContainer_rv.setLayoutManager(layoutManager);
            FiveAirAdapter fiveAirAdapter = new FiveAirAdapter();
            fiveAirAdapter.setData(mResultBean);
            mFiveContainer_rv.setAdapter(fiveAirAdapter);
            int aqi = (int) mResultBean.getDaily().getAir_quality().getAqi().get(0).getAvg().getChn();
            tv_air_lv.setText(WeatherUtils.aqiType(aqi));
            tv_air_number.setText(aqi+"");

            if (aqi >= 720) {
                mRadAqiView.setProgress(180f);
            } else {
                mRadAqiView.setProgress(aqi/4f);
            }


        }





    }


    @Override
    protected void intPresent() {


    }




    @Override
    protected void intEvent() {
        mDiyToolbar.finishActivity(this);
    }


    @Override
    protected void release() {
        if (mBanFeedHelper != null) {
            mBanFeedHelper.releaseAd();
        }
    }


}
