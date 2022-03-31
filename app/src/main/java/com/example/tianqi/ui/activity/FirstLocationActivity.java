package com.example.tianqi.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.example.module_ad.advertisement.SplashHelper;
import com.example.module_ad.bean.AdBean;
import com.example.module_ad.utils.CommonUtil;
import com.example.module_ad.utils.MyStatusBarUtil;
import com.example.module_ad.utils.SpUtil;
import com.example.tianqi.base.BaseApplication;
import com.example.tianqi.base.BaseMainActivity;
import com.example.tianqi.model.bean.LocationBean;
import com.example.tianqi.presenter.Impl.AdPresentImpl;
import com.example.tianqi.presenter.Impl.AddressPresentImpl;
import com.example.tianqi.presenter.views.IAdCallback;
import com.example.tianqi.presenter.views.IAddressCallback;
import com.example.tianqi.utils.ColorUtil;
import com.example.tianqi.utils.Contents;
import com.example.tianqi.utils.GaoDeHelper;
import com.example.tianqi.utils.PresentManager;
import com.example.tianqi.utils.SpUtils;
import com.example.tianqi.utils.WeatherUtils;
import com.tamsiree.rxkit.view.RxToast;
import com.tiantian.tianqi.R;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.CityListAdapter;
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
public class FirstLocationActivity extends BaseMainActivity implements OnPickListener, AMapLocationListener, IAdCallback, IAddressCallback {

    private GaoDeHelper mGaoDeHelper;
    private LocatedCity mLocatedCity;
    @BindView(R.id.fl_adContainer)
    FrameLayout  mSplashContainer;
    private SplashHelper mSplashHelper;
    private String mCity;
    private AdPresentImpl mAdPresent;
    private AddressPresentImpl mAddressPresent;

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
        CityPicker.from(FirstLocationActivity.this)
                .enableAnimation(false)
                .setAnimationStyle(0)
                .setHideBar(false)
                .setOnPickListener(FirstLocationActivity.this).show();
    }

    @Override
    protected void intPresent() {
        mAdPresent = AdPresentImpl.getInstance();
        mAdPresent.registerCallback(this);

        mAddressPresent = PresentManager.getInstance().getAddressPresent();
        mAddressPresent.registerCallback(this);

        mGaoDeHelper = GaoDeHelper.getInstance();
        mGaoDeHelper.setListener(this);
        mGaoDeHelper.startLocation();
    }

    @Override
    public void onPick(int position, City data) {
        selectCitySuccess();
    }

    private void  selectCitySuccess() {
        MyStatusBarUtil.setFullScreen(this,true);
        AdBean.DataBean data1 = SpUtil.getAdState();
        Map<String, String> adKey = SpUtil.getADKey();
        if (data1 != null || adKey != null) {
            if (mSplashHelper != null) {
                mSplashHelper.showAd();
            }
        }else {
            if (mAdPresent != null) {
                mAdPresent.toRequestAd();
            }
        }
        SpUtils.getInstance().putBoolean(Contents.IS_FIRST, false).commit();
        SpUtils.getInstance().putBoolean(Contents.FIRST_LOCATION, true).commit();
        getSharedPreferences(Contents.NO_BACK_SP, MODE_PRIVATE).edit().putBoolean(Contents.NO_BACK, false).apply();
        releaseLocation();
    }


    @Override
    public void onLocate() {
        mGaoDeHelper.startLocation();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            CityListAdapter.haveLocationPermission.setValue(true);
        }
    }

    @Override
    public void onCancel() {
        finish();
    }

    @Override
    public void onFail(int position, City data) {
        //  RxToast.warning("请先定位当前城市");
        if (!CommonUtil.isNetworkAvailable(this)){
            RxToast.warning(getString(R.string.connect_error));
        }else {
            if (mAddressPresent != null) {
                mCity = data.getName();
                mAddressPresent.getLocationAddress(mCity);
            }
        }

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

        if (mSplashHelper != null) {
            mSplashHelper=null;
        }

        if (mAddressPresent != null) {
            mAddressPresent.unregisterCallback(this);
        }
    }

    private void releaseLocation() {
        if (mGaoDeHelper != null) {
            mGaoDeHelper.stopLocation();
            AMapLocationClient locationClient = mGaoDeHelper.getLocationClient();
            if (locationClient!=null) {
                locationClient.unRegisterLocationListener(this);
            }
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
    public void onLoadAddressSuccess(LocationBean addressBean) {
        selectCitySuccess();
        SpUtils.getInstance().putString(Contents.CURRENT_CITY, mCity)
                .putString(Contents.CURRENT_LONG, addressBean.getLongitude() + "")
                .putString(Contents.CURRENT_LAT,addressBean.getLatitude()+"")
                .commit();
    }


    @Override
    public void onLoadAdMsgError() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onError() {

    }
}
