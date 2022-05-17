package com.example.module_ad

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.fastjson.JSON
import com.bumptech.glide.Glide
import com.example.module_ad.bean.AdBean
import com.example.module_ad.utils.Contents
import com.example.module_base.MainBaseApplication
import com.kwad.sdk.api.*
import com.kwad.sdk.api.KsInnerAd.KsInnerAdInteractionListener
import com.kwad.sdk.api.KsNativeAd.VideoPlayListener
import com.kwad.sdk.api.KsSplashScreenAd.SplashScreenAdInteractionListener
import com.kwad.sdk.api.model.*
import java.util.*

class KS_Ad constructor(val mActivity: Activity) {
    companion object{
        /**
         * @see AdUtil.askADConfig
         * @see Contents.AD_INFO
         */
        var adConfig:AdBean.DataBean?=null
            get(){
                if (field==null){
                    val ad_info: SharedPreferences = MainBaseApplication.getApplication().getSharedPreferences(Contents.AD_INFO_SP, Context.MODE_PRIVATE)
                    val ad = ad_info.getString(Contents.AD_INFO, "")
                    val dataBean = JSON.parseObject(ad, AdBean.DataBean::class.java)
                    field=dataBean
                }
                return field
            }
        fun getAppKey():String{
            return adConfig?.advertisement?.kWaiAppKey?:""
        }
        fun getKaiPing():Long{
            return adConfig?.advertisement?.kWaiKaiPing?.toLong()?:0L
        }

        fun getBanner():Long{
            return adConfig?.advertisement?.kWaiBannerKey?.toLong()?:0L
        }
        fun getChaPing():Long{
            return adConfig?.advertisement?.kWaiChaPingKey?.toLong()?:0L
        }
        fun getJiLi():Long{
            return adConfig?.advertisement?.kWaiJiLiKey?.toLong()?:0L
        }
        fun getSenior():Long{
            return adConfig?.advertisement?.kWaiSeniorKey?.toLong()?:0L
        }

        fun initKSSdk(context: Application):Boolean{
            KsAdSDK.setPersonalRecommend(true)//个性化推荐⼴告开关：关闭后，看到的⼴告数量不变，相关度将降低。 是否允许开启⼴告的个性化推荐 （false-关闭，true-开启），由开发者通过SDK以下接⼝来设置。不设置的话则默认为true。
            KsAdSDK.setProgrammaticRecommend(true)//程序化推荐⼴告开关：关闭后，看到的⼴告数量不变，但将不会为你推荐程序化⼴告。 是否允许开启⼴ 告的程序化推荐（false-关闭，true-开启），由开发者通过SDK以下接⼝来设置。不设置的话则默认为 true。
            return KsAdSDK.init(context, SdkConfig.Builder()
                    .appId(getAppKey()) // 测试aapId，请联系快⼿平台申请正式AppId，必填
                    .appName(context.applicationInfo.loadLabel(context.packageManager).toString()) // 测试appName，请填写您应⽤的名称， ⾮必填
                    .showNotification(false) // 是否展示下载通知栏
                    .debug(BuildConfig.DEBUG) // 是否开启sdk 调试⽇志 可选
                    .build())
        }
    }

//    constructor(mActivity: Activity,frameLayout:FrameLayout,isClose:Boolean,aClass:Class<*>):this(mActivity){
//
//    }

    private val adRequestManager by lazy {
        KsAdSDK.getLoadManager()
    }

