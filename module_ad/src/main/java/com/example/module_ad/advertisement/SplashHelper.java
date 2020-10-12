package com.example.module_ad.advertisement;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;

import com.example.module_ad.base.IShowAdCallback;
import com.example.module_ad.bean.AdBean;
import com.example.module_ad.utils.AdProbabilityUtil;
import com.example.module_ad.utils.LogUtils;
import com.example.module_ad.utils.SpUtil;
import com.example.module_ad.utils.StartActivityUtil;
import com.example.module_base.MainBaseApplication;

public class SplashHelper {

    private Activity mActivity;
    private FrameLayout mSplashContainer;
    private Class mClass;
    private TTSplashAd mTtSplashAd;
    private TXSplashAd mTxSplashAd;
    private boolean mAddToutiaoAdError = false;
    private boolean mAddTengxunAdError = false;

    public SplashHelper(Activity activity,FrameLayout frameLayout,Class  aClass) {
        this.mActivity=activity;
        this.mSplashContainer=frameLayout;
        this.mClass=aClass;
    }

    public void showAd() {
        //广告key
        if (SpUtil.isHaveAdData()) {
        AdBean.DataBean.StartPageBean.SpreadScreenBean spread_screen = SpUtil.getAdState().getStart_page().getSpread_screen();
        //显示状态
        boolean status = spread_screen.isStatus();
        //显示比例
        String ad_percent = spread_screen.getAd_percent();
        final double probability = AdProbabilityUtil.showAdProbability(ad_percent);
            if (status) {
                MainBaseApplication.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        double random = Math.random();
                        mSplashContainer.setVisibility(View.VISIBLE);
                        if (random > probability) {
                            showTtSplashAd();

                        } else {
                            showTxSplashAd();

                        }
                        LogUtils.i(this, "onLoadAdMsgSuccess-------22222222222222222222-------------->" + random);
                    }
                });
            } else {
                StartActivityUtil.startActivity(mActivity, mClass, true);
            }

        }
        else {
            StartActivityUtil.startActivity(mActivity, mClass, true);
        }
    }

    // 加载头条广告
    private void showTtSplashAd() {

        // TODO: 2020/7/17
        mTtSplashAd = new TTSplashAd(mActivity,  mSplashContainer, true,mClass);
        mTtSplashAd.showRealAd();
        mTtSplashAd.setOnShowError(new IShowAdCallback() {
            @Override
            public void onShowError() {
                if (!mAddToutiaoAdError) {
                    showTxSplashAd();
                }

                mAddToutiaoAdError = true;
                showADError();
            }
        });


    }

    // 加载腾讯广告
    private void showTxSplashAd() {
        // TODO: 2020/7/17
        mTxSplashAd = new TXSplashAd(mActivity, mSplashContainer, true,mClass);
        mTxSplashAd.showRealAd();
        mTxSplashAd.setOnShowError(new IShowAdCallback() {
            @Override
            public void onShowError() {
                if (!mAddTengxunAdError) {
                    showTtSplashAd();
                }
                mAddTengxunAdError=true;
                showADError();
            }
        });

    }

    //都加载失败
    private void showADError() {
        if (mAddTengxunAdError&mAddToutiaoAdError) {
            StartActivityUtil.startActivity(mActivity, mClass, true);
        }
    }
}
