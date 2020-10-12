package com.example.tianqi.ui.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.module_ad.advertisement.BanFeedHelper;
import com.example.module_ad.utils.MyStatusBarUtil;
import com.example.module_ad.utils.SizeUtils;
import com.example.tianqi.base.BaseMainActivity;
import com.example.tianqi.model.bean.DayWeatherBean;
import com.example.tianqi.model.bean.DescribeBean;
import com.example.tianqi.ui.adapter.WeatherDescribeAdapter;
import com.example.tianqi.ui.custom.DiyToolbar;
import com.example.tianqi.ui.custom.SunriseView;
import com.example.tianqi.utils.ChangeBgUtil;
import com.example.tianqi.utils.Contents;
import com.example.tianqi.utils.WeatherUtils;
import com.tiantian.tianqi.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

/**
 * @author: Administrator
 * @date: 2020/8/2 0002
 */
public class AirDescribeActivity extends BaseMainActivity {

    @BindView(R.id.airDes_toolbar)
    DiyToolbar mDiyToolbar;

    @BindView(R.id.iv_airDes_bg)
    ImageView mAirBg_iv;

    @BindView(R.id.tv_airWea)
    TextView tv_airWea;

    @BindView(R.id.tv_airTeam)
    TextView tv_airTeam;

    @BindView(R.id.rv_airDes)
    RecyclerView rv_airDes;

    @BindView(R.id.sun)
    SunriseView mSunriseView;

    @BindView(R.id.banner_container)
    FrameLayout mBannerContainer;

    @BindView(R.id.feed_container)
    FrameLayout mFeedContainer;

    private BanFeedHelper mBanFeedHelper;

    private DayWeatherBean.ResultBean mResultBean;
    private DescribeBean mDescribeBean;
    @Override
    public int getLayoutId() {
        return R.layout.activity_air_describe;
    }

    @Override
    protected void intView() {
        MyStatusBarUtil.setFullWindow(this);
     //   ChangeBgUtil.selectBg(mAirBg_iv,R.mipmap.home_day_bg,R.mipmap.home_night_bg);
        mAirBg_iv.setBackground(getDrawable(ChangeBgUtil.selectIcon()?R.drawable.shape_day_permission_bg:R.drawable.shape_night_permission_bg));

        mBanFeedHelper = new BanFeedHelper(this, mBannerContainer, mFeedContainer);
        mBanFeedHelper.showAd(BanFeedHelper.AdType.TEMPERATURE_PAGE);

        Intent intent = getIntent();
        String city = intent.getStringExtra(Contents.CITY);
        String fiveData = intent.getStringExtra(Contents.FIVE_DATA);
        String describeData = intent.getStringExtra(Contents.DESCRIBE_DATA);

        if (!TextUtils.isEmpty(fiveData)&&!TextUtils.isEmpty(describeData)) {
            mResultBean = JSON.parseObject(fiveData, DayWeatherBean.ResultBean.class);
            mDescribeBean = JSON.parseObject(describeData, DescribeBean.class);


            ChangeBgUtil.setToolBar(this,mDiyToolbar,city,false);
            tv_airWea.setText(WeatherUtils.weatherPhenomena(mResultBean.getDaily().getSkycon().get(0).getValue()));

            tv_airTeam.setText( WeatherUtils.addTemSymbol((int)mResultBean.getDaily().getTemperature().get(0).getMin())+"/"+
               (int)mResultBean.getDaily().getTemperature().get(0).getMax()+"℃");


            GridLayoutManager manager = new GridLayoutManager(this, 3);
            rv_airDes.setLayoutManager(manager);
            rv_airDes.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.bottom= SizeUtils.dip2px(AirDescribeActivity.this,30f);
                }
            });

            List<DescribeBean.Des> list=new ArrayList<>();
            list.add(mDescribeBean.getDes().get(5));
            list.add(mDescribeBean.getDes().get(3));
            list.add(mDescribeBean.getDes().get(6));
            list.add(mDescribeBean.getDes().get(4));
            list.add(mDescribeBean.getDes().get(7));
            list.add(mDescribeBean.getDes().get(1));

            WeatherDescribeAdapter describeAdapter = new WeatherDescribeAdapter(false);
            describeAdapter.setData(list);
            rv_airDes.setAdapter(describeAdapter);

            startSunAnim();

        }





    }

    public void startSunAnim() {
        String timeSunrise =  mResultBean.getDaily().getAstro().get(0).getSunrise().getTime();
        String timeSunset =  mResultBean.getDaily().getAstro().get(0).getSunset().getTime();

        String riseHour = timeSunrise.substring(0, 2);
        String riseMin = timeSunrise.substring(3, 5);

        String setHour = timeSunset.substring(0, 2);
        String setMin = timeSunset.substring(3, 5);

        mSunriseView.setSunrise(Integer.valueOf(riseHour), Integer.valueOf(riseMin));
        // 设置日落时间
        mSunriseView.setSunset(Integer.valueOf(setHour), Integer.valueOf(setMin));
        // 获取系统 时 分
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        // 设置当前时间
        mSunriseView.setCurrentTime(hour, minute);
    }

    @Override
    protected void release() {
        if (mBanFeedHelper != null) {
            mBanFeedHelper.releaseAd();
        }
    }

    @Override
    protected void intEvent() {
        mDiyToolbar.finishActivity(this);
    }
}
