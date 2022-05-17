package com.example.module_ad

import android.content.Context
import com.alibaba.fastjson.JSON
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.module_ad.utils.Contents
import com.example.module_base.MainBaseApplication

object AdUtil {
    val context= MainBaseApplication.application
    //http://114.215.47.46:8080/ytkapplicaton/youhuaWeatherKwai?name=com.youhua.zhongyangtianqi&version=1.1&channel=_xiaomi
    //youhuaWeather
    fun askADConfig(version: String = context.packageManager.getPackageInfo(context.packageName, 0).versionName,
                    channel: String = "未知",
                    name: String = context.packageName){
        val url="http://114.215.47.46:8080/ytkapplicaton/youhuaWeatherKwai?name=${name}&version=${version}&channel=${channel}"
        eLog(url, "广告url")
        val request= JsonObjectRequest(Request.Method.GET, url, null, { jsonObject ->
            val data = jsonObject.getJSONObject("data")
            iLog(data.toString(), "广告配置")
            try {
                val ad = data.toString()
                context.getSharedPreferences(Contents.AD_INFO_SP, Context.MODE_PRIVATE)
                        .edit()
                        .putString(Contents.AD_INFO, ad).apply()
                iLog(ad)
            } catch (e: Exception) {

            } finally {

            }
        }, {

        })
        Volley.newRequestQueue(context).add(request)
    }
}