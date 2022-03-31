package com.example.tianqi.base;

import android.content.Context;
import android.os.Handler;

import com.example.module_ad.advertisement.TTAdManagerHolder;
import com.example.tianqi.utils.Contents;
import com.example.tianqi.utils.PackageUtil;
import com.example.tianqi.utils.SpUtils;
import com.tamsiree.rxkit.RxTool;
import com.umeng.commonsdk.UMConfigure;

/**
 * @author: Administrator
 * @date: 2020/6/26 0026
 */
public class BaseApplication extends com.example.module_tool.base.BaseApplication {

    public static  Handler  sHandler=null;
    public static  Context  sContext=null;

    private String APP_ID="OVxkIijsKHnVMy5IrvObeA84";
    private String APP_KEY="SVOdAAjAw4fX7rzF9wn74bIH9UI0Y9C6";

    @Override
    public void onCreate() {
        super.onCreate();
        RxTool.init(this);
        SpUtils.init(this);

        //友盟
        UMConfigure.preInit(
                this,
                "5f8d051ba88dfc3eb93ab173",
                PackageUtil.getAppMetaData(this, Contents.PLATFORM_KEY)
        );
        if (SpUtils.getInstance().getBoolean(Contents.SP_AGREE)) {
            //穿山甲广告
            TTAdManagerHolder.init(getApplicationContext());
            UMConfigure.init(getApplicationContext(),UMConfigure.DEVICE_TYPE_PHONE,"5f8d051ba88dfc3eb93ab173");
        }

        sContext=getApplicationContext();
        sHandler=new Handler();
    }


    public static Context getAppContext() {
        return sContext;
    }

    public static Handler getHandler() {
        return sHandler;
    }
}
