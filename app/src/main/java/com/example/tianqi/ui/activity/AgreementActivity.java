package com.example.tianqi.ui.activity;

import android.widget.TextView;

import com.example.tianqi.base.BaseMainActivity;
import com.example.tianqi.ui.custom.DiyToolbar;
import com.example.tianqi.utils.PackageUtil;
import com.tiantian.tianqi.R;

import butterknife.BindView;

public class AgreementActivity extends BaseMainActivity {

    @BindView(R.id.agreement_toolbar)
    DiyToolbar mDiyToolbar;

    @BindView(R.id.user_text)
    TextView mTextView;


    @Override
    protected void intView() {
        mDiyToolbar.setTitle(getString(R.string.user_Agreement));
        String platformName = PackageUtil.difPlatformName(this,R.string.user);
        mTextView.setText(platformName);
    }

    @Override
    protected void intEvent() {
        mDiyToolbar.finishActivity(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_agreement;
    }
}