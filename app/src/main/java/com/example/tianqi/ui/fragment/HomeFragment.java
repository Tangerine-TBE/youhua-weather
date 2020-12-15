package com.example.tianqi.ui.fragment;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.example.module_ad.utils.LogUtils;
import com.example.module_ad.utils.SizeUtils;
import com.example.tianqi.base.BaseFragment;
import com.example.tianqi.base.IHomeFragment;
import com.example.tianqi.model.bean.LocationBean;
import com.example.tianqi.presenter.Impl.CityPresentImpl;
import com.example.tianqi.presenter.views.ICityCallback;
import com.example.tianqi.ui.activity.CityManageActivity;
import com.example.tianqi.ui.adapter.WeatherAdapter;
import com.example.tianqi.utils.ChangeBgUtil;
import com.example.tianqi.utils.Contents;
import com.example.tianqi.utils.GaoDeHelper;
import com.example.tianqi.utils.ImmersionUtil;
import com.example.tianqi.utils.SpUtils;
import com.example.tianqi.utils.WeatherUtils;
import com.tamsiree.rxkit.RxBarTool;
import com.tamsiree.rxkit.view.RxToast;
import com.tiantian.tianqi.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class HomeFragment extends BaseFragment implements ICityCallback, AMapLocationListener, IHomeFragment {


    @BindView(R.id.weather_container)
    ViewPager mViewPager2;
    @BindView(R.id.indicator)
    LinearLayout mIndicator;
    @BindView(R.id.city)
    TextView mCity;
    @BindView(R.id.relativeLayout)
    RelativeLayout mToolBar;
    @BindView(R.id.add_city)
    ImageView mAdd;
    @BindView(R.id.toolBar_bg)
    LinearLayout toolBar_bg;
    private double mLongitude;
    private double mLatitude;
    private String mCurrentCity;

    private WeatherAdapter mAdapter;
    private int mStatusBarHeight;
    private CityPresentImpl mCityPresent;
    private List<LocationBean> mCityList = new ArrayList<>();
    private boolean mIsOne;
    private int mPosition;
    private GaoDeHelper mGaoDeHelper;
    private MyReceiver mMyReceiver;
    //获取布局文件
    @Override
    public int getChildLayout() {
        return R.layout.fragment_home;
    }


    @Override
    protected void intView() {
        setViewState(ViewState.SUCCESS);

        //设置toolbar高度
        mStatusBarHeight = RxBarTool.getStatusBarHeight(getContext());
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mToolBar.getLayoutParams();
        layoutParams.topMargin = mStatusBarHeight;
        mToolBar.setLayoutParams(layoutParams);

        mAdapter = new WeatherAdapter(getChildFragmentManager());
        mViewPager2.setAdapter(mAdapter);
        mViewPager2.setOffscreenPageLimit(8);

        mGaoDeHelper = GaoDeHelper.getInstance();
        mGaoDeHelper.setListener(this);
        mGaoDeHelper.startLocation();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
        mMyReceiver = new MyReceiver();
        getActivity().registerReceiver(mMyReceiver,intentFilter);

    }
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            LocationManager lm = (LocationManager) context.getSystemService(Service.LOCATION_SERVICE);
            boolean isEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (isEnabled) {
                mGaoDeHelper.startLocation();
            }

        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMsg(LocationBean message) {
        //  LogUtils.i(CityManageActivity.this,"--------------------------->"+message.toString());
        if (mCityPresent != null) {
            mCityPresent.updateDataToSQLite(message);
        }
    }


    @Override
    protected void intPresent() {
        EventBus.getDefault().register(this);
        mCityPresent = CityPresentImpl.getInstance();
        mCityPresent.registerCallback(this);

        if (!SpUtils.getInstance().getBoolean(Contents.FIRST_ADD,false)) {
            LocationBean locationBean = new LocationBean();
            locationBean.setCity(SpUtils.getInstance().getString(Contents.CURRENT_CITY, "深圳"));
            locationBean.setLongitude(Double.parseDouble(SpUtils.getInstance().getString(Contents.CURRENT_LONG, "113.959531")));
            locationBean.setLatitude(Double.parseDouble(SpUtils.getInstance().getString(Contents.CURRENT_LAT, "22.544983")));
            locationBean.setWea("CLEAR_NIGHT");
            locationBean.setHighTeam(30);
            locationBean.setLowTeam(25);
            mCityList.add(locationBean);
            mCityPresent.addDataToSQLite(locationBean);
            mAdapter.setData(mCityList);
        }

        if (mCityPresent != null) {
            mCityPresent.getCityData();
        }

    }


    @Override
    protected void intEvent() {
        //滑动页面监听
        mViewPager2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position>=mCityList.size()) return;
               final Fragment currentFragment = mAdapter.getInstantFragment();
                mPosition = position;
                updateIndicator(position);
                mCity.setText(mCityList.get(position).getCity());
                if ( currentFragment instanceof WeatherFragment) {
                    ((WeatherFragment)currentFragment).setTopNestedScrollView();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImmersionUtil.startActivity(getActivity(), CityManageActivity.class, false);
            }
        });

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation.getLatitude() != 0.0 || aMapLocation.getLongitude() != 0.0) {
            mCurrentCity = WeatherUtils.cityType(aMapLocation.getCity());
            mLongitude = aMapLocation.getLongitude();
            mLatitude = aMapLocation.getLatitude();
            boolean isFirstLocation = SpUtils.getInstance().getBoolean(Contents.FIRST_LOCATION, false);
            if (isFirstLocation) {
                RxToast.success("已自动定位到当前城市");
                SpUtils.getInstance().putBoolean(Contents.FIRST_LOCATION, false).commit();
            }

            if (!TextUtils.isEmpty(mCurrentCity)) {
                String lastCity = SpUtils.getInstance().getString(Contents.CURRENT_CITY);
                String lastLong = SpUtils.getInstance().getString(Contents.CURRENT_LONG);
                String lastLat = SpUtils.getInstance().getString(Contents.CURRENT_LAT);
                if (!TextUtils.isEmpty(lastCity) & !mCurrentCity.equals(lastCity) & mCityPresent != null) {
                    //更新当前城市
                    LocationBean locationBean = new LocationBean();
                    locationBean.setCity(mCurrentCity);
                    locationBean.setLongitude(mLongitude);
                    locationBean.setLatitude(mLatitude);
                    locationBean.setWea("CLEAR_NIGHT");
                    locationBean.setHighTeam(35);
                    locationBean.setLowTeam(25);
                    //更新没定位前的城市
                    LocationBean lastLocationBean = new LocationBean();
                    lastLocationBean.setCity(lastCity);
                    lastLocationBean.setLongitude(Double.parseDouble(lastLong));
                    lastLocationBean.setLatitude(Double.parseDouble(lastLat));
                    mIsOne = true;
                    mCityPresent.updateLocationToSQLite(locationBean,lastLocationBean);
                    SpUtils.getInstance().putString(Contents.CURRENT_CITY, mCurrentCity)
                            .putString(Contents.CURRENT_LONG, mLongitude + "")
                            .putString(Contents.CURRENT_LAT, mLatitude + "")
                            .commit();
                }

            }
        }
    }


    //添加城市回调
    @Override
    public void addState(boolean state) {
        //  RxToast.success(getActivity(), state ? "添加成功" : "添加失败").show();
        SpUtils.getInstance().putBoolean(Contents.FIRST_ADD,true);
    }


    //删除城市回调
    @Override
    public void deleteState(boolean state) {

    }

    @Override
    public void updateState(boolean state) {

    }


    //获取添加城市回调
    @Override
    public void onLoadSuccess(List<LocationBean> locationBeanList) {
        if (locationBeanList == null) {
            return;
        }
        this.mCityList = locationBeanList;

        if (mIsOne) {
            mAdapter.setData(locationBeanList);
            mViewPager2.setCurrentItem(0);
            showIndicator(locationBeanList);
            updateIndicator(0);
            mCity.setText(mCityList.get(0).getCity());
            mIsOne = false;
        } else {
            if (locationBeanList.size() != 0) {
                if (!mCityPresent.isScroll()) {
                    mAdapter.setData(locationBeanList);
                    mViewPager2.setCurrentItem(mCityList.size());
                    showIndicator(locationBeanList);
                    updateIndicator(mCityList.size() - 1);
                    mCity.setText(mCityList.get(mCityList.size() - 1).getCity());
                    //  LogUtils.i(this, "滑动---------------------->" + locationBeanList.size());
                    mCityPresent.setScroll(true);
                }

                LogUtils.i(this, " 现在位置---------------------->" + mPosition);
                LogUtils.i(this, " 现在长度---------------------->" + locationBeanList.size());

                int position = mCityPresent.getPosition();

                if (position != 0) {
                    int realPosition = 0;
                    if (position > mPosition) {
                        realPosition = mPosition;
                        LogUtils.i(this, mCityList.size() + "大于现在为止删除---------------------->" + realPosition);
                    }
                    if (position < mPosition) {
                        realPosition = mPosition - 1;
                        LogUtils.i(this, mCityList.size() + "小于现在为止删除---------------------->" + realPosition);
                    }

                    if (position == mPosition & position != locationBeanList.size()) {
                        realPosition = mPosition;
                        LogUtils.i(this, position + "等于现在为止删除---------------------->" + realPosition);
                    }

                    if (position == locationBeanList.size() + 1) {
                        realPosition = locationBeanList.size() - 1;
                        LogUtils.i(this, mCityList.size() + "删除最后一个---------------------->" + realPosition);
                    }
                    if (position == mPosition && position == locationBeanList.size()) {
                        realPosition = locationBeanList.size() - 1;
                        LogUtils.i(this, mCityList.size() + "等于现在最后一个为止删除---------------------->" + realPosition);
                    }
                    mAdapter.setData(locationBeanList);
                    mViewPager2.setCurrentItem(realPosition);
                    showIndicator(locationBeanList);
                    updateIndicator(realPosition);
                    mCity.setText(mCityList.get(realPosition).getCity());
                    LogUtils.i(this, "删除滑动---------------------->" + locationBeanList.size());
                    mCityPresent.setPosition(0);
                }

            }


        }


    }

    //城市添加限制回调
    @Override
    public void onOverLimit() {

    }

    @Override
    public void onAgainAdd() {
        LogUtils.i(HomeFragment.this, "onAgainAdd------------------------>");
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsOne = true;
    }

    //更新指示器
    private void updateIndicator(int index) {
        if (mIndicator == null) {
            return;
        }
        for (int i = 0; i < mIndicator.getChildCount(); i++) {
            View childAt = mIndicator.getChildAt(i);
            if (index == i) {
                childAt.setBackgroundResource(R.drawable.loop_action_point_select);
            } else {
                childAt.setBackgroundResource(R.drawable.loop_action_point_nomal);
            }
        }
    }

    //显示指示器
    private void showIndicator(List<LocationBean> locationBeanList) {
        mIndicator.removeAllViews();
        int size = SizeUtils.dip2px1(getContext(), 5);
        for (int i = 0; i < locationBeanList.size(); i++) {
            View view = new View(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            params.leftMargin = SizeUtils.dip2px1(getContext(), 3);
            params.rightMargin = SizeUtils.dip2px1(getContext(), 3);
            view.setLayoutParams(params);
            if (i == 0) {
                view.setBackgroundResource(R.drawable.loop_action_point_select);
            } else {
                view.setBackgroundResource(R.drawable.loop_action_point_nomal);
            }
            mIndicator.addView(view);
        }
    }


    //释放资源
    @Override
    protected void release() {
        if (mCityPresent != null) {
            mCityPresent.unregisterCallback(this);
        }

        if (mGaoDeHelper != null) {
            mGaoDeHelper.stopLocation();
        }
        if (mMyReceiver != null) {
            getActivity().unregisterReceiver(mMyReceiver);
        }


        EventBus.getDefault().unregister(this);
    }


    @Override
    public void setToolbarBg(int x) {
        if (toolBar_bg != null & getActivity() != null) {
            if (x > mStatusBarHeight+50) {
                toolBar_bg.setBackground(getResources().getDrawable(ChangeBgUtil.selectIcon() ? R.color.day_bg : R.color.night_bg, null));
            } else {
                toolBar_bg.setBackground(getResources().getDrawable(R.color.transparent, null));
            }
        }
    }
}

