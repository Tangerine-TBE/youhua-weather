package com.example.tianqi.utils;

import com.example.tianqi.base.BaseApplication;

public class Contents {

    //黄历key
    public static final String HUANG_LI_KEY="APPCODE 52ce58f29858415596449874e5555eec";

    //activity -> fragment
    public static final String FRAGMENT_ID = "fragmentId";

    //位置
    public static final String CURRENT_POSITION = "current_position";

    //fragment -> activity
    public static final String FIVE_DATA = "five_data";
    public static final String DESCRIBE_DATA = "describe_data";

    //包名
    public static final String APP_PACKAGE = PackageUtil.getAppProcessName(BaseApplication.getApplication());




    //App名称
    public static final String AppNAME =PackageUtil.getAppMetaData(BaseApplication.getApplication(),"APP_NAME");

    public static final String PLATFORM_KEY = "CHANNEL";



    //QQ  id
    public static final String QQ_ID = "1110633257";

    //微信
    public static final String WECHAT_APP_ID = "wxd50ba7d3843506b0";
    public static final String WECHAT_SECRET = "4cb785b8a8289e8eb63abfecc423e796";
    public static final String WXAPPID = "appid";
    public static final String WXSECRET = "secret";
    public static final String WXACODE = "code";
    public static final String WXTYPE = "grant_type";

    //广告接口
    public static final String AD_NAME = "name";
    public static final String AD_VERSION = "version";
    public static final String AD_VERSION_VALUES = PackageUtil.packageCode2(BaseApplication.getApplication());
    public static final String AD_CHANNEL = "channel";

    //广告接口缓存
    public static final String AD_INFO_SP = "ad_info";
    public static final String AD_INFO = "ad";

    //穿山甲
    public static final String  CSJ_APPID= "5091408";

   // public static final String CSJ_APPID = "5044191";
    public static final String CSJ_SPLASH = "887345812";
    public static final String CSJ_BANNER = "945308592";
    public static final String CSJ_FEED = "945309048";

    //广告key
    //TT
    public static final String KT_OUTIAO_APPKEY = "kTouTiaoAppKey";
    public static final String KT_OUTIAO_KAIPING = "kTouTiaoKaiPing";
    public static final String KT_OUTIAO_BANNERKEY = "kTouTiaoBannerKey";
    public static final String KT_OUTIAO_CHAPINGKEY = "kTouTiaoChaPingKey";
    public static final String KT_OUTIAO_SENIORKEY = "kTouTiaoSeniorKey";
    public static final String KT_OUTIAO_JILIKEY = "kTouTiaoJiLiKey";

    //TX
    public static final String KGDT_MOBSDK_APPKEY = "kGDTMobSDKAppKey";
    public static final String KGDT_MOBSDK_CHAPINGKEY = "kGDTMobSDKChaPingKey";
    public static final String KGDT_MOBSDK_KAIPINGKEY = "kGDTMobSDKKaiPingKey";
    public static final String KGDT_MOBSDK_BANNERKEY = "kGDTMobSDKBannerKey";
    public static final String KGDT_MOBSDK_NATIVEKEY = "kGDTMobSDKNativeKey";
    public static final String KGDT_MOBSDK_JILIKEY = "kGDTMobSDKJiLiKey";


    //储存用户信息
    public static final String USER_INFO = "user_info";
    public static final String USER_ID = "id";
    public static final String USER_PWD = "pwd";
    public static final String USER_IS_LOGIN = "isLogin";


    //第一次启动
    public static final String FIRST_LOCATION = "first_location";
    public static final String FIRST_LAUNCH = "firstOne";
    public static final String IS_FIRST = "one";


    //存在当前位置信息
    public static final String CURRENT_CITY_SP = "currentCity";
    public static final String CURRENT_CITY = "city";
    public static final String CURRENT_LONG = "longitude";
    public static final String CURRENT_LAT = "latitude";

    //是否开启后台信息
    public static final String NO_BACK_SP = "no_back_sp";
    public static final String NO_BACK = "no_back";



    public static final String SERVICE = "service";//接口名
    public static final String MOBILE = "mobile";//电话
    public static final String PACKAGE = "package";//包名
    public static final String SIGNATURE = "signature";//验证码
    public static final String NONCE = "nonce";//随机数
    public static final String TIMESTAMP = "timestamp";//时间戳
    public static final String TOKEN = "^x389fhfeahykge";//token值


    public static final String QQ_TYPE = "1";//QQ
    public static final String WECHAT_TYPE = "0";//微信
    public static final String OPENID = "openId";//openId
    public static final String TYPE = "type";//openId
    public static final String CODE = "code";//验证码
    public static final String VER = "ver";//软件版本
    public static final String PLATFORM = "platform";//平台
    public static final String PASSWORD = "password";//密码
    public static final String APP_NAME = "appname";//app名
    public static final String PACK_NAME = "packname";//包名
    public static final String CHANNEL_ID = "channelId";//渠道号

    //城市添加限制
    public static final int LIMIT_SIZE = 10;

    //数据库
    public static final String LOCATION_DB = "address.db";
    //城市信息表
    public static final String LOCATION_TABLE = "location";//表名
    public static final int VERSION = 2;//版本号
    public static final String ID = "_id";//id
    public static final String CITY = "city";  //城市
    public static final String LATITUDE = "latitude"; //纬度
    public static final String LONGITUDE = "longitude";  //经度
    public static final String WEA = "wea"; //天气
    public static final String HIGH_TEAM = "high";//高温
    public static final String LOW_TEAM = "low";// 低温

    //缓存数据表
    public static final String WEATHER_CACHE = "weather_cache";//表名
    public static final String WEA_DESCRIBE = "wea_describe";//天气描述
    public static final String TF_TIME = "tf_time";//24小时时间段
    public static final String TF_WINDY = "tf_windy";//24小时风力
    public static final String TF_TEAM = "team";//24小时温度
    public static final String TF_WEA_ICON = "tf_wea_icon";//24天气图标
    public static final String TF_DATA = "tf_team";
    public static final String TF_QUALITY = "tf_weaicon";//24小时天气图标
    public static final String FIVE_WEA = "five_wea";//五天天气
    public static final String LIFE_INDEX = "life_index";//生活指数
    public static final String RAIN_STATE = "rain_state";//降雨状态


    public static final String HUANGLI_DATA = "huangli_data";
    public static final String SUN_COME = "SUN_COME";
    public static final String HL_DATA = "hl_data";
}
