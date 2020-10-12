package com.example.tianqi.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.tianqi.model.bean.MjDesBean
import com.example.tianqi.utils.WeatherUtils
import com.tiantian.tianqi.R

/**
 * @name WeatherOne
 * @class name：com.example.tianqi.ui.adapter
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/9/28 11:22
 * @class describe
 */
class TwentyFourAdapter:BaseQuickAdapter<MjDesBean,BaseViewHolder>(R.layout.itme_hour24_container) {
    override fun convert(helper: BaseViewHolder, item: MjDesBean?) {
        item?.let {
            helper?.setText(R.id.mHourTem, it.title+"°")
            helper?.setImageResource(R.id.mTimeWea, it.icon)
            helper?.setText(R.id.mHourTime, if (helper.adapterPosition == 0) "现在" else it.value)
        }

    }
}