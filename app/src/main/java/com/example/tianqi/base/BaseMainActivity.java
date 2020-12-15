package com.example.tianqi.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;

import androidx.fragment.app.FragmentActivity;

import com.example.module_ad.bean.AdBean;
import com.example.module_ad.utils.CommonUtil;
import com.example.module_ad.utils.LogUtils;
import com.example.module_ad.utils.MyActivityManager;
import com.example.module_ad.utils.MyStatusBarUtil;
import com.example.tianqi.ui.activity.BackActivity;
import com.example.tianqi.utils.Contents;
import com.example.tianqi.utils.SpUtil;
import com.tiantian.tianqi.R;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseMainActivity extends FragmentActivity {

    private Unbinder mUnbinder;
    private boolean isShow=false;
    private CountDownTimer mStart;
    private int mShowTime=1000;

    protected void setOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOrientation();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        int id=getLayoutId();
        if (id!=0) {
            setContentView(id);
        }
        MyActivityManager.addActivity(this);
        setStatusBarColor();
        mUnbinder = ButterKnife.bind(this);
        intView();
        intPresent();
        intEvent();

    }

    protected void intPresent() {

    }

    protected void setStatusBarColor() {
        MyStatusBarUtil.setColor(this,getResources().getColor(R.color.white));
    }


    public abstract int getLayoutId();

    protected void intView() {

    }


    protected void intEvent() {

    }

    @Override
    protected void onStop() {
        super.onStop();
        AdBean.DataBean adState = SpUtil.getAdState();
        if (adState != null) {
            AdBean.DataBean.StartPageBean.SpreadScreenBean spread_screen = adState.getStart_page().getSpread_screen();
            int times = spread_screen.getTimes();
            mShowTime=times*1000;
            LogUtils.i(BaseMainActivity.this,"onStop: --------------------->"+mShowTime);

            if (!isAppOnForeground()) {
                mStart = new CountDownTimer(mShowTime, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        LogUtils.i(BaseMainActivity.this,mStart+"CountDownTimer: --------------------->"+millisUntilFinished / 1000);
                    }
                    @Override
                    public void onFinish() {
                        isShow = true;
                    }
                }.start();
            }
        }

    }

    public boolean isAppOnForeground() {

        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName) && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                LogUtils.i(this,": --------------------->在后台运行");
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
       MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        SharedPreferences no_back_sp = BaseApplication.getAppContext().getSharedPreferences(Contents.NO_BACK_SP, MODE_PRIVATE);
        boolean no_back = no_back_sp.getBoolean(Contents.NO_BACK, false);
        if (no_back) {
        }else {
            if (CommonUtil.isNetworkAvailable(this)) {
                AdBean.DataBean adState = SpUtil.getAdState();
                if (adState != null ) {
                    if (adState.getStart_page()!=null) {
                        if (adState.getStart_page().getSpread_screen().isStatus()) {
                            if (isShow) {
                                LogUtils.i(this,"onResume: --------------------->"+ isShow);
                                Intent intent = new Intent(this, BackActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder=null;
        }
        MyActivityManager.removeActivity(this);
        release();

    }

    protected void release() {

    }
}