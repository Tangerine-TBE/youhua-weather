package com.example.module_tool.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import com.example.module_tool.R;
import com.example.module_tool.widget.ActionBar;
import com.feisukj.base.widget.loaddialog.LoadingDialog;
import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;


/**
 * Author : Gupingping
 * Date : 2019/1/17
 * QQ : 464955343
 * 普通activity
 * 需要actionBar时，返回true，默认false
 */
public abstract class BaseActivity extends RxAppCompatActivity {
    public Activity mContext;

    protected ImmersionBar mImmersionBar;
    //    protected DaoSession daoSession;
    protected LoadingDialog loadingDialog;//正在加载
    protected ActionBar actionBar;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mContext = this;
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        mContext = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setCancelable(cancelDialog());
        if (isActionBar()) {
            setContentView(R.layout.activity_base_new);
            ((ViewGroup) findViewById(R.id.base_activity_container)).addView(getLayoutInflater().inflate(getLayoutId(), null));
            actionBar = findViewById(R.id.actionbar);
            actionBar.setVisibility(View.VISIBLE);

        } else {
//            setContentView(getLayoutId());
            if (Build.VERSION.SDK_INT< Build.VERSION_CODES.M) {
                setContentView(R.layout.activity_base_new);
                try {
                    ((ViewGroup) findViewById(R.id.base_activity_container)).addView(getLayoutInflater().inflate(getLayoutId(), null));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                setContentView(getLayoutId());
            }
        }
        //沉浸式状态栏
       // initImmersionBar(getStatusBarColor());
        initView();
        initData();
        initEvent();
//        daoSession = BaseApplication.getDaoSession();

    }

    protected int getStatusBarColor() {
        return R.color.white;
    }

    /**
     * 沉浸栏颜色
     */
    protected void initImmersionBar(int color) {
        mImmersionBar = ImmersionBar.with(this);
        if (color != 0) {
            mImmersionBar.statusBarColor(color);
        }
        mImmersionBar.init();
    }

    protected abstract int getLayoutId();


    public void showLoading() {
        if (loadingDialog != null) {
            loadingDialog.show();
        }
    }

    public void dismissLoading() {
        if (loadingDialog != null) {
            if (loadingDialog.isShowing())
                loadingDialog.dismiss();
        }
    }

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    protected void initEvent() {

    }

    /**
     * 是否需要ActionBar
     */
    protected boolean isActionBar() {
        return false;
    }

    protected boolean cancelDialog() {
        return false;
    }

    public void visible(View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void invisible(View... views) {
        for (View view : views) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void receive(Event event) {
//
//    }

    protected void setTitleText(String title) {
        if (actionBar != null) {
            actionBar.setCenterText(title);
        }
    }
    protected void setTitleText(int title) {
        if (actionBar != null) {
            actionBar.setCenterText(title);
        }
    }

    protected void setTransparentActionBar() {
        if (actionBar != null) {
            actionBar.setBackgroundColor(getResources().getColor(R.color.transparent));
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        BaseBackstage.setStop(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseBackstage.setBackstage(this);

    }

}