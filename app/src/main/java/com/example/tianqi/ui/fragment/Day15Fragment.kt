package com.example.tianqi.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.fastjson.JSON
import com.example.module_ad.advertisement.BanFeedHelper
import com.example.module_ad.utils.MyStatusBarUtil
import com.example.tianqi.model.bean.DayWeatherBean
import com.example.tianqi.model.bean.DescribeBean
import com.example.tianqi.model.bean.HuangLiBean
import com.example.tianqi.presenter.Impl.HuangLiPresentImpl
import com.example.tianqi.presenter.views.IHuangLiCallback
import com.example.tianqi.ui.activity.HuangLiActivity
import com.example.tianqi.ui.adapter.Day15DesAdapter
import com.example.tianqi.ui.adapter.WeatherDescribeAdapter
import com.example.tianqi.utils.ColorUtil
import com.example.tianqi.utils.Contents
import com.example.tianqi.utils.SpUtils
import com.example.tianqi.utils.WeatherUtils
import com.tiantian.tianqi.R
import kotlinx.android.synthetic.main.fragment_day15weather.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * @name WeatherOne
 * @class name：com.example.tianqi.ui.fragment
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/9/22 17:31
 * @class describe
 */
class Day15Fragment:Fragment(), IHuangLiCallback {

    companion object{
        @JvmStatic
        fun getInstance(position:Int):Day15Fragment{
            val day15Fragment = Day15Fragment()
            var bundle = Bundle()
            bundle.putInt(Contents.CURRENT_POSITION,position)
            day15Fragment.arguments=bundle
            return day15Fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_day15weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intView()
        intPresent()
        initLoad()
        intEvent()
    }

    private var position=0
    private var mBanFeedHelper:BanFeedHelper?=null
     fun intView() {

        position = arguments!!.getInt(Contents.CURRENT_POSITION)
          mBanFeedHelper = BanFeedHelper(activity, banner_container, feed_container)
         mBanFeedHelper?.showAd(BanFeedHelper.AdType.TEMPERATURE_PAGE)

     }
    private var mHuangLiPresent:HuangLiPresentImpl?=null
    fun intPresent() {
        mHuangLiPresent  = HuangLiPresentImpl()
        mHuangLiPresent?.registerCallback(this)
    }

    private fun initLoad() {
        val weatherData = SpUtils.getInstance().getString(Contents.FIVE_WEA)
        if (!TextUtils.isEmpty(weatherData)) {
            val dat15Data = JSON.parseObject(weatherData, DayWeatherBean.ResultBean::class.java)
            val daily = dat15Data.daily
            showSunset(daily)
            showWea(daily)
            showDescribe(daily)
            val date = daily.astro[position].date
            val year = date.substring(0, 4)
            val month = date.substring(5, 7)
            val day = date.substring(8, 10)
            mHuangLiPresent?.getHuangLi(day,month,year)
        }
    }

    fun intEvent() {
        tv_detail.setOnClickListener {
            mHuangLiData?.let {
                startActivity(Intent(activity,HuangLiActivity::class.java).apply{
                    putExtra(Contents.HL_DATA,JSON.toJSONString(it))
                })
            }
        }


    }

    private var mDescribeList:MutableList<DescribeBean.Des>?=ArrayList()
    private fun showDescribe(daily: DayWeatherBean.ResultBean.DailyBean?) {
        mDescribeList?.clear()
        mDescribeList?.add(DescribeBean.Des("紫外线",daily!!.life_index.ultraviolet[position].desc))
        mDescribeList?.add(DescribeBean.Des("空气质量",WeatherUtils.aqiType(daily!!.air_quality.aqi[position].avg.chn.toInt())))
        mDescribeList?.add(DescribeBean.Des("体感温度",WeatherUtils.addTemSymbol(daily!!.temperature[position].avg.toInt())))
        mDescribeList?.add(DescribeBean.Des(WeatherUtils.winDirection(daily!!.wind[position].avg.direction),WeatherUtils.winType(daily!!.wind[position].avg.speed,true)))
        mDescribeList?.add(DescribeBean.Des("能见度",daily!!.visibility[position].avg.toString()+"km"))
        mDescribeList?.add(DescribeBean.Des("气压",WeatherUtils.preType(daily!!.pressure[position].avg)))

        rv_airDes.layoutManager=GridLayoutManager(activity,3)
        val weatherDescribeAdapter = Day15DesAdapter()
        weatherDescribeAdapter.setNewData(mDescribeList)
        rv_airDes.adapter=weatherDescribeAdapter

    }

    private fun showWea(daily: DayWeatherBean.ResultBean.DailyBean?) {
        val value = daily!!.skycon[position].value
        tv_airWea.text = WeatherUtils.weatherPhenomena(value)
        tv_airTeam.text = WeatherUtils.addTemSymbol(daily.temperature[0].min.toInt()) + "/" +
                daily.temperature[position].max.toInt()+ "℃"
        mWeaIcon.setImageResource(WeatherUtils.weatherIcon(value))
    }

    private fun showSunset(daily: DayWeatherBean.ResultBean.DailyBean?) {
        val astroBean = daily!!.astro[position]
        var timeSunrise: String = astroBean.sunrise.time
        var timeSunset: String = astroBean.sunset.time
        val riseHour = timeSunrise.substring(0, 2)
        val riseMin = timeSunrise.substring(3, 5)
        val setHour = timeSunset.substring(0, 2)
        val setMin = timeSunset.substring(3, 5)
        mSun?.setSunrise(riseHour.toInt(), riseMin.toInt())
        // 设置日落时间
        mSun?.setSunset(setHour.toInt(),setMin.toInt())
        // 获取系统 时 分
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]
        // 设置当前时间
        mSun?.setCurrentTime(hour, minute)

    }

    private var mHuangLiData:HuangLiBean?=null

    override fun onLoadHuangLi(huangLiBean: HuangLiBean?) {
        mHuangLiData=huangLiBean
        val result = huangLiBean?.result
        tv_nongli.text = result?.nongli?.substring(7,result?.nongli!!.length)

        var suici:MutableList<String> = result?.suici!!
        var stringBuffer = StringBuffer()
        for (s in suici) {
            stringBuffer.append("$s  ")
        }
        tv_suici.text=stringBuffer

        val stringBuffer1 = StringBuffer()
        val yi = result.yi
        for (s in yi) {
            stringBuffer1.append("$s  ")
        }
        tv_yi.text=stringBuffer1

        val stringBuffer2 = StringBuffer()
        val ji = result.ji
        for (s in ji) {
            stringBuffer2.append("$s  ")
        }
        tv_ji.text=stringBuffer2

    }

    override fun onLoadHuangLiError() {

    }

    override fun onLoading() {

    }

    override fun onError() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mHuangLiPresent?.unregisterCallback(this)
        mBanFeedHelper?.releaseAd()
    }


}