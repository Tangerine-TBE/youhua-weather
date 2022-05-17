package com.example.module_ad.advertisement

import android.app.Activity
import android.view.View
import android.widget.FrameLayout
import com.example.module_ad.KS_Ad
import com.example.module_ad.base.IBaseXXBean
import com.example.module_ad.base.IBaseAdBean
import com.example.module_ad.utils.AdProbabilityUtil
import com.example.module_ad.bean.AdBean
import com.example.module_ad.utils.SpUtil

class BanFeedHelper(
    private val mActivity: Activity,
    private val mBannerContainer: FrameLayout,
    private val mFeedContainer: FrameLayout
) {
    private var mAddTengxunAdBanError = false
    private var mAddToutiaoAdFeedError = false
    private var mAddTengxunAdFeedError = false
    private var mTxBannerAd: TXBannerAd? = null
    private var mTxFeedAd: TXFeedAd? = null
    private var mNative_percent: String? = null
    private var mBanner_percent: String? = null
    private var mBanner_screen: IBaseXXBean? = null
    private var mNative_screen: IBaseXXBean? = null
    private var mManager_page: IBaseAdBean? = null
    fun releaseAd() {


        //广点通
        if (mTxBannerAd != null) {
            mTxBannerAd!!.releaseAd()
        }
        if (mTxFeedAd != null) {
            mTxFeedAd!!.releaseAd()
        }
    }

    enum class AdType {
        CITY_MANAGER_PAGE, SETTING_PAGE, AIRQUALITY_PAGE, TEMPERATURE_PAGE, HOUSINGLOAN_PAGE
    }

    private fun switchAdType(type: AdType, dataBean: AdBean.DataBean) {
        if (type == AdType.CITY_MANAGER_PAGE) {
            mManager_page = dataBean.city_manager_page
        } else if (type == AdType.SETTING_PAGE) {
            mManager_page = dataBean.setting_page
        } else if (type == AdType.AIRQUALITY_PAGE) {
            mManager_page = dataBean.airquality_page
        } else if (type == AdType.TEMPERATURE_PAGE) {
            mManager_page = dataBean.temperature_page
        } else if (type == AdType.HOUSINGLOAN_PAGE) {
            mManager_page = dataBean.housingloan_page
        }
    }

    fun showAd(type: AdType) {
        mFeedContainer.visibility = View.VISIBLE
        if (SpUtil.isHaveAdData()) {
            //状态信息
            switchAdType(type, SpUtil.getAdState())
            mBanner_screen = mManager_page!!.baseBanner_screen
            mNative_screen = mManager_page!!.baseBanner_screen

            // 显示比例
            mBanner_percent = mBanner_screen?.getBaseAd_percent()
            val bannerProbability = AdProbabilityUtil.showAdProbability(mBanner_percent)
            mNative_percent = mNative_screen?.getBaseAd_percent()
            val nativeProbability = AdProbabilityUtil.showAdProbability(mNative_percent)

            // TODO: 2020/7/16
            //按比例展示banner
            val random = Math.random()
            if (mBanner_screen?.isBaseStatus ==true) {
                if (random >= bannerProbability) {
                    showTTBannerAd()
                } else {
                    showTXBannerAd()
                }
            }

            //按比例展示feed
            if (mNative_screen?.isBaseStatus ==true) {
                if (random >= nativeProbability) {
                    showTTFeedAd()
                } else {
                    showTXFeedAd()
                }
            }
        }
    }

    private fun showTTBannerAd() {

        showTXBannerAd()
    }

    private fun showTXBannerAd() {
        //TX
        mTxBannerAd = TXBannerAd(mActivity, mBannerContainer)
        mTxBannerAd!!.showRealAd()
        mTxBannerAd!!.setOnShowError {
            if (!mAddTengxunAdBanError) {
                showTTBannerAd()
            }
            mAddTengxunAdBanError = true
        }
    }

    private fun showTTFeedAd() {


        val ks_ad = KS_Ad(mActivity)
        ks_ad.loadNativeAd(mFeedContainer){
            if (!mAddToutiaoAdFeedError) {
                showTXFeedAd()
            }
            mAddToutiaoAdFeedError = true
        }
    }

    private fun showTXFeedAd() {
        mTxFeedAd = TXFeedAd(mActivity, mFeedContainer)
        mTxFeedAd!!.showRealAd()
        mTxFeedAd!!.setOnShowError {
            if (!mAddTengxunAdFeedError) {
                showTTFeedAd()
            }
            mAddTengxunAdFeedError = true
        }
    }
}