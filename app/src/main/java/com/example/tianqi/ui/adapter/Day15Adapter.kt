package com.example.tianqi.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.module_ad.utils.LogUtils
import com.example.tianqi.model.bean.DayWeatherBean
import com.example.tianqi.ui.fragment.Day15Fragment
import com.example.tianqi.utils.DateUtil
import com.example.tianqi.utils.WeatherUtils


/**
 * @name WeatherOne
 * @class name：com.example.tianqi.ui.adapter
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/9/22 17:32
 * @class describe
 */

class Day15Adapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

     var mList:MutableList<DayWeatherBean.ResultBean.DailyBean.AstroBean>?=ArrayList()

    override fun getItem(position: Int)=Day15Fragment.getInstance(position)

    override fun getCount(): Int=if (mList?.size!=0) mList!!.size else 0

    override fun getPageTitle(position: Int): CharSequence? {
        val date = mList?.get(position)?.date
        val realData = date?.substring(0, 10)
        var week= DateUtil.getWeek(realData)
        val showDate= DateUtil.StrToData2(realData)
        if (position == 0) {
            week = "今天"
        }

        return week+"\n"+showDate
    }

    fun setData(data: MutableList<DayWeatherBean.ResultBean.DailyBean.AstroBean>) {
        mList?.clear()
        mList?.addAll(data)
        notifyDataSetChanged()
    }

}