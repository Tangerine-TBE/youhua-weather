package com.example.tianqi.ui.activity;

import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.example.module_ad.KS_Ad;
import com.example.module_ad.advertisement.TXSplashAd;
import com.example.module_ad.base.IShowAdCallback;
import com.example.module_ad.bean.AdBean;
import com.example.module_ad.utils.AdProbabilityUtil;
import com.example.module_ad.utils.MyStatusBarUtil;
import com.example.tianqi.base.BaseMainActivity;
import com.example.tianqi.utils.SpUtil;
import com.tiantian.tianqi.R;

import java.util.Map;

import butterknife.BindView;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/**
 * @author: Administrator
 * @date: 2020/7/16 0016
 */
public class BackActivity extends BaseMainActivity {

    @BindView(R.id.ad_container)
    FrameLayout mAdContainer;
    private KS_Ad mTtSplashAd;
    private TXSplashAd mTxSplashAd;

    private boolean mAddToutiaoAdError=false;
    private boolean mAddTengxunAdError=false;

    @Override
    protected void setOrientation() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_back;
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        MyStatusBarUtil.fullScreenWindow(hasFocus,this);
    }

    @Override
    protected void intView() {
        AdBean.DataBean adState = SpUtil.getAdState();
        Map<String, String> adKey = SpUtil.getADKey();
        if (adKey != null&adState!=null) {
            AdBean.DataBean.StartPageBean.SpreadScreenBean spread_screen = adState.getStart_page().getSpread_screen();
            String ad_percent = spread_screen.getAd_percent();
            double probability = AdProbabilityUtil.showAdProbability(ad_percent);
            if (spread_screen.isStatus()) {
                double random = Math.random();
                if (random > probability) {

                    showBackTTAd();

                } else {

                    showTXBackAd();
                }
            }
        }

    }

    private void showTXBackAd() {
        mTxSplashAd = new TXSplashAd(this,mAdContainer,false,MainActivity.class);
        mTxSplashAd.showRealAd();
        mTxSplashAd.setOnShowError(new IShowAdCallback() {
            @Override
            public void onShowError() {
                if (!mAddTengxunAdError) {
                    showBackTTAd();
                }
                mAddTengxunAdError=true;
                showADError();
            }
        });

    }

    private void showBackTTAd() {
        mTtSplashAd = new KS_Ad(this);
        mTtSplashAd.loadSplashAd(mAdContainer, MainActivity.class, false, new Function0<Unit>() {
            @Override
            public Unit invoke() {
                if (!mAddToutiaoAdError) {
                    showTXBackAd();
                }
                mAddToutiaoAdError=true;
                showADError();
                return null;
            }
        });

    }

    private  void showADError() {
        if (mAddTengxunAdError&mAddToutiaoAdError) {
            finish();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK)
            return true;//不执行父类点击事件
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }

}
