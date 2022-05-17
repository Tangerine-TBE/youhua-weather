package com.example.module_ad.advertisement

import android.app.Activity
import android.widget.FrameLayout
import com.example.module_ad.KS_Ad
import com.example.module_ad.utils.SpUtil

class FeedHelper(private val mActivity: Activity, private val mFeedAdContainer: FrameLayout) {
    private var mAddToutiaoAdError = false
    private var mAddTengxunAdError = false
    private var mTxFeedAd: TXFeedAd? = null
    fun showAd() {
        //拿到缓存接口信息
        if (SpUtil.isHaveAdData()) {
            val native_screen = SpUtil.getAdState().home_page!!.native_screen
            //判断时候展示广告
            val status = native_screen!!.isStatus
            val isFirstAd = native_screen.randomFirstAd()
            if (status) {
               if (!isFirstAd) {
                    //初始化广告
                    showTTFeedAd()
                } else {
                    showTXFeedAd()
                }
            }
        }
    }

    //头条
    private fun showTTFeedAd() {

        val ks_ad = KS_Ad(mActivity)
        ks_ad.loadNativeAd(mFeedAdContainer){
            if (!mAddToutiaoAdError) {
                showTXFeedAd()
            }
            mAddToutiaoAdError = true
        }
    }

    //腾讯
    private fun showTXFeedAd() {
        mTxFeedAd = TXFeedAd(mActivity, mFeedAdContainer)
        mTxFeedAd!!.showRealAd()
        mTxFeedAd!!.setOnShowError {
            if (!mAddTengxunAdError) {
                showTTFeedAd()
            }
            mAddTengxunAdError = true
        }
    }

    fun releaseAd() {
        if (mTxFeedAd != null) {
            mTxFeedAd!!.releaseAd()
        }
    }
}