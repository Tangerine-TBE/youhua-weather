package com.example.tianqi.ui.fragment;

import android.widget.FrameLayout;

import com.example.module_ad.advertisement.SplashHelper;
import com.example.module_ad.bean.AdBean;
import com.example.module_ad.utils.CommonUtil;
import com.example.module_ad.utils.SpUtil;
import com.example.tianqi.base.BaseFragment;
import com.example.tianqi.ui.activity.MainActivity;
import com.example.tianqi.utils.ImmersionUtil;
import com.tiantian.tianqi.R;

import java.util.Map;

import butterknife.BindView;

public class AdFragment extends BaseFragment {


    @BindView(R.id.ad_container)
    FrameLayout mSplashContainer;

    private SplashHelper mSplashHelper;
    private boolean isShow;
    @Override
    public int getChildLayout() {
        return R.layout.fragment_ad;
    }


    @Override
    protected void intView() {
        setViewState(ViewState.SUCCESS);
        mSplashHelper = new SplashHelper(getActivity(), mSplashContainer, MainActivity.class);
    }

    @Override
    protected void intPresent() {
        //http://114.215.47.46:8080/ytkapplicaton/anHuangliWeather?name=com.tiantian.tianqi&version=1.1&channel=_xiaomi
        if (CommonUtil.isNetworkAvailable(getContext())) {
            AdBean.DataBean data = SpUtil.getAdState();
            Map<String, String> adKey = SpUtil.getADKey();
            if (data != null || adKey != null) {
               mSplashHelper.showAd();
                isShow = true;
            }
        } else {
            ImmersionUtil.startActivity(getContext(), MainActivity.class, true);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void release() {
        if (mSplashHelper != null) {
            mSplashHelper=null;
        }
    }


}