    fun loadSplashAd(mContainer:FrameLayout,aClass:Class<*>,isStart:Boolean,errorAction:(()->Unit)?=null){
        val scene = KsScene.Builder(getKaiPing())        //是否需要开屏小窗展示，默认为false, 设置false后将不会回调 onShowMiniWindow
                .needShowMiniWindow(false)
                .build(); // 此为测试posId，请联系快手平台申请正式posId //是否需要开屏小窗展示，默认为false, 设置false后将不会回调 onShowMiniWindow

        adRequestManager?.loadSplashScreenAd(scene, object : KsLoadManager.SplashScreenAdListener {
            override fun onError(code: Int, msg: String?) {
                iLog("开屏广告请求失败,code:$code,message:$msg")
                if (errorAction!=null){
                    errorAction.invoke()
                }else {
                    mActivity.finish()
                    if (isStart) {
                        mActivity.startActivity(Intent(mActivity, aClass))
                    }
                }
            }

            override fun onRequestResult(p0: Int) {

            }

            override fun onSplashScreenAdLoad(splashScreenAd: KsSplashScreenAd?) {
                val fragment = splashScreenAd?.getFragment(object : SplashScreenAdInteractionListener {
                    override fun onAdClicked() {
                        iLog("开屏广告点击")
                        /**
                         * 开屏广告点击会吊起h5或应用商店，并回调onAdClick(), mGotoMainActivity控制由h5或应用商店返回后是否直接进入主界面
                         * 建议当需要展示开屏小窗时设置为返回后进入主界面，其他情况设为false以继续执行开屏的倒计时
                         */
//                        mGotoMainActivity = mNeedShowMiniWindow
                    }

                    override fun onAdShowError(code: Int, extra: String) {
                        iLog("开屏广告显示错误 $code extra $extra")
                        //点击不触发显示miniWindow
                        if (errorAction!=null){
                            errorAction.invoke()
                        }else {
                            mActivity.finish()
                            if (isStart) {
                                mActivity.startActivity(Intent(mActivity, aClass))
                            }
                        }
                    }

                    override fun onAdShowEnd() {
                        iLog("开屏广告显示结束")
                        mActivity.finish()
                        if (isStart) {
                            mActivity.startActivity(Intent(mActivity, aClass))
                        }
                    }

                    override fun onAdShowStart() {
                        iLog("开屏广告显示开始")
                    }

                    override fun onSkippedAd() {
                        iLog("用户跳过开屏广告")
                        mActivity.finish()
                        if (isStart) {
                            mActivity.startActivity(Intent(mActivity, aClass))
                        }
                    }

                    override fun onDownloadTipsDialogShow() {
                        iLog("开屏广告显示下载合规弹窗")
                    }

                    override fun onDownloadTipsDialogDismiss() {
                        iLog("开屏广告关闭下载合规弹窗")
                    }

                    override fun onDownloadTipsDialogCancel() {
                        iLog("开屏广告取消下载合规弹窗")
                    }
                })
                if (mActivity?.isFinishing == false) {
                    mActivity?.apply {
                        if (this is AppCompatActivity) {
                            supportFragmentManager.beginTransaction()
                                    .replace(mContainer?.id ?: return@apply, fragment
                                            ?: return@apply)
                                    .commitAllowingStateLoss()
                        } else {
                            mActivity.finish()
                            if (isStart) {
                                mActivity.startActivity(Intent(mActivity, aClass))
                            }
                        }
                    }

                }
            }
        })
    }

