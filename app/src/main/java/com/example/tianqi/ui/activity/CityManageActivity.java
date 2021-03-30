package com.example.tianqi.ui.activity;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.module_ad.advertisement.BanFeedHelper;
import com.example.module_ad.utils.LogUtils;
import com.example.tianqi.base.BaseApplication;
import com.example.tianqi.base.BaseMainActivity;
import com.example.tianqi.model.bean.DayWeatherBean;
import com.example.tianqi.model.bean.DescribeBean;
import com.example.tianqi.model.bean.HourWeatherBean;
import com.example.tianqi.model.bean.LocationBean;
import com.example.tianqi.model.bean.MjLifeBean;
import com.example.tianqi.model.bean.RainWeatherBean;
import com.example.tianqi.model.bean.WarningBean;
import com.example.tianqi.model.bean.WeatherCacheBean;
import com.example.tianqi.presenter.Impl.AddressPresentImpl;
import com.example.tianqi.presenter.Impl.CityPresentImpl;
import com.example.tianqi.presenter.Impl.WeatherCachePresentImpl;
import com.example.tianqi.presenter.Impl.WeatherPresentImpl;
import com.example.tianqi.presenter.views.IAddressCallback;
import com.example.tianqi.presenter.views.ICityCallback;
import com.example.tianqi.presenter.views.IWeatherCacheCallback;
import com.example.tianqi.presenter.views.IWeatherCallback;
import com.example.tianqi.ui.adapter.CityListAdapter;
import com.example.tianqi.ui.custom.DiyToolbar;
import com.example.tianqi.ui.custom.mj15day.WeatherModel;
import com.example.tianqi.utils.Contents;
import com.example.tianqi.utils.PresentManager;
import com.example.tianqi.utils.RecyclerViewItemDistanceUtil;
import com.example.tianqi.utils.SpUtils;
import com.tamsiree.rxkit.view.RxToast;
import com.tiantian.tianqi.R;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.List;

import butterknife.BindView;

public class CityManageActivity extends BaseMainActivity implements OnPickListener, IAddressCallback, ICityCallback, SwipeMenuCreator, OnItemMenuClickListener, IWeatherCallback, IWeatherCacheCallback {

    @BindView(R.id.city_toolbar)
    DiyToolbar mToolbar;

    @BindView(R.id.city_container)
    SwipeRecyclerView mSwipeRecyclerView;

    @BindView(R.id.banner_container)
    FrameLayout mBannerContainer;

    @BindView(R.id.feed_container)
    FrameLayout mFeedContainer;


