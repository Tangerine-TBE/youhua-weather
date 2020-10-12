package com.example.tianqi.ui.activity;

import android.widget.TextView;

import com.example.tianqi.base.BaseMainActivity;
import com.example.tianqi.ui.custom.DiyToolbar;
import com.example.tianqi.utils.PackageUtil;
import com.tiantian.tianqi.R;

import butterknife.BindView;

public class   AboutActivity extends BaseMainActivity {


    @BindView(R.id.about_toolbar)
    DiyToolbar mDiyToolbar;

    @BindView(R.id.version)
    TextView mTextView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void intView() {
        mDiyToolbar.setTitle("关于我们");
            mTextView.setText(PackageUtil.packageCode(this));
    }

    @Override
    protected void intEvent() {
        mDiyToolbar.finishActivity(this);
    }
}