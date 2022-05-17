package com.example.module_ad.advertisement

import android.app.Activity
import android.view.View
import android.widget.FrameLayout
import com.example.module_base.MainBaseApplication
import com.example.module_ad.utils.StartActivityUtil
import com.example.module_ad.KS_Ad
import com.example.module_ad.utils.SpUtil

class SplashHelper(
    private val mActivity: Activity,
    private val mSplashContainer: FrameLayout,
    private val mClass: Class<*>
) {
    private var mTxSplashAd: TXSplashAd? = null
    private var mAddToutiaoAdError = false
    private var mAddTengxunAdError = false
    fun showAd() {
        //广告key
        if (SpUtil.isHaveAdData()) {
            val spread_screen = SpUtil.getAdState().start_page!!.spread_screen
            //显示状态
            val status = spread_screen!!.isStatus
            val isFirstAd = spread_screen.randomFirstAd()
            if (status) {
                MainBaseApplication.getHandler().post {
                    mSplashContainer.visibility = View.VISIBLE
                    if (!isFirstAd) {
                        showTtSplashAd()
                    } else {
                        showTxSplashAd()
                    }
                }
            } else {
                StartActivityUtil.startActivity(mActivity, mClass, true)
            }
        } else {
            StartActivityUtil.startActivity(mActivity, mClass, true)
        }
    }

    // 加载头条广告
    private fun showTtSplashAd() {
        // TODO: 2020/7/17
//        mTtSplashAd = TTSplashAd(mActivity, mSplashContainer, true, mClass)
//        mTtSplashAd!!.showRealAd()
//        mTtSplashAd!!.setOnShowError {
//            if (!mAddToutiaoAdError) {
//                showTxSplashAd()
//            }
//            mAddToutiaoAdError = true
//            showADError()
//        }
        val ks_ad = KS_Ad(mActivity)
        ks_ad.loadSplashAd(mSplashContainer,mClass,true){
            if (!mAddToutiaoAdError) {
                showTxSplashAd()
            }
            mAddToutiaoAdError = true
            showADError()
        }
    }

    // 加载腾讯广告
    private fun showTxSplashAd() {
        // TODO: 2020/7/17
        mTxSplashAd = TXSplashAd(mActivity, mSplashContainer, true, mClass)
        mTxSplashAd!!.showRealAd()
        mTxSplashAd!!.setOnShowError {
            if (!mAddTengxunAdError) {
                showTtSplashAd()
            }
            mAddTengxunAdError = true
            showADError()
        }
    }

    //都加载失败
    private fun showADError() {
        if (mAddTengxunAdError and mAddToutiaoAdError) {
            StartActivityUtil.startActivity(mActivity, mClass, true)
        }
    }
}