package com.example.tianqi.ui.activity

import android.text.TextUtils
import com.alibaba.fastjson.JSON
import com.example.module_ad.utils.MyStatusBarUtil
import com.example.tianqi.base.BaseMainActivity
import com.example.tianqi.model.bean.DayWeatherBean
import com.example.tianqi.ui.adapter.Day15Adapter
import com.example.tianqi.utils.ChangeBgUtil
import com.example.tianqi.utils.Contents
import com.tiantian.tianqi.R
import kotlinx.android.synthetic.main.actitivy_day15weather.*

/**
 * @name WeatherOne
 * @class name：com.example.tianqi.ui.activity
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/9/22 17:26
 * @class describe
 */
class Day15Activity :BaseMainActivity() {
    override fun getLayoutId(): Int= R.layout.actitivy_day15weather

    override fun intView() {
        val day15Data = intent.getStringExtra(Contents.FIVE_DATA)
        val city = intent.getStringExtra(Contents.CITY)

        if (!TextUtils.isEmpty(day15Data)) {
            val position = intent.getIntExtra(Contents.CURRENT_POSITION, 0);
            var data = JSON.parseObject(day15Data, DayWeatherBean.ResultBean::class.java)
            var daily = data.daily
            var astro = daily.astro


            
            tl_title.setupWithViewPager(vp_container,true)
            var day15Adapter = Day15Adapter(supportFragmentManager)
            vp_container.adapter=day15Adapter
            day15Adapter.setData(astro)


            vp_container.setCurrentItem(position,true)

            day_toolbar.setTitle(city)
        }

    }

    override fun intEvent() {
        day_toolbar.finishActivity(this)
    }
}