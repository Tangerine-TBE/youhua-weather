package com.example.tianqi.ui.activity;


import android.animation.ValueAnimator;
import android.content.Intent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tianqi.base.BaseMainActivity;
import com.example.tianqi.ui.custom.ExitPoPupWindow;
import com.example.tianqi.ui.fragment.HomeFragment;
import com.example.tianqi.ui.fragment.SettingFragment;
import com.example.tianqi.utils.Contents;
import com.example.tianqi.utils.ImmersionUtil;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;
import com.sr.cejuyiczclds.fragment.MapFragment;
import com.tiantian.tianqi.R;

import butterknife.BindView;


public class MainActivity extends BaseMainActivity {


    private HomeFragment mHomeFragment;
    private FragmentManager mFm;
    @BindView(R.id.JPTabBar)
    JPTabBar mJPTabBar;

    @BindView(R.id.main_container)
    FrameLayout mFrameLayout;

    private ValueAnimator mInValueAnimator;
    private ValueAnimator mOutValueAnimator;

    @Titles
    private static final String[] mTitles = {"天气", "海拔","我的"};
    @SeleIcons
    private static final int[] mSeleIcons = {R.mipmap.home_select, R.mipmap.tool_select, R.mipmap.setting_select};
    @NorIcons
    private static final int[] mNormalIcons = {R.mipmap.home_normal, R.mipmap.tool_normal, R.mipmap.setting_normal};
    private ExitPoPupWindow mExitPoPupWindow;
    private SettingFragment mSettingFragment;
    private MapFragment mMapFragment;


    @Override
    protected void setStatusBarColor() {
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void intView() {
        ImmersionUtil.setImmersion(this);
       // LogUtils.i(this,"------------26666666666666666666666666666666---------->"+Contents.AD_VERSION_VALUES);
        mHomeFragment = new HomeFragment();
        mSettingFragment = new SettingFragment();
        mMapFragment = new MapFragment();
        mFm = getSupportFragmentManager();
        switchFragment(mHomeFragment);
        mExitPoPupWindow = new ExitPoPupWindow(this);
        intBgAnimation();

    }


    private void intBgAnimation() {
        mInValueAnimator = ValueAnimator.ofFloat(1.0f, 0.5f);
        mInValueAnimator.setDuration(300);
        mInValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateBgWindowAlpha((Float) animation.getAnimatedValue());
            }
        });

        mOutValueAnimator = ValueAnimator.ofFloat(0.5f, 1.0f);
        mOutValueAnimator.setDuration(300);
        mOutValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateBgWindowAlpha((Float) animation.getAnimatedValue());
            }
        });
    }

    private Fragment mLastFragment = null;

    private void switchFragment(Fragment baseFragment) {
        if (mLastFragment == baseFragment) {
            return;
        }
        FragmentTransaction transaction = mFm.beginTransaction();

        if (!baseFragment.isAdded()) {
            transaction.add(R.id.main_container, baseFragment);
        } else {
            transaction.show(baseFragment);
        }

        if (mLastFragment != null) {
            transaction.hide(mLastFragment);
        }
        mLastFragment = baseFragment;
        transaction.commitAllowingStateLoss();
    }


    @Override
    protected void intEvent() {
        mJPTabBar.setTabListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int index) {
                switch (index) {
                    case 0:
                        switchFragment(mHomeFragment);
                        break;
                    case 1:
                        switchFragment(mMapFragment);
                        break;
                    case 2:
                        switchFragment(mSettingFragment);
                        break;
                }
            }

            @Override
            public boolean onInterruptSelect(int index) {
                return false;
            }
        });


        mExitPoPupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
             //   mOutValueAnimator.start();
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//如果返回键按下
            //此处写退向后台的处理
          //  mInValueAnimator.start();
            mExitPoPupWindow.popupShowAd(this);
            mExitPoPupWindow.showAtLocation(mFrameLayout, Gravity.BOTTOM, 0, 0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //设置窗口渐变
    private void updateBgWindowAlpha(float alpha) {
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha = alpha;
        window.setAttributes(attributes);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        int intExtra = intent.getIntExtra(Contents.FRAGMENT_ID, 2);
        if (mSettingFragment != null&mJPTabBar!=null) {
            if (intExtra==1) {
                mJPTabBar.setSelectTab(2);
            }
        }
    }
}