    private int anim;
    private int theme;
    private boolean enable;
    private LocatedCity mLocatedCity;
    private AddressPresentImpl mAddressPresent;
    private CityPresentImpl mCityPresent;
    private CityListAdapter mCityListAdapter;
    private String mCityType;
    private WeatherPresentImpl mWeatherPresent;
    private String mCity;
    private LocationBean mAddress = null;
    private WeatherCachePresentImpl mWeatherCachePresent;
    private BanFeedHelper mBanFeedHelper;
    private List<LocationBean> mCityList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_city_manage;
    }

    @Override
    protected void intView() {
        mToolbar.setTitle("城市列表");
        mToolbar.setAddVis();
        mToolbar.finishActivity(this);


        //侧滑删除
        mSwipeRecyclerView.setSwipeMenuCreator(this);
        mSwipeRecyclerView.setOnItemMenuClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        RecyclerViewItemDistanceUtil.setDistance(mSwipeRecyclerView, this, 3.5f);
        mSwipeRecyclerView.setLayoutManager(manager);
        mCityListAdapter = new CityListAdapter();
        mSwipeRecyclerView.setAdapter(mCityListAdapter);


        //加载广告
        mBanFeedHelper = new BanFeedHelper(this, mBannerContainer, mFeedContainer);
        mBanFeedHelper.showAd(BanFeedHelper.AdType.CITY_MANAGER_PAGE);

    }


    @Override
    protected void intPresent() {
        mAddressPresent = PresentManager.getInstance().getAddressPresent();
        mAddressPresent.registerCallback(this);

        mCityPresent = CityPresentImpl.getInstance();
        mCityPresent.registerCallback(this);

        mWeatherPresent = new WeatherPresentImpl();
        mWeatherPresent.registerCallback(this);

        mWeatherCachePresent = WeatherCachePresentImpl.getInstance();
        mWeatherCachePresent.registerCallback(this);




        if (mCityPresent != null) {
            mCityPresent.getCityData();

        }
        String currentCity=SpUtils.getInstance().getString(Contents.CURRENT_CITY,"");

        if (!TextUtils.isEmpty(currentCity)) {
            mCityType = currentCity;
            LogUtils.i(this, mCityType + "---------sharedPreferences-------->" + mCityType);
            mLocatedCity = new LocatedCity(mCityType, "", "");
        }

    }


    @Override
    protected void intEvent() {
        //  LogUtils.i(this,"intEvent----------------------->"+mLocatedCity);
        //添加城市
        mToolbar.addOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityPicker.from(CityManageActivity.this)
                        .enableAnimation(enable)
                        .setAnimationStyle(anim)
                        .setLocatedCity(mLocatedCity)
                        .setOnPickListener(CityManageActivity.this).show();
            }
        });

        //刪除城市
        mCityListAdapter.setOnItemClickListener(new CityListAdapter.OnItemClickListener() {
            @Override
            public void deleteOnClick(LocationBean city, int position) {
                if (mCityPresent != null && !TextUtils.isEmpty(mCityType) && mWeatherCachePresent != null) {
                    if (city.getCity().equals(mCityType)) {
                        RxToast.error(CityManageActivity.this, "当前所在城市不能移除").show();
                    } else {
                        mCityPresent.deleteDataFromSQLite(city, position);
                        mWeatherCachePresent.deleteWeatherCache(city.getCity());
                    }
                }
            }
        });
    }


    @Override
    public void onPick(int position, City data) {
        if (mCityList!=null&&mCityList.size() < 10) {
            if (mAddressPresent != null) {
                mCity = data.getName();
                mAddressPresent.getLocationAddress(mCity);
            }
        } else {
            RxToast.normal(this, "最多只能添加10个城市").show();
        }

    }

    @Override
    public void onLocate() {
        if (mLocatedCity != null) {
            BaseApplication.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    CityPicker.from(CityManageActivity.this).locateComplete(mLocatedCity, LocateState.SUCCESS);
                }
            }, 1000);
        }
    }

    @Override
    public void onCancel() {
    }

    @Override
    public void onFail(int position, City data) {

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onLoadAddressSuccess(LocationBean addressBean) {
        if (mWeatherPresent != null) {
            this.mAddress = addressBean;
            mWeatherPresent.getDayWeatherInfo(addressBean.getLongitude(), addressBean.getLatitude());

        }
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onError() {
        RxToast.error(this, "没有网络，请重新再试").show();
    }

    @Override
    public void addState(boolean state) {
        LogUtils.i(this, "addState----------------------->" + state);
       // RxToast.success(this, state ? "添加成功" : "添加失败").show();
    }

    @Override
    public void deleteState(boolean state) {

    }

    @Override
    public void updateState(boolean state) {

    }

    @Override
    public void onLoadSuccess(List<LocationBean> cityList) {
        this.mCityList=cityList;
        mCityListAdapter.setData(cityList);
    }

    @Override
    public void onOverLimit() {

    }

    @Override
    public void onAgainAdd() {
        // RxToast.normal(this,"已经添加过了").show();
    }


    //添加右侧侧滑按钮
    @Override
    public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
        int width = getResources().getDimensionPixelSize(R.dimen.dp_70);
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        {
            SwipeMenuItem deleteItem = new SwipeMenuItem(CityManageActivity.this).setBackground(R.drawable.selector_red)
                    .setImage(R.mipmap.ic_action_delete)
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            rightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。
        }
    }

    //侧滑监听
    @Override
    public void onItemClick(SwipeMenuBridge menuBridge, int adapterPosition) {
        menuBridge.closeMenu();

        int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
        if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
            mCityListAdapter.deleteCity(adapterPosition);

        }
    }

    @Override
    public void onLoadRealtimeWeatherData(DescribeBean resultBean) {

    }

    @Override
    public void onLoadDayWeatherData(DayWeatherBean.ResultBean resultBean, List<WeatherModel> LifeBeans) {
        if (resultBean != null) {
            DayWeatherBean.ResultBean.DailyBean daily = resultBean.getDaily();
            DayWeatherBean.ResultBean.DailyBean.SkyconBean skyconBean = daily.getSkycon().get(0);
            String value = skyconBean.getValue();
            DayWeatherBean.ResultBean.DailyBean.TemperatureBean temperatureBean = daily.getTemperature().get(0);
            double max = temperatureBean.getMax();
            double min = temperatureBean.getMin();

            // LogUtils.i(this,"----------------->"+value);
            if (mAddress != null) {
                mCityPresent.addDataToSQLite(new LocationBean(mCity, mAddress.getLongitude(), mAddress.getLatitude(), value, max, min));
                finish();
            }


        }


    }

    @Override
    public void onLoadHourWeatherData(HourWeatherBean weatherBean) {

    }

    @Override
    public void onLoadRainWeatherData(RainWeatherBean rainWeatherBean) {

    }

    @Override
    public void onLoadWarningWeatherData(WarningBean warningBean) {

    }

    @Override
    public void onLoadLifeWeatherData(List<MjLifeBean> beanList) {

    }

    @Override
    public void onRefreshSuccess() {

    }

    @Override
    public void onRefreshError() {

    }

    @Override
    public void addCacheState(boolean state) {

    }

    @Override
    public void deleteCacheState(boolean state) {

    }

    @Override
    public void onLoadCacheSuccess(List<WeatherCacheBean> cacheBeanList) {

    }




    @Override
    protected void release() {
        if (mAddressPresent != null) {
            mAddressPresent.unregisterCallback(this);
        }

        if (mCityPresent != null) {
            mCityPresent.unregisterCallback(this);
        }


        if (mWeatherPresent != null) {
            mWeatherPresent.unregisterCallback(this);
        }

        if (mWeatherCachePresent != null) {
            mWeatherCachePresent.unregisterCallback(this);
        }







    }
}