package com.example.tianqi.utils;

public class WeatherUrl {

    public static final String WEATHER_URL = "https://api.caiyunapp.com";
    public static final String WEATHER_REAL_TIME = "/v2.5/1RSIsl63PoUkwn0f/%s,%s/realtime.json";//实时天气接口
    public static final String WEATHER_PREDICTION_DAILY = "/v2.5/1RSIsl63PoUkwn0f/%s,%s/daily.json?dailysteps=15";//天级别的预报接口
    public static final String WEATHER_PREDICTION_HOURLY = "/v2.5/1RSIsl63PoUkwn0f/%s,%s/hourly.json";//小时级别的预报接口
    public static final String WEATHER_PREDICTION_RAIN = "/v2.5/1RSIsl63PoUkwn0f/%s,%s/minutely.json";//分钟级降雨预报接口
    public static final String WEATHER_WARNING  = "/v2.5/1RSIsl63PoUkwn0f/%s,%s/weather.json?alert=true";//预警



    //墨迹天气
    public static final String MJ_WEATHER_URL = "http://aliv8.data.moji.com/whapi/json/aliweather/";

    //黄历
    public static final String HUANG_LI = "https://jisuhlcx.market.alicloudapi.com/";

    //微信
    public static final String WECHAT_URL = "https://api.weixin.qq.com/sns/oauth2/";


    //广告
    public static final String AD_URL = "http://114.215.47.46:8080/ytkapplicaton/";


    //域名
    public static final String USER_URL = "https://www.aisou.club";
    //支付
    public static final String PAY_WX_URL = "https://www.aisou.club/pay/wxh5/dafa.php/";
    public static final String PAY_ALI_URL = "https://www.aisou.club/pay/aliv2/wappay/pay.php/";
    ////////////////////SeverName///////////////////
    //注册验证码
    public static final String GET_CODE = "passport.regcode";
    //找回密码验证码
    public static final String GET_FIND_PWD_CODE = "passport.findPassword";
    //校验验证码
    public static final String CHECK_CODE = "passport.checkcode";
    //注册
    public static final String ADD_USER = "passport.registerByMobile";
    //登录
    public static final String LOGIN = "passport.loginMobile";
    //找回密码
    public static final String FIND_PWD = "passport.setPassByFind";
    //注销账号
    public static final String DELETE_USER  ="passport.unregister";
    //ali_pay
    public static final String ALI_PAY = "";
    //wx_pay
    public static final String WX_PAY = "";
    //验证QQ微信是否注册
    public static final String CHECK_THIRD = "passport.checkThird";
    //第三方注册
    public static final String REGISTER_BY_THIRD = "passport.registerByThird";
    //第三方登录
    public static final String LOGIN_THIRD = "passport.loginThird";




}
