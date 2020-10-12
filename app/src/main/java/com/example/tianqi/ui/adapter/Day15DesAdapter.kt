package com.example.tianqi.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.tianqi.model.bean.DescribeBean
import com.tiantian.tianqi.R

/**
 * @name WeatherOne
 * @class name：com.example.tianqi.ui.adapter
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/9/28 17:24
 * @class describe
 */
class Day15DesAdapter:BaseQuickAdapter<DescribeBean.Des,BaseViewHolder>(R.layout.item_day15_container) {
    override fun convert(helper: BaseViewHolder?, item: DescribeBean.Des?) {
        item?.let {

            helper?.setText(R.id.tv_des_title,it.title)
            helper?.setText(R.id.tv_des_value,it.values)

        }



    }
}