package com.example.tianqi.ui.activity;

import android.widget.TextView;

import com.example.tianqi.base.BaseMainActivity;
import com.example.tianqi.ui.custom.DiyToolbar;
import com.example.tianqi.utils.PackageUtil;
import com.tiantian.tianqi.R;

import butterknife.BindView;

public class PrivacyActivity extends BaseMainActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_privacy;
    }


    @BindView(R.id.privacy_toolbar)
    DiyToolbar mDiyToolbar;

    @BindView(R.id.text)
    TextView mWordAlignTextView;


    @Override
    protected void intView() {
        mDiyToolbar.setTitle(getString(R.string.user_Privacy));
        mWordAlignTextView.setText(PackageUtil.difPlatformName(this,R.string.privacy));
    }

    @Override
    protected void intEvent() {
        mDiyToolbar.finishActivity(this);
    }
}