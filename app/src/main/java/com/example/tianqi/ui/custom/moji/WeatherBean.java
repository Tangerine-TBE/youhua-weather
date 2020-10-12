package com.example.tianqi.ui.custom.moji;

/**
 * Created by ccy on 2017-07-28.
 */

public class WeatherBean {

    public static final String CLEAR_DAY = "晴";
    public static final String CLEAR_NIGHT = "晴";
    public static final String PARTLY_CLOUDY_DAY = "多云";
    public static final String PARTLY_CLOUDY_NIGHT = "多云";
    public static final String LIGHT_HAZE = "轻度雾霾";
    public static final String MODERATE_HAZE = "中度雾霾";
    public static final String HEAVY_HAZE = "重度雾霾";
    public static final String LIGHT_RAIN = "小雨";
    public static final String MODERATE_RAIN = "中雨";
    public static final String HEAVY_RAIN = "大雨";
    public static final String STORM_RAIN = "暴雨";
    public static final String LIGHT_SNOW = "小雪";
    public static final String MODERATE_SNOW = "中雪";
    public static final String HEAVY_SNOW = "大雪";
    public static final String STORM_SNOW = "暴雪";
    public static final String DUST = "浮尘";
    public static final String SAND = "沙尘";
    public static final String WIND = "大风";
    public static final String FOG = "雾";
    public static final String CLOUDY = "阴";


    public static String[] getAllWeathers() {
        String[] str = {CLEAR_DAY, CLEAR_NIGHT, PARTLY_CLOUDY_DAY, PARTLY_CLOUDY_NIGHT,
                LIGHT_HAZE,
                MODERATE_HAZE,
                HEAVY_HAZE,
                LIGHT_RAIN,
                MODERATE_RAIN,
                HEAVY_RAIN,
                STORM_RAIN,
                LIGHT_SNOW,
                MODERATE_SNOW,
                HEAVY_SNOW,
                STORM_SNOW,
                DUST,
                SAND,
                WIND,
                FOG,
                CLOUDY
        };
        return str;
    }





    public String weather;  //天气，取值为上面6种
    public int temperature; //温度值
    public String temperatureStr; //温度的描述值
    public String time; //时间值
    public String windy; //风力

    public String getWindy() {
        return windy;
    }

    public void setWindy(String windy) {
        this.windy = windy;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String aqi; //空气质量



    public WeatherBean() {

    }

    public WeatherBean(String weather, int temperature, String time) {
        this.weather = weather;
        this.temperature = temperature;
        this.time = time;
        this.temperatureStr = temperature + "°";
    }

    public WeatherBean(String weather, int temperature, String time,String aqi ,String windy ) {
        this.weather = weather;
        this.temperature = temperature;
        this.time = time;
        this.aqi=aqi;
        this.windy=windy;
        this.temperatureStr = temperature + "°";
    }

    public WeatherBean(String weather, int temperature, String temperatureStr, String time) {
        this.weather = weather;
        this.temperature = temperature;
        this.temperatureStr = temperatureStr;
        this.time = time;
    }


}
