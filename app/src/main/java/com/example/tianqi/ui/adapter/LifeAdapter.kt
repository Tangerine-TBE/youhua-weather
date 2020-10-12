package com.example.tianqi.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tianqi.model.bean.MjDesBean
import com.tiantian.tianqi.R
import kotlinx.android.synthetic.main.item_life_container.view.*

/**
 * @name WeatherOne
 * @class name：com.example.tianqi.ui.adapter
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/9/27 10:41
 * @class describe
 */
class LifeAdapter: RecyclerView.Adapter<LifeAdapter.MyHolder>() {

    private var  mList:MutableList<MjDesBean>?=ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_life_container, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {

       return mList?.size?:0
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.setItemData(mList?.get(position),position)

    }

    fun setData(data: List<MjDesBean>) {
            mList!!.clear()
            mList!!.addAll(data)
            notifyDataSetChanged()
    }


    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setItemData(mjDesBean: MjDesBean?, position: Int) {
            mjDesBean?.let {
                itemView.mIcon.setImageResource(it.icon)
                itemView.mHint.text=it.title
                itemView.mValue.text=it.value
            }
        }

    }
}