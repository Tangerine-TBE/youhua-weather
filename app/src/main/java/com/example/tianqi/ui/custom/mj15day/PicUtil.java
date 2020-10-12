package com.example.tianqi.ui.custom.mj15day;


import com.tiantian.tianqi.R;

/**
 * Created by zz on 2016/12/28.
 */

public class PicUtil {

    public static int getDayWeatherPic(String weatherName) {
        switch (weatherName) {
            case "晴":
                return R.mipmap.qing;
            case "多云":
                return R.mipmap.duoyun;
            case "阴":
                return R.mipmap.yin;
            case "雷阵雨":
                return R.mipmap.duoyun;
            case "雨夹雪":
                return R.mipmap.dayu;
            case "小雨":
                return R.mipmap.duoyun;
            case "中雨":
                return R.mipmap.duoyun;
            case "大雨":
                return R.mipmap.duoyun;
            case "暴雨":
                return R.mipmap.duoyun;
            case "大雪":
                return R.mipmap.duoyun;
            case "中雪":
                return R.mipmap.duoyun;
            case "冰雹":
                return R.mipmap.duoyun;
        }
        return R.mipmap.duoyun;
    }

    public static int getNightWeatherPic(String weatherName) {
        switch (weatherName) {
            case "晴":
                return R.mipmap.qing;
            case "多云":
                return R.mipmap.duoyun;
            case "阴":
                return R.mipmap.yin;
            case "雷阵雨":
                return R.mipmap.duoyun;
            case "雨夹雪":
                return R.mipmap.dayu;
            case "小雨":
                return R.mipmap.duoyun;
            case "中雨":
                return R.mipmap.duoyun;
            case "大雨":
                return R.mipmap.duoyun;
            case "暴雨":
                return R.mipmap.duoyun;
            case "大雪":
                return R.mipmap.duoyun;
            case "中雪":
                return R.mipmap.duoyun;
            case "冰雹":
                return R.mipmap.duoyun;
        }
        return R.mipmap.duoyun;
    }
}
