package com.example.module_tool.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;

import com.example.module_ad.activity.BackActivity;
import com.example.module_ad.bean.AdBean;
import com.example.module_ad.utils.CommonUtil;
import com.example.module_ad.utils.Contents;
import com.example.module_ad.utils.SpUtil;

import java.util.List;

public class BaseBackstage {

    private static boolean isShow=false;
    private static CountDownTimer mStart;
    private static int mShowTime=1000;


    private static boolean isAppOnForeground(Activity activity) {
        // Returns a list of application processes that are running on the
        // device
        ActivityManager activityManager = (ActivityManager) activity.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = activity.getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName) && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {

                return true;
            }
        }
        return false;
    }


    public static void setStop(Activity activity) {
        AdBean.DataBean adState = SpUtil.getAdState();
        if (adState != null) {
            AdBean.DataBean.StartPageBean.SpreadScreenBean spread_screen = adState.getStart_page().getSpread_screen();
            int times = spread_screen.getTimes();
            mShowTime=times*1000;


            if (!isAppOnForeground(activity)) {
                mStart = new CountDownTimer(mShowTime, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }
                    @Override
                    public void onFinish() {
                        isShow = true;
                    }
                }.start();
            }
        }
    }


    public static void setBackstage(Activity context) {
        SharedPreferences no_back_sp = BaseApplication.getApplication().getSharedPreferences(Contents.NO_BACK_SP, context.MODE_PRIVATE);
        boolean no_back = no_back_sp.getBoolean(Contents.NO_BACK, false);
        if (no_back) {
        }else {
            if (CommonUtil.isNetworkAvailable(context)) {
                AdBean.DataBean adState = SpUtil.getAdState();
                if (adState != null ) {
                    if (adState.getStart_page()!=null) {
                        if (adState.getStart_page().getSpread_screen().isStatus()) {
                            if (isShow) {
                                Intent intent = new Intent(context, BackActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                context.startActivity(intent);
                            }
                            if (mStart != null) {
                                mStart.cancel();
                            }
                            isShow=false;
                        }
                    }
                    // TODO: 2020/7/17

                }
            }
        }
    }
}
