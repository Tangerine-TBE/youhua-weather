package com.example.tianqi.utils;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.example.tianqi.base.BaseApplication;
import com.example.tianqi.ui.custom.mj15day.AirLevel;
import com.tiantian.tianqi.R;

import java.util.ArrayList;
import java.util.List;

public class WeatherUtils {

    private static List<String> sWinType = new ArrayList<>();
    /*    风向 	符号 	中心角度 	角度
北 	N 	0 	            348.76-11.25
北东北 	NNE 	22.5 	11.26-33.75
东北 	NE 	45 	        33.76-56.25
东东北 	ENE 	67.5 	56.26-78.75
东 	E 	90             	78.76-101.25
东东南 	ESE 	112.5 	101.26-123.75
东南 	SE 	135 	    123.76-146.25
南东南 	SSE 	157.5 	146.26-168.75
南 	S 	180 	         168.76-191.25
南西南 	 SSW 	202.5 	 191.26-213.75
西南 	SW 	225 	      213.76-236.25
西西南 	WSW 	247.5 	 236.26-258.75
西 	W 	270             	258.76-281.25
西西北 	WNW 	295.5 	 281.26-303.75
西北 	NW 	315 	     303.76-326.25
北西北 	NNW 	337.5 	  326.26-348.75*/
    private static String[] sDirection = {"北风", "东北风", "东北风", "东北风", "东风",
            "东南风", "东南风", "东南风", "南风", "西南风", "西南风",
            "西南风", "西风", "西北风", "西北风", "西北风"};

    //天气状态
    public enum State {
        CLEAR_DAY, CLEAR_NIGHT, PARTLY_CLOUDY_DAY, PARTLY_CLOUDY_NIGHT, CLOUDY, LIGHT_HAZE,
        MODERATE_HAZE, HEAVY_HAZE, LIGHT_RAIN, MODERATE_RAIN, HEAVY_RAIN, STORM_RAIN, FOG,
        LIGHT_SNOW, MODERATE_SNOW, HEAVY_SNOW, STORM_SNOW, DUST, SAND, WIND
    }

    //格式化湿度
    public static String addHumiditySymbol(double tem) {
        String realHum = (int) (tem * 100) + "%";
        return realHum;
    }

    //格式化城市
    public static String cityType(String city) {
        if (city.endsWith("市")) {
            String replaceStr = city.replace("市", "");

            return replaceStr;
        }
        return city;
    }


    //格式化气压
    public static String preType(double p) {
        int hPa = (int) p / 1000;

        return hPa + "kPa";
    }


    //格式化温度
    public static String addTemSymbol(int tem) {
        String realTem = tem + "°";
        return realTem;
    }

    public static final String[] ALARM_LEVEL = {"优", "良", "轻度污染", "中度污染", "重度污染", "严重污染"};

    //格式化空气质量
    public static String aqiType(int aqi) {
        if (aqi >= 0 && aqi <= 50) {
            return ALARM_LEVEL[0];
        }
        if (aqi > 50 && aqi <= 100) {
            return ALARM_LEVEL[1];
        }
        if (aqi > 100 && aqi <= 150) {
            return ALARM_LEVEL[2];
        }
        if (aqi > 150 && aqi <= 200) {
            return ALARM_LEVEL[3];
        }
        if (aqi > 200 && aqi <= 300) {
            return ALARM_LEVEL[4];
        }
        if (aqi > 300) {
            return ALARM_LEVEL[5];
        }

        return ALARM_LEVEL[0];
    }

    //格式化空气质量
    public static AirLevel aqiState(int aqi) {
        if (aqi >= 0 && aqi <= 50) {
            return AirLevel.EXCELLENT;
        }
        if (aqi > 50 && aqi <= 100) {
            return AirLevel.GOOD;
        }
        if (aqi > 100 && aqi <= 150) {
            return AirLevel.LIGHT;
        }
        if (aqi > 150 && aqi <= 200) {
            return AirLevel.MIDDLE;
        }
        if (aqi > 200 && aqi <= 300) {
            return AirLevel.HIGH;
        }
        if (aqi > 300) {
            return AirLevel.POISONOUS;
        }

        return AirLevel.EXCELLENT;
    }


    public static Drawable aqiTypeBg(int[] values, int aqi) {
        Resources resources = BaseApplication.getAppContext().getResources();
        Drawable drawable = null;
        if (aqi >= values[0] && aqi <= values[1]) {
            return drawable = resources.getDrawable(R.drawable.shape_air_a_bg);
        }
        if (aqi > values[1] && aqi <= values[2]) {
            return drawable = resources.getDrawable(R.drawable.shape_air_b_bg);
        }
        if (aqi > values[2] && aqi <= values[3]) {
            return drawable = resources.getDrawable(R.drawable.shape_air_c_bg);
        }
        if (aqi > values[3] && aqi <= values[4]) {
            return drawable = resources.getDrawable(R.drawable.shape_air_d_bg);
        }
        if (aqi > values[4] && aqi <= values[5]) {
            return drawable = resources.getDrawable(R.drawable.shape_air_e_bg);
        }
        if (aqi > values[5]) {
            return drawable = resources.getDrawable(R.drawable.shape_air_f_bg);
        }

        return resources.getDrawable(R.drawable.shape_air_a_bg);
    }

