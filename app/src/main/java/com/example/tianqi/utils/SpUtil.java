package com.example.tianqi.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.example.module_ad.bean.AdBean;
import com.example.tianqi.base.BaseApplication;
import com.example.tianqi.model.bean.LoginBean;
import com.tiantian.tianqi.R;

import java.util.HashMap;
import java.util.Map;


/**
 * @author: Administrator
 * @date: 2020/7/11 0011
 */
public class SpUtil {

    private static boolean sIsShow;

    public static void saveUserInfo(Context context, LoginBean loginBean) {
        SharedPreferences sharedPreferences = BaseApplication.getAppContext().getSharedPreferences(Contents.USER_INFO, context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(Contents.USER_IS_LOGIN, true);
        edit.putInt(Contents.USER_ID, loginBean.getData().getId());
        edit.apply();
    }

    public static AdBean.DataBean getAdState() {
        SharedPreferences ad_info = BaseApplication.getAppContext().getSharedPreferences(com.example.module_ad.utils.Contents.AD_INFO_SP, Context.MODE_PRIVATE);
        String ad = ad_info.getString(com.example.module_ad.utils.Contents.AD_INFO, "");

        if (!TextUtils.isEmpty(ad)) {
            AdBean.DataBean dataBean = JSON.parseObject(ad, AdBean.DataBean.class);
            return dataBean;
        }
        return  null;
    }



    public static  Map<String, String>  getADKey() {
        Map<String, String> map = new HashMap<>();
        SharedPreferences ad_info = BaseApplication.getAppContext().getSharedPreferences(com.example.module_ad.utils.Contents.AD_INFO_SP, Context.MODE_PRIVATE);
        String ad = ad_info.getString(com.example.module_ad.utils.Contents.AD_INFO, "");
        if (!TextUtils.isEmpty(ad)) {
            AdBean.DataBean dataBean = JSON.parseObject(ad, AdBean.DataBean.class);
            //广告信息
            AdBean.DataBean.AdvertisementBean advertisement = dataBean.getAdvertisement();
            //穿山甲广告
//            String kTouTiaoAppKey = advertisement.getKTouTiaoAppKey();
//            String kTouTiaoKaiPing = advertisement.getKTouTiaoKaiPing();
//            String kTouTiaoBannerKey = advertisement.getKTouTiaoBannerKey();
//            String kTouTiaoChaPingKey = advertisement.getKTouTiaoChaPingKey();
//            String kTouTiaoJiLiKey = advertisement.getKTouTiaoJiLiKey();
//            String kTouTiaoSeniorKey = advertisement.getKTouTiaoSeniorKey();
//            map.put(Contents.KT_OUTIAO_APPKEY, kTouTiaoAppKey);
//            map.put(Contents.KT_OUTIAO_KAIPING, kTouTiaoKaiPing);
//            map.put(Contents.KT_OUTIAO_BANNERKEY, kTouTiaoBannerKey);
//            map.put(Contents.KT_OUTIAO_CHAPINGKEY, kTouTiaoChaPingKey);
//            map.put(Contents.KT_OUTIAO_JILIKEY, kTouTiaoJiLiKey);
//            map.put(Contents.KT_OUTIAO_SENIORKEY, kTouTiaoSeniorKey);

            map.put(Contents.KT_OUTIAO_APPKEY, advertisement.getKWaiAppKey());
            map.put(Contents.KT_OUTIAO_KAIPING, advertisement.getKWaiKaiPing());
            map.put(Contents.KT_OUTIAO_BANNERKEY, advertisement.getKWaiBannerKey());
            map.put(Contents.KT_OUTIAO_CHAPINGKEY, advertisement.getKWaiChaPingKey());
            map.put(Contents.KT_OUTIAO_JILIKEY, advertisement.getKWaiJiLiKey());
            map.put(Contents.KT_OUTIAO_SENIORKEY, advertisement.getKWaiSeniorKey());

            //广点通广告
            String kgdtMobSDKAppKey = advertisement.getKGDTMobSDKAppKey();
            String kgdtMobSDKChaPingKey = advertisement.getKGDTMobSDKChaPingKey();
            String kgdtMobSDKKaiPingKey = advertisement.getKGDTMobSDKKaiPingKey();
            String kgdtMobSDKBannerKey = advertisement.getKGDTMobSDKBannerKey();
            String kgdtMobSDKNativeKey = advertisement.getKGDTMobSDKNativeKey();
            String kgdtMobSDKJiLiKey = advertisement.getKGDTMobSDKJiLiKey();

            map.put(Contents.KGDT_MOBSDK_APPKEY, kgdtMobSDKAppKey);
            map.put(Contents.KGDT_MOBSDK_CHAPINGKEY, kgdtMobSDKChaPingKey);
            map.put(Contents.KGDT_MOBSDK_KAIPINGKEY, kgdtMobSDKKaiPingKey);
            map.put(Contents.KGDT_MOBSDK_BANNERKEY, kgdtMobSDKBannerKey);
            map.put(Contents.KGDT_MOBSDK_NATIVEKEY, kgdtMobSDKNativeKey);
            map.put(Contents.KGDT_MOBSDK_JILIKEY, kgdtMobSDKJiLiKey);

            return map;
        }
        return null;
    }






    public static void changePwdShow(EditText mPassWord_edit, ImageView mShowPassWord_image, boolean isShow) {
        if (isShow) {
            mShowPassWord_image.setImageResource(R.mipmap.pwd_show_select);
            mPassWord_edit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            mShowPassWord_image.setImageResource(R.mipmap.pwd_show_normal);
            mPassWord_edit.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    public static void changePwdShow2(EditText mPassWord_edit, ImageView mShowPassWord_image, boolean isShow) {
        if (isShow) {
            mShowPassWord_image.setImageResource(R.mipmap.login_pwd_show_select);
            mPassWord_edit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            mShowPassWord_image.setImageResource(R.mipmap.login_pwd_show_normal);
            mPassWord_edit.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}