    fun loadInsertAd(){
        // 此为测试posId，请联系快手平台申请正式posId
        val scene = KsScene.Builder(getChaPing())
//                .setBackUrl("ksad://returnback")
                .build()
        adRequestManager?.loadInterstitialAd(scene, object : KsLoadManager.InterstitialAdListener {
            override fun onError(code: Int, msg: String?) {
                iLog("插屏广告请求失败$code$msg")
            }

            override fun onRequestResult(adNumber: Int) {
                iLog("插屏广告请求填充个数 $adNumber")
            }

            override fun onInterstitialAdLoad(adList: MutableList<KsInterstitialAd>?) {
                if (adList != null && adList.size > 0) {
                    val mKsInterstitialAd = adList[0]
                    iLog("插屏广告请求成功")
                    val videoPlayConfig = KsVideoPlayConfig.Builder()
                            .videoSoundEnable(true)
                            .showLandscape(mActivity.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
                            .build()
                    showInterstitialAd(mKsInterstitialAd, videoPlayConfig)
                }
            }

        })
    }

    // 1.请求激励视频广告，获取广告对象，KsRewardVideoAd
    fun requestRewardAd() {
        val builder = KsScene.Builder(getJiLi())
//                .setBackUrl("ksad://returnback")
                .screenOrientation(SdkConfig.SCREEN_ORIENTATION_PORTRAIT)
//        if (/*激励视频服务器回调*/) {
//            val rewardCallbackExtraData: MutableMap<String, String> = HashMap()
//            rewardCallbackExtraData["thirdUserId"] = "test-uerid-jia"
//            rewardCallbackExtraData["extraData"] = "testExtraData"
//            builder.rewardCallbackExtraData(rewardCallbackExtraData)
//        }
        val scene = builder.build() // 此为测试posId，请联系快手平台申请正式posId
        // 请求的期望屏幕方向传递为1，表示期望为竖屏
        KsAdSDK.getLoadManager()?.loadRewardVideoAd(scene, object : KsLoadManager.RewardVideoAdListener {
            override fun onError(code: Int, msg: String) {
                iLog("激励视频广告请求失败$code$msg")
            }

            override fun onRequestResult(adNumber: Int) {
                iLog("激励视频广告请求结果返回 $adNumber")
            }

            override fun onRewardVideoAdLoad(adList: List<KsRewardVideoAd>?) {
                if (adList != null && adList.isNotEmpty()) {
                    val mRewardVideoAd = adList[0]

                    // 设置激励视频内部广告的监听
                    mRewardVideoAd.setInnerAdInteractionListener(
                            object : KsInnerAdInteractionListener {
                                override fun onAdClicked(ksInnerAd: KsInnerAd) {
                                    iLog("激励视频内部广告点击：" + ksInnerAd.type)
                                }

                                override fun onAdShow(ksInnerAd: KsInnerAd) {
                                    iLog("激励视频内部广告曝光：" + ksInnerAd.type)
                                }
                            })
                    iLog("激励视频广告请求成功")
//                    if (mIsShowPortrait) {
                    showRewardVideoAd(mRewardVideoAd)
//                    } else {
//                        showRewardVideoAd(**)
//                    }
                }
            }
        })
    }

    // 2.展示激励视频广告，通过步骤1获取的KsRewardVideoAd对象，判断缓存有效，则设置监听并展示
    fun showRewardVideoAd(mRewardVideoAd: KsRewardVideoAd) {
        if (mRewardVideoAd.isAdEnable) {
            mRewardVideoAd
                    .setRewardAdInteractionListener(object : KsRewardVideoAd.RewardAdInteractionListener {
                        override fun onAdClicked() {
                            iLog("激励视频广告点击")
                        }

                        override fun onPageDismiss() {
                            iLog("激励视频广告关闭")
                        }

                        override fun onVideoPlayError(code: Int, extra: Int) {
                            iLog("激励视频广告播放出错")
                        }

                        override fun onVideoPlayEnd() {

                        }

                        override fun onVideoSkipToEnd(playDuration: Long) {
                            iLog("激励视频广告跳过播放完成")
                        }

                        override fun onVideoPlayStart() {
                            iLog("激励视频广告播放开始")
                        }

                        /**
                         * 激励视频广告激励回调，只会回调一次
                         */
                        override fun onRewardVerify() {
                            iLog("激励视频广告获取激励")
                        }

                        /**
                         * 视频激励分阶段回调
                         * @param taskType 当前激励视频所属任务类型
                         * RewardTaskType.LOOK_VIDEO 观看视频类型             属于浅度奖励类型
                         * RewardTaskType.LOOK_LANDING_PAGE 浏览落地页N秒类型  属于深度奖励类型
                         * RewardTaskType.USE_APP 下载使用App N秒类型          属于深度奖励类型
                         * @param currentTaskStatus  当前所完成任务类型，@RewardTaskType中之一
                         */
                        override fun onRewardStepVerify(taskType: Int, currentTaskStatus: Int) {
                            iLog("激励视频广告分阶段获取激励，当前任务类型为：" + getTaskStatusStr(taskType) +
                                    "，当前完成任务类型为：" + getTaskStatusStr(currentTaskStatus))
                        }
                    })

            // 设置"再看一个" 广告的监听
            mRewardVideoAd.setRewardPlayAgainInteractionListener(object : KsRewardVideoAd.RewardAdInteractionListener {
                private val PREFIX = "再看一个"
                override fun onAdClicked() {
                    iLog(PREFIX + "激励视频广告点击")
                }

                override fun onPageDismiss() {
                    iLog(PREFIX + "激励视频广告关闭")
                }

                override fun onVideoPlayError(code: Int, extra: Int) {
                    iLog(PREFIX + "激励视频广告播放出错")
                }

                override fun onVideoPlayEnd() {
                    iLog(PREFIX + "激励视频广告播放完成")
                }

                override fun onVideoSkipToEnd(playDuration: Long) {
                    iLog(PREFIX + "激励视频广告跳过播放完成")
                }

                override fun onVideoPlayStart() {
                    iLog(PREFIX + "激励视频广告播放开始")
                }

                /**
                 * 激励视频广告激励回调，只会回调一次
                 */
                override fun onRewardVerify() {
                    iLog(PREFIX + "激励视频广告获取激励")
                }

                /**
                 * 视频激励分阶段回调
                 * @param taskType 当前激励视频所属任务类型
                 * RewardTaskType.LOOK_VIDEO 观看视频类型             属于浅度奖励类型
                 * RewardTaskType.LOOK_LANDING_PAGE 浏览落地页N秒类型  属于深度奖励类型
                 * RewardTaskType.USE_APP 下载使用App N秒类型          属于深度奖励类型
                 * @param currentTaskStatus  当前所完成任务类型，@RewardTaskType中之一
                 */
                override fun onRewardStepVerify(taskType: Int, currentTaskStatus: Int) {
                    iLog("激励视频广告分阶段获取激励，当前任务类型为：" + getTaskStatusStr(taskType) +
                            "，当前完成任务类型为：" + getTaskStatusStr(currentTaskStatus))
                }
            })
            mRewardVideoAd.showRewardVideoAd(mActivity, null)
        } else {
            iLog("暂无可用激励视频广告，请等待缓存加载或者重新刷新")
        }
    }

    fun getInterval1(time: Long): String {
        var txt: String? = null
        val hour = time % (24 * 3600) / 3600 // 小时
        val minute = time % 3600 / 60 // 分钟
        txt = hour.toString() + "小时" + minute + "分"
        return txt
    }

    private fun getTaskStatusStr(taskType: Int): String {
        var taskStatusStr = ""
        when (taskType) {
            RewardTaskType.LOOK_VIDEO -> taskStatusStr = "观看视频"
            RewardTaskType.LOOK_LANDING_PAGE -> taskStatusStr = "浏览落地页"
            RewardTaskType.USE_APP -> taskStatusStr = "使用APP"
        }
        return taskStatusStr
    }

    private fun showInterstitialAd(mKsInterstitialAd: KsInterstitialAd, videoPlayConfig: KsVideoPlayConfig) {
        mKsInterstitialAd
                .setAdInteractionListener(object : KsInterstitialAd.AdInteractionListener {
                    override fun onAdClicked() {
                        iLog("插屏广告点击")
                    }

                    override fun onAdShow() {
                        iLog("插屏广告曝光")
                    }

                    override fun onAdClosed() {
                        iLog("用户点击插屏关闭按钮")
                    }

                    override fun onPageDismiss() {
                        iLog("插屏广告关闭")
                    }

                    override fun onVideoPlayError(code: Int, extra: Int) {
                        iLog("插屏广告播放出错")
                    }

                    override fun onVideoPlayEnd() {
                        iLog("插屏广告播放完成")
                    }

                    override fun onVideoPlayStart() {
                        iLog("插屏广告播放开始")
                    }

                    override fun onSkippedAd() {
                        iLog("插屏广告播放跳过")
                    }
                })
        mKsInterstitialAd.showInterstitialAd(mActivity, videoPlayConfig)
    }

    fun loadNativeAd(mContainer: FrameLayout,errorAction:(()->Unit)?=null){
        val scene = KsScene.Builder(getSenior()).adNum(1).build() // 此为测试posId，请联系快手平台申请正式posId,

        adRequestManager?.loadNativeAd(scene, object : KsLoadManager.NativeAdListener {
            override fun onError(code: Int, msg: String?) {
                iLog("广告数据请求失败$code$msg")
                errorAction?.invoke()
            }

            override fun onNativeAdLoad(adList: MutableList<KsNativeAd>?) {
                if (adList == null || adList.isEmpty()) {
                    iLog("广告数据为空")
                    errorAction?.invoke()
                    return
                }
                val ksNativeAd = adList.first()
                val container = mContainer
                if (container?.visibility != View.VISIBLE) {
                    container?.visibility = View.VISIBLE
                }
                val adView = when (ksNativeAd.materialType) {
                    MaterialType.VIDEO ->         // 视频素材，渲染自定义的视频广告
                        getVideoItemView(container, ksNativeAd)
                    MaterialType.SINGLE_IMG ->         // 单图素材，渲染自定义的单图广告
                        getSingleImageItemView(container, ksNativeAd)
                    MaterialType.GROUP_IMG ->         // 组图素材，渲染自定义的组图广告
                        getGroupImageItemView(container, ksNativeAd)
                    MaterialType.UNKNOWN -> getNormalItemView(container)
                    else -> getNormalItemView(container)
                }
                container?.removeAllViews()
                container?.addView(adView)
            }
        })
    }

    private fun bindCommonData(convertView: ViewGroup, adBaseViewHolder: AdBaseViewHolder,
                               ad: KsNativeAd) {
        // 点击转换view的集合，传入的view点击时会触发转换操作：app下载， 打开h5页面
        val clickViewMap: MutableMap<View, Int> = HashMap()
        clickViewMap[adBaseViewHolder.mAdConvertBtn] = KsNativeConvertType.CONVERT
        clickViewMap[adBaseViewHolder.convertView] = KsNativeConvertType.CONVERT
        clickViewMap[adBaseViewHolder.mAdIcon] = KsNativeConvertType.SHOW_DOWNLOAD_TIPS_DIALOG
        clickViewMap[adBaseViewHolder.mAdName] = KsNativeConvertType.SHOW_DOWNLOAD_TIPS_DIALOG
        clickViewMap[adBaseViewHolder.mAdDes] = KsNativeConvertType.SHOW_DOWNLOAD_TIPS_DIALOG
        clickViewMap[adBaseViewHolder.mAdDesc] = KsNativeConvertType.SHOW_DOWNLOAD_TIPS_DIALOG

        // 如果是自定义弹窗，请使用下面的配置
        // 注册View的点击，点击后触发转化
        ad.registerViewForInteraction(mActivity, convertView, clickViewMap,
                object : KsNativeAd.AdInteractionListener {
                    override fun onAdClicked(p0: View?, ad: KsNativeAd?) {
                        iLog("广告" + ad?.appName + "被点击")
                    }

                    override fun onAdShow(ad: KsNativeAd?) {
                        iLog("广告" + ad?.appName + "展示")
                    }

                    override fun handleDownloadDialog(p0: DialogInterface.OnClickListener?): Boolean {
                        return true
                    }

                    override fun onDownloadTipsDialogShow() {
                        iLog("广告展示下载合规弹窗")
                    }

                    override fun onDownloadTipsDialogDismiss() {
                        iLog("广告关闭下载合规弹窗")
                    }
                })

        // 其他数据
        Log.d("AppInfo", "应用名字 = " + ad.appName)
        Log.d("AppInfo", "应用包名 = " + ad.appPackageName)
        Log.d("AppInfo", "应用版本 = " + ad.appVersion)
        Log.d("AppInfo", "开发者 = " + ad.corporationName)
        Log.d("AppInfo", "包大小 = " + ad.appPackageSize)
        Log.d("AppInfo", "隐私条款链接 = " + ad.appPrivacyUrl)
        Log.d("AppInfo", "权限信息 = " + ad.permissionInfo)
        Log.d("AppInfo", "权限信息链接 = " + ad.permissionInfoUrl)
        // 获取app的评分，取值范围0~5.0
        Log.d("AppInfo", "应用评分 = " + ad.appScore)
        // 获取app下载次数文案，例如：800W此下载，自行渲染。
        Log.d("AppInfo", "app下载次数文案 = " + ad.appDownloadCountDes)

        // 广告描述
        adBaseViewHolder.mAdDes.text = ad.adDescription
        val adIconUrl = ad.appIconUrl
        // 广告icon
        if (!TextUtils.isEmpty(adIconUrl)) {
            Glide.with(mActivity).load(adIconUrl).into(adBaseViewHolder.mAdIcon)
            adBaseViewHolder.mAdIcon.visibility = View.VISIBLE
        } else {
            adBaseViewHolder.mAdIcon.visibility = View.GONE
        }
        // 广告转化文案
        adBaseViewHolder.mAdConvertBtn.text = ad.actionDescription
        // 广告名称
        if (ad.interactionType == InteractionType.DOWNLOAD) {
            adBaseViewHolder.mAdName.text = ad.appName
            // 下载类型的可以设置下载监听
            bindDownloadListener(adBaseViewHolder, ad)
        } else {
            adBaseViewHolder.mAdName.text = ad.productName
        }
        // 广告描述
        adBaseViewHolder.mAdDesc.text = ad.adDescription

        // 不喜欢
        adBaseViewHolder.mDislikeBtn.setOnClickListener { iLog("广告" + ad.appName + "不喜欢点击") }
        // 广告来源
        val adSource = ad.adSource
        val grayMode = true // 开发者可根据实际需要调整
        if (TextUtils.isEmpty(adSource)) {
            adBaseViewHolder.mAdSourceDesc.visibility = View.GONE
            adBaseViewHolder.mAdSourceDesc.text = ""
            adBaseViewHolder.mAdLogoIcon.visibility = View.GONE
        } else {
            Glide.with(mActivity).load(ad.getAdSourceLogoUrl(if (grayMode) AdSourceLogoType.GREY else AdSourceLogoType.NORMAL)).into(adBaseViewHolder.mAdLogoIcon)
            adBaseViewHolder.mAdSourceDesc.setTextColor(if (grayMode) -0x636364 else -0x66000001)
            adBaseViewHolder.mAdSourceDesc.text = adSource
        }
    }

    /**
     * 使用SDK渲染的播放控件
     */
    private fun getVideoItemView(parent: ViewGroup, ksNativeAd: KsNativeAd): View? {
        val convertView: View = LayoutInflater.from(mActivity).inflate(R.layout.native_item_video, parent, false)
        val videoViewHolder = AdVideoViewHolder(convertView)

        // 设置广告数据
        bindCommonData(convertView as ViewGroup, videoViewHolder, ksNativeAd)
        ksNativeAd.setVideoPlayListener(object : VideoPlayListener {
            override fun onVideoPlayStart() {
                iLog("onVideoPlayStart")
            }

            override fun onVideoPlayComplete() {
                iLog("onVideoPlayComplete")
            }

            override fun onVideoPlayError(what: Int, extra: Int) {
                iLog("onVideoPlayError")
            }
        })

        // SDK默认渲染的视频view
        val videoPlayConfig = KsAdVideoPlayConfig.Builder()
                .build()

        val videoView = ksNativeAd.getVideoView(mActivity, videoPlayConfig)
        if (videoView != null && videoView.parent == null) {
            videoViewHolder.mAdVideoContainer.removeAllViews()
            videoViewHolder.mAdVideoContainer.addView(videoView)
        }
        return convertView
    }

    protected fun getSingleImageItemView(parent: ViewGroup?, ksNativeAd: KsNativeAd): View? {
        val convertView: View = LayoutInflater.from(mActivity).inflate(R.layout.native_item_single_image, parent, false)
        val viewHolder: AdSingleImageViewHolder = AdSingleImageViewHolder(convertView)
        bindCommonData(convertView as ViewGroup, viewHolder, ksNativeAd)

        // 获取图片资源
        if (ksNativeAd.imageList != null && !ksNativeAd.imageList!!.isEmpty()) {
            val image = ksNativeAd.imageList!![0]
            if (image != null && image.isValid) {
                Glide.with(mActivity).load(image.imageUrl).into(viewHolder.mAdImage)
            }
        }
        return convertView
    }

    protected fun getGroupImageItemView(parent: ViewGroup?, ksNativeAd: KsNativeAd): View? {
        val convertView: View = LayoutInflater.from(mActivity).inflate(R.layout.native_item_group_image, parent, false)
        val viewHolder: AdGroupImageViewHolder = AdGroupImageViewHolder(convertView)
        bindCommonData(convertView as ViewGroup, viewHolder, ksNativeAd)

        // 获取图片资源
        val ksImageList = ksNativeAd.imageList
        if (ksImageList != null && !ksImageList.isEmpty()) {
            for (i in ksImageList.indices) {
                val image = ksNativeAd.imageList!![i]
                if (image != null && image.isValid) {
                    if (i == 0) {
                        Glide.with(mActivity).load(image.imageUrl).into(viewHolder.mAdImageLeft)
                    } else if (i == 1) {
                        Glide.with(mActivity).load(image.imageUrl).into(viewHolder.mAdImageMid)
                    } else if (i == 2) {
                        Glide.with(mActivity).load(image.imageUrl).into(viewHolder.mAdImageRight)
                    }
                }
            }
        }
        return convertView
    }

    @SuppressLint("DefaultLocale")
    protected fun getNormalItemView(parent: ViewGroup?): View? {
        val convertView: View = LayoutInflater.from(mActivity).inflate(R.layout.native_item_normal, parent, false)
        val normalViewHolder = NormalViewHolder(convertView)
        normalViewHolder.textView.setText("没有广告")
        return convertView
    }


    private class NormalViewHolder constructor(convertView: View) {
        var textView: TextView

        init {
            textView = convertView.findViewById(R.id.tv)
        }
    }

    private class AdGroupImageViewHolder constructor(convertView: View) : AdBaseViewHolder(convertView) {
        var mAdImageLeft: ImageView
        var mAdImageMid: ImageView
        var mAdImageRight: ImageView

        init {
            mAdImageLeft = convertView.findViewById(R.id.ad_image_left)
            mAdImageMid = convertView.findViewById(R.id.ad_image_mid)
            mAdImageRight = convertView.findViewById(R.id.ad_image_right)
        }
    }

    private class AdSingleImageViewHolder constructor(convertView: View) : AdBaseViewHolder(convertView) {
        var mAdImage: ImageView = convertView.findViewById(R.id.ad_image)

    }

    class AdVideoViewHolder constructor(convertView: View) : AdBaseViewHolder(convertView) {
        var mAdVideoContainer: FrameLayout

        init {
            mAdVideoContainer = convertView.findViewById(R.id.video_container)
        }
    }

    open class AdBaseViewHolder constructor(val convertView: View) {
        var mAdDes: TextView
        var mAdIcon: ImageView
        var mAdName: TextView
        var mAdDesc: TextView
        var mAdConvertBtn: TextView
        var mDislikeBtn: ImageView
        var mAdLogoIcon: ImageView
        var mAdSourceDesc: TextView

        init {
            mAdDes = convertView.findViewById(R.id.ad_desc)
            mAdIcon = convertView.findViewById(R.id.app_icon)
            mAdName = convertView.findViewById(R.id.app_title)
            mAdDesc = convertView.findViewById(R.id.app_desc)
            mAdConvertBtn = convertView.findViewById(R.id.app_download_btn)
            mDislikeBtn = convertView.findViewById(R.id.ad_dislike)
            mAdLogoIcon = convertView.findViewById(R.id.ksad_logo_icon)
            mAdSourceDesc = convertView.findViewById(R.id.ksad_logo_text)
        }
    }

    private fun bindDownloadListener(adBaseViewHolder: AdBaseViewHolder, ad: KsNativeAd) {
        val ksAppDownloadListener: KsAppDownloadListener = object : KsAppDownloadListener {
            override fun onIdle() {
                adBaseViewHolder.mAdConvertBtn.text = ad.actionDescription
            }

            override fun onDownloadStarted() {
                adBaseViewHolder.mAdConvertBtn.text = "开始下载"
            }

            override fun onProgressUpdate(progress: Int) {
                adBaseViewHolder.mAdConvertBtn.text = String.format("%s/100", progress)
            }

            override fun onDownloadFinished() {
                adBaseViewHolder.mAdConvertBtn.text = "立即安装"
            }

            override fun onDownloadFailed() {
                adBaseViewHolder.mAdConvertBtn.text = ad.actionDescription
            }

            override fun onInstalled() {
                adBaseViewHolder.mAdConvertBtn.text = "立即打开"
            }
        }
        // 注册下载监听器
        ad.setDownloadListener(ksAppDownloadListener)
    }
}