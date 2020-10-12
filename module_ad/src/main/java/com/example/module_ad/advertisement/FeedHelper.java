package com.example.module_ad.advertisement;

import android.app.Activity;
import android.widget.FrameLayout;

import com.example.module_ad.base.IShowAdCallback;
import com.example.module_ad.bean.AdBean;
import com.example.module_ad.utils.AdProbabilityUtil;
import com.example.module_ad.utils.SpUtil;

public class FeedHelper {

    private Activity mActivity;
    private FrameLayout mFeedAdContainer;
    private boolean mAddToutiaoAdError=false;
    private boolean mAddTengxunAdError=false;
    private TTFeedAd mTTFeedAd;
    private TXFeedAd mTxFeedAd;

    public FeedHelper(Activity activity,FrameLayout frameLayout) {
        this.mActivity=activity;
        this.mFeedAdContainer=frameLayout;
    }


    public void showAd() {
        //拿到缓存接口信息
        if (SpUtil.isHaveAdData()) {
            AdBean.DataBean.HomePageBean.NativeScreenBean native_screen =SpUtil.getAdState().getHome_page().getNative_screen();
            //判断时候展示广告
            boolean status = native_screen.isStatus();
            String ad_percent = native_screen.getAd_percent();
            double probability = AdProbabilityUtil.showAdProbability(ad_percent);
            if (status) {
                double random = Math.random();
                if (random >probability) {
                    //初始化广告
                    showTTFeedAd();

                } else {
                    showTXFeedAd();
                }
            }
        }
    }



    //头条
    private void showTTFeedAd() {
        mTTFeedAd = new TTFeedAd(mActivity, mFeedAdContainer);
        mTTFeedAd.showRealAd();
        mTTFeedAd.setOnShowError(new IShowAdCallback() {
            @Override
            public void onShowError() {
                if (!mAddToutiaoAdError) {
                    showTXFeedAd();
                }
                mAddToutiaoAdError=true;
            }
        });

    }

    //腾讯
    private void showTXFeedAd() {
        mTxFeedAd = new TXFeedAd(mActivity, mFeedAdContainer);
        mTxFeedAd.showRealAd();
        mTxFeedAd.setOnShowError(new IShowAdCallback() {
            @Override
            public void onShowError() {
                if (!mAddTengxunAdError) {
                    showTTFeedAd();
                }
                mAddTengxunAdError=true;
            }
        });

    }

    public void releaseAd() {
        if (mTTFeedAd != null) {
            mTTFeedAd.releaseAd();
        }

        if (mTxFeedAd!=null) {
            mTxFeedAd.releaseAd();
        }
    }


}
