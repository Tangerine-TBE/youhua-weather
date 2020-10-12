package com.example.tianqi.ui.fragment;

import android.content.Context;
import android.widget.FrameLayout;

import com.alibaba.fastjson.JSON;
import com.example.module_ad.advertisement.SplashHelper;
import com.example.module_ad.bean.AdBean;
import com.example.module_ad.utils.CommonUtil;
import com.example.module_ad.utils.SpUtil;
import com.example.tianqi.base.BaseApplication;
import com.example.tianqi.base.BaseFragment;
import com.example.tianqi.presenter.Impl.AdPresentImpl;
import com.example.tianqi.presenter.views.IAdCallback;
import com.example.tianqi.ui.activity.MainActivity;
import com.example.tianqi.utils.Contents;
import com.example.tianqi.utils.ImmersionUtil;
import com.tiantian.tianqi.R;

import java.util.Map;

import butterknife.BindView;

public class AdFragment extends BaseFragment implements IAdCallback {


    @BindView(R.id.ad_container)
    FrameLayout mSplashContainer;



    private AdPresentImpl mAdPresent;
    private SplashHelper mSplashHelper;
    private boolean isShow;
    @Override
    public int getChildLayout() {
        return R.layout.fragment_ad;
    }


    @Override
    protected void intView() {
        setViewState(ViewState.SUCCESS);
        mSplashHelper = new SplashHelper(getActivity(), mSplashContainer, MainActivity.class);
    }

    @Override
    protected void intPresent() {
        mAdPresent = AdPresentImpl.getInstance();
        mAdPresent.registerCallback(this);
        //http://114.215.47.46:8080/ytkapplicaton/anHuangliWeather?name=com.tiantian.tianqi&version=1.1&channel=_xiaomi
        if (CommonUtil.isNetworkAvailable(getContext())) {
            AdBean.DataBean data = SpUtil.getAdState();
            Map<String, String> adKey = SpUtil.getADKey();
            if (data != null || adKey != null) {

                mSplashHelper.showAd();
                isShow = true;
            }
            if (mAdPresent != null) {
                mAdPresent.toRequestAd();
            }

        } else {
            ImmersionUtil.startActivity(getContext(), MainActivity.class, true);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onLoadAdMsgSuccess(AdBean adBean) {
        if (adBean != null) {
            AdBean.DataBean data = adBean.getData();
            String ad = JSON.toJSONString(data);
            BaseApplication.getAppContext().getSharedPreferences(Contents.AD_INFO_SP, Context.MODE_PRIVATE).edit().putString(Contents.AD_INFO, ad).apply();
            if (!isShow) {
                mSplashHelper.showAd();
            }
        }
    }

    @Override
    public void onLoadAdMsgError() {
       // ImmersionUtil.startActivity(getActivity(),MainActivity.class,true);
    }

    @Override
    protected void release() {
        if (mAdPresent != null) {
            mAdPresent.unregisterCallback(this);
        }
        if (mSplashHelper != null) {
            mSplashHelper=null;
        }
    }


}
