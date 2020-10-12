package com.example.tianqi.ui.activity;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.example.tianqi.base.BaseMainActivity;
import com.tiantian.tianqi.R;


public class UserFeedbackActivity extends BaseMainActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_user_feedback;
    }

    @Override
    protected void intView() {
        FeedbackAPI.openFeedbackActivity();
        finish();
    }

}