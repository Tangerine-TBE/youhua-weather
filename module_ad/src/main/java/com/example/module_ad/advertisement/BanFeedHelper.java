package com.example.module_ad.advertisement;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;

import com.example.module_ad.base.IBaseAdBean;
import com.example.module_ad.base.IBaseXXBean;
import com.example.module_ad.base.IShowAdCallback;
import com.example.module_ad.bean.AdBean;
import com.example.module_ad.utils.AdProbabilityUtil;
import com.example.module_ad.utils.SpUtil;

public class BanFeedHelper {

    private boolean mAddToutiaoAdError = false;
    private boolean mAddTengxunAdError = false;
    private Activity mActivity;
    private TXBannerAd mTxBannerAd;
    private TXFeedAd mTxFeedAd;
    private TTBannerAd mTtBannerAd;
    private TTFeedAd mTtFeedAd;
    private FrameLayout mFeedContainer;
    private FrameLayout mBannerContainer;

    private String mNative_percent;
    private String mBanner_percent;
    private IBaseXXBean mBanner_screen;
    private IBaseXXBean mNative_screen;
    private IBaseAdBean mManager_page;


    public BanFeedHelper(Activity activity, FrameLayout banner, FrameLayout feed) {
        this.mActivity = activity;
        this.mBannerContainer = banner;
        this.mFeedContainer = feed;
    }




    public void releaseAd() {
        //穿山甲
        if (mTtFeedAd != null) {
            mTtFeedAd.releaseAd();
        }

        if (mTtBannerAd != null) {
            mTtBannerAd.releaseAd();
        }


        //广点通
        if (mTxBannerAd != null) {
            mTxBannerAd.releaseAd();
        }

        if (mTxFeedAd != null) {
            mTxFeedAd.releaseAd();
        }
    }


  public   enum AdType {
        CITY_MANAGER_PAGE,
        SETTING_PAGE,
        AIRQUALITY_PAGE,
        TEMPERATURE_PAGE,
        HOUSINGLOAN_PAGE
    }

    private void switchAdType(AdType type, AdBean.DataBean dataBean) {
        if (type== AdType.CITY_MANAGER_PAGE) {
            mManager_page = dataBean.getCity_manager_page();
        } else if (type== AdType.SETTING_PAGE) {
            mManager_page = dataBean.getSetting_page();
        } else if (type== AdType.AIRQUALITY_PAGE) {
            mManager_page = dataBean.getAirquality_page();
        } else if (type== AdType.TEMPERATURE_PAGE) {
            mManager_page = dataBean.getTemperature_page();
        } else if (type== AdType.HOUSINGLOAN_PAGE) {
            mManager_page = dataBean.getHousingloan_page();
        }
    }

    public void showAd(AdType type) {
        mFeedContainer.setVisibility(View.VISIBLE);
        if (SpUtil.isHaveAdData()) {
            //状态信息
            switchAdType(type, SpUtil.getAdState());
            mBanner_screen = mManager_page.getBaseBanner_screen();
            mNative_screen = mManager_page.getBaseBanner_screen();

            // 显示比例
            mBanner_percent = mBanner_screen.getBaseAd_percent();
            double bannerProbability = AdProbabilityUtil.showAdProbability(mBanner_percent);
            mNative_percent = mNative_screen.getBaseAd_percent();
            double nativeProbability = AdProbabilityUtil.showAdProbability(mNative_percent);

            // TODO: 2020/7/16
            //按比例展示banner
            double random = Math.random();
            if (mBanner_screen.isBaseStatus()) {
                if (random >= bannerProbability) {
                    showTTBannerAd();

                } else {
                    showTXBannerAd();
                }
            }

            //按比例展示feed
            if (mNative_screen.isBaseStatus()) {
                if (random >= nativeProbability) {
                    showTTFeedAd();
                } else {
                    showTXFeedAd();
                }
            }

        }
    }


    private void showTTBannerAd() {
        //TT
        mTtBannerAd = new TTBannerAd(mActivity, mBannerContainer);
        mTtBannerAd.showRealAd();
        mTtBannerAd.setOnShowError(new IShowAdCallback() {
            @Override
            public void onShowError() {
                if (!mAddToutiaoAdError) {
                    showTXBannerAd();
                }
                mAddToutiaoAdError = true;
            }
        });

    }

    private void showTXBannerAd() {
        //TX
        mTxBannerAd = new TXBannerAd(mActivity, mBannerContainer);
        mTxBannerAd.showRealAd();
        mTxBannerAd.setOnShowError(new IShowAdCallback() {
            @Override
            public void onShowError() {
                if (!mAddTengxunAdError) {
                    showTTBannerAd();
                }
                mAddTengxunAdError = true;
            }
        });
    }


    private void showTTFeedAd() {
        mTtFeedAd = new TTFeedAd(mActivity, mFeedContainer);
        mTtFeedAd.showRealAd();
        mTtFeedAd.setOnShowError(new IShowAdCallback() {
            @Override
            public void onShowError() {
                if (!mAddToutiaoAdError) {
                    showTXFeedAd();
                }
                mAddToutiaoAdError = true;
            }
        });
    }

    private void showTXFeedAd() {
        mTxFeedAd = new TXFeedAd(mActivity, mFeedContainer);
        mTxFeedAd.showRealAd();
        mTxFeedAd.setOnShowError(new IShowAdCallback() {
            @Override
            public void onShowError() {
                if (!mAddTengxunAdError) {
                    showTTFeedAd();
                }
                mAddTengxunAdError = true;
            }
        });
    }

}
