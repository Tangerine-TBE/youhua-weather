package com.example.tianqi.ui.activity;

import android.content.Context;
import android.widget.FrameLayout;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.example.module_ad.advertisement.SplashHelper;
import com.example.module_ad.bean.AdBean;
import com.example.module_ad.utils.LogUtils;
import com.example.module_ad.utils.MyStatusBarUtil;
import com.example.module_ad.utils.SpUtil;
import com.example.tianqi.base.BaseApplication;
import com.example.tianqi.base.BaseMainActivity;
import com.example.tianqi.presenter.Impl.AdPresentImpl;
import com.example.tianqi.presenter.views.IAdCallback;
import com.example.tianqi.utils.ColorUtil;
import com.example.tianqi.utils.Contents;
import com.example.tianqi.utils.GaoDeHelper;
import com.example.tianqi.utils.SpUtils;
import com.example.tianqi.utils.WeatherUtils;
import com.tamsiree.rxkit.view.RxToast;
import com.tiantian.tianqi.R;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.Map;

import butterknife.BindView;

/**
 * @author wujinming QQ:1245074510
 * @name WeatherOne
 * @class name：com.example.tianqi.ui.activity
 * @class describe
 * @time 2020/9/11 18:00
 * @class describe
 */
public class FirstLocationActivity extends BaseMainActivity implements OnPickListener, AMapLocationListener, IAdCallback {

    private GaoDeHelper mGaoDeHelper;
    private LocatedCity mLocatedCity;

    @BindView(R.id.fl_adContainer)
    FrameLayout  mSplashContainer;
    private SplashHelper mSplashHelper;
    private String mCity;
    private AdPresentImpl mAdPresent;

    @Override
    protected void setStatusBarColor() {
        MyStatusBarUtil.setColor(this, ColorUtil.WHITE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_location;
    }

    @Override
    protected void intView() {
        mSplashHelper = new SplashHelper(this, mSplashContainer, MainActivity.class);
        mAdPresent = AdPresentImpl.getInstance();
        mAdPresent.registerCallback(this);
        mGaoDeHelper = GaoDeHelper.getInstance();
        mGaoDeHelper.setListener(this);

        CityPicker.from(FirstLocationActivity.this)
                .enableAnimation(false)
                .setAnimationStyle(0)
                .setHideBar(false)
                .setOnPickListener(FirstLocationActivity.this).show();
    }

    @Override
    public void onPick(int position, City data) {
        MyStatusBarUtil.setFullScreen(this,true);
        AdBean.DataBean data1 = SpUtil.getAdState();
        Map<String, String> adKey = SpUtil.getADKey();
        if (data1 != null || adKey != null) {
            mSplashHelper.showAd();
        }else {
            if (mAdPresent != null) {
                mAdPresent.toRequestAd();
            }
        }
        LogUtils.i(this,"onPick---------------------->"+data.getCode()+data.getName());
        SpUtils.getInstance().putBoolean(Contents.IS_FIRST, false).commit();
        SpUtils.getInstance().putBoolean(Contents.FIRST_LOCATION, true).commit();
        getSharedPreferences(Contents.NO_BACK_SP, MODE_PRIVATE).edit().putBoolean(Contents.NO_BACK, false).apply();
    }

    @Override
    public void onLocate() {
        LogUtils.i(this,"onLocate---------------------->");
        mGaoDeHelper.startLocation();

    }

    @Override
    public void onCancel() {
        LogUtils.i(this,"onCancel---------------------->");
        finish();
    }

    @Override
    public void onFail() {
        LogUtils.i(this,"onFail---------------------->");
        RxToast.warning("请先定位当前城市");
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        double latitude = aMapLocation.getLatitude();
        double longitude = aMapLocation.getLongitude();
        mCity = WeatherUtils.cityType(aMapLocation.getCity());
        mLocatedCity = new LocatedCity(mCity, "", "");
        if ( longitude!= 0.0 ||latitude  != 0.0) {
            CityPicker.from(this).locateComplete(mLocatedCity, LocateState.SUCCESS);
            SpUtils.getInstance().putString(Contents.CURRENT_CITY, mCity)
                    .putString(Contents.CURRENT_LONG, longitude + "")
                    .putString(Contents.CURRENT_LAT,latitude+"")
                    .commit();
        } else {
            BaseApplication.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    CityPicker.from(FirstLocationActivity.this).locateComplete(mLocatedCity, LocateState.FAILURE);
                }
            },1500 );

        }

    }

    @Override
    protected void release() {
        if (mGaoDeHelper != null) {
            mGaoDeHelper.stopLocation();
            mGaoDeHelper=null;
        }

        if (mSplashHelper != null) {
            mSplashHelper=null;
        }
    }

    @Override
    public void onLoadAdMsgSuccess(AdBean adBean) {
        if (adBean != null) {
            AdBean.DataBean data = adBean.getData();
            String ad = JSON.toJSONString(data);
            BaseApplication.getAppContext().getSharedPreferences(Contents.AD_INFO_SP, Context.MODE_PRIVATE).edit().putString(Contents.AD_INFO, ad).apply();
            mSplashHelper.showAd();
        }
    }

    @Override
    public void onLoadAdMsgError() {

    }
}