    private static int[] aqiState={0,50,100,150,200,300};

    public static Drawable aqiTypeBg2(int aqi) {
        Resources resources = BaseApplication.getAppContext().getResources();
        Drawable drawable = null;
        if (aqi >= aqiState[0] && aqi <= aqiState[1]) {
            return drawable = resources.getDrawable(R.drawable.best_level_shape);
        }
        if (aqi > aqiState[1] && aqi <= aqiState[2]) {
            return drawable = resources.getDrawable(R.drawable.good_level_shape);
        }
        if (aqi > aqiState[2] && aqi <= aqiState[3]) {
            return drawable = resources.getDrawable(R.drawable.small_level_shape);
        }
        if (aqi > aqiState[3] && aqi <= aqiState[4]) {
            return drawable = resources.getDrawable(R.drawable.mid_level_shape);
        }
        if (aqi > aqiState[4] && aqi <= aqiState[5]) {
            return drawable = resources.getDrawable(R.drawable.big_level_shape);
        }
        if (aqi > aqiState[5]) {
            return drawable = resources.getDrawable(R.drawable.poison_level_shape);
        }

        return resources.getDrawable(R.drawable.best_level_shape);
    }



    //格式化天气类型
    public static String weatherPhenomena(String type) {
        State state = State.valueOf(type);
        if (state == State.CLEAR_DAY) {
            return "晴";
        }
        if (state == State.CLEAR_NIGHT) {
            return "晴";
        }
        if (state == State.PARTLY_CLOUDY_DAY) {
            return "多云";
        }
        if (state == State.PARTLY_CLOUDY_NIGHT) {
            return "多云";
        }
        if (state == State.LIGHT_HAZE) {
            return "轻度雾霾";
        }
        if (state == State.MODERATE_HAZE) {
            return "中度雾霾";
        }
        if (state == State.HEAVY_HAZE) {
            return "重度雾霾";
        }
        if (state == State.LIGHT_RAIN) {
            return "小雨";
        }
        if (state == State.MODERATE_RAIN) {
            return "中雨";
        }
        if (state == State.HEAVY_RAIN) {
            return "大雨";
        }
        if (state == State.STORM_RAIN) {
            return "暴雨";
        }
        if (state == State.LIGHT_SNOW) {
            return "小雪";
        }
        if (state == State.MODERATE_SNOW) {
            return "中雪";
        }
        if (state == State.HEAVY_SNOW) {
            return "大雪";
        }
        if (state == State.STORM_SNOW) {
            return "暴雪";
        }
        if (state == State.DUST) {
            return "浮尘";
        }
        if (state == State.SAND) {
            return "沙尘";
        }
        if (state == State.WIND) {
            return "大风";
        }
        if (state == State.FOG) {
            return "雾";
        }
        if (state == State.CLOUDY) {
            return "阴";
        }
        return "暂无";
    }

    /*    风力等级 	风速范围 （km/h） 	自然语言描述
    0级 	<1 	无风
    1级 	1-5 	微风徐徐
    2级 	6-11 	清风
    3级 	12-19 	树叶摇摆
    4级 	20-28 	树枝摇动
    5级 	29-38 	风力强劲
    6级 	39-49 	风力强劲
    7级 	50-61 	风力超强
    8级 	62-74 	狂风大作
    9级 	75-88 	狂风呼啸
    10级 	89-102 	暴风毁树
    11级 	103-117 	暴风毁树
    12级 	118-133 	飓风
    13级 	134-149 	台风
    14级 	150-166 	强台风
    15级 	167-183 	强台风
    16级 	184-201 	超强台风
    17级 	202-220 	超强台风*/

