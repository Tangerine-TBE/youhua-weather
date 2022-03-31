package com.example.tianqi.ui.activity;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps.MapsInitializer;
import com.example.module_ad.utils.MyStatusBarUtil;
import com.example.tianqi.base.BaseApplication;
import com.example.tianqi.base.BaseMainActivity;
import com.example.tianqi.ui.fragment.AdFragment;
import com.example.tianqi.ui.fragment.PermissionFragment;
import com.example.tianqi.utils.Contents;
import com.example.tianqi.utils.SpUtils;
import com.tiantian.tianqi.R;


@RequiresApi(api = Build.VERSION_CODES.M)
public class BeginActivity extends BaseMainActivity {//隐私政策 R.string.privacy

    private PermissionFragment mPermissionFragment;
    private AdFragment mAdFragment;
    private FragmentManager mFragmentManager;


    @Override
    public int getLayoutId() {
        return R.layout.activity_ad;
    }

    @Override
    protected void intView() {
        BaseApplication.getAppContext().getSharedPreferences("no_back_sp", Context.MODE_PRIVATE).edit().putBoolean("no_back",true).apply();
        mAdFragment = new AdFragment();
        mPermissionFragment = new PermissionFragment();
        mFragmentManager = getSupportFragmentManager();
        AMapLocationClient.updatePrivacyShow(this,true,true);
        Log.e("地图","第一步");
        boolean one= SpUtils.getInstance().getBoolean(Contents.IS_FIRST, true);
        if (!one) {
            ttttt();
            mFragmentManager.beginTransaction().add(R.id.fragment_container, mAdFragment).commit();
        } else {
            mFragmentManager.beginTransaction().add(R.id.fragment_container, mPermissionFragment).commit();
        }
    }

    public void ttttt(){
        Log.e("地图","第二步");
        AMapLocationClient.updatePrivacyAgree(this,true);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        MyStatusBarUtil.fullScreenWindow(hasFocus,this);
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK)
            return true;//不执行父类点击事件
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }


}