    //格式化风力等级
    public static String winType(double speed, boolean isBight) {
        sWinType.clear();
        if (isBight) {
            for (int i = 0; i < 18; i++) {
                sWinType.add(i + "级");
            }
        } else {
            for (int i = 0; i < 18; i++) {
                sWinType.add(i + "");
            }
        }


        if (speed < 1) {
            return sWinType.get(1);
        } else if (speed >= 1 && speed < 6) {
            return sWinType.get(1);
        } else if (speed >= 6 && speed < 11) {
            return sWinType.get(2);
        } else if (speed >= 11 && speed < 20) {
            return sWinType.get(3);
        } else if (speed >= 20 && speed < 29) {
            return sWinType.get(4);
        } else if (speed >= 29 && speed < 39) {
            return sWinType.get(5);
        } else if (speed >= 39 && speed < 50) {
            return sWinType.get(6);
        } else if (speed >= 50 && speed < 62) {
            return sWinType.get(7);
        } else if (speed >= 62 && speed < 75) {
            return sWinType.get(8);
        } else if (speed >= 75 && speed < 89) {
            return sWinType.get(9);
        } else if (speed >= 89 && speed < 103) {
            return sWinType.get(10);
        } else if (speed >= 103 && speed < 118) {
            return sWinType.get(11);
        } else if (speed >= 118 && speed < 134) {
            return sWinType.get(12);
        } else if (speed >= 134 && speed < 150) {
            return sWinType.get(13);
        } else if (speed >= 150 && speed < 167) {
            return sWinType.get(14);
        } else if (speed >= 167 && speed < 184) {
            return sWinType.get(15);
        } else if (speed >= 184 && speed < 202) {
            return sWinType.get(16);
        } else if (speed >= 202 && speed < 221) {
            return sWinType.get(17);
        }

        return "暂无";
    }




    //格式化风向
    public static String winDirection(double direction) {
        if (direction <= 11.25) {
            return sDirection[0];
        }
        if (direction >= 11.26 && direction <= 33.75) {
            return sDirection[1];
        }
        if (direction >= 33.76 && direction <= 56.25) {
            return sDirection[2];
        }
        if (direction >= 56.26 && direction <= 78.75) {
            return sDirection[3];
        }
        if (direction >= 78.76 && direction <= 101.25) {
            return sDirection[4];
        }
        if (direction >= 101.26 && direction <= 123.75) {
            return sDirection[5];
        }
        if (direction >= 123.76 && direction <= 146.25) {
            return sDirection[6];
        }
        if (direction >= 146.26 && direction <= 168.75) {
            return sDirection[7];
        }
        if (direction >= 168.76 && direction <= 191.25) {
            return sDirection[8];
        }
        if (direction >= 191.26 && direction <= 213.75) {
            return sDirection[9];
        }
        if (direction >= 213.76 && direction <= 236.25) {
            return sDirection[10];
        }
        if (direction >= 236.26 && direction <= 258.75) {
            return sDirection[11];
        }
        if (direction >= 258.76 && direction <= 281.25) {
            return sDirection[12];
        }
        if (direction >= 281.26 && direction <= 303.75) {
            return sDirection[13];
        }
        if (direction >= 303.76 && direction <= 326.25) {
            return sDirection[14];
        }
        if (direction >= 326.26 && direction <= 348.75) {
            return sDirection[15];
        }
        if (direction >= 348.76) {
            return sDirection[0];
        }
        return "暂无";
    }

    //天气图标
    public static int weatherIcon(String type) {
        State state = State.valueOf(type);
        if (state == State.CLEAR_DAY) {
            return R.mipmap.qing;
        }
        if (state == State.CLEAR_NIGHT) {
            return R.mipmap.wanqing;
        }
        if (state == State.PARTLY_CLOUDY_DAY) {
            return R.mipmap.duoyun;
        }
        if (state == State.PARTLY_CLOUDY_NIGHT) {
            return R.mipmap.duoyun;
        }
        if (state == State.LIGHT_HAZE) {
            return R.mipmap.qingduwumai;
        }
        if (state == State.MODERATE_HAZE) {
            return R.mipmap.zhongduwumai;
        }
        if (state == State.HEAVY_HAZE) {
            return R.mipmap.zdwumai;
        }
        if (state == State.LIGHT_RAIN) {
            return R.mipmap.xiaoyu;
        }
        if (state == State.MODERATE_RAIN) {
            return R.mipmap.zhongyu;
        }
        if (state == State.HEAVY_RAIN) {
            return R.mipmap.dayu;
        }
        if (state == State.STORM_RAIN) {
            return R.mipmap.baoyu;
        }
        if (state == State.LIGHT_SNOW) {
            return R.mipmap.xiaoxue;
        }
        if (state == State.MODERATE_SNOW) {
            return R.mipmap.zhongxue;
        }
        if (state == State.HEAVY_SNOW) {
            return R.mipmap.daxue;
        }
        if (state == State.STORM_SNOW) {
            return R.mipmap.baoxue;
        }
        if (state == State.DUST) {
            return R.mipmap.fuchen;
        }
        if (state == State.SAND) {
            return R.mipmap.shachen;
        }
        if (state == State.WIND) {
            return R.mipmap.dafeng;
        }
        if (state == State.FOG) {
            return R.mipmap.wu;
        }
        if (state == State.CLOUDY) {
            return R.mipmap.yin;
        }
        return R.mipmap.wushuju;
    }

}
