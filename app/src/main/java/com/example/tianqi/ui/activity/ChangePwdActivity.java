package com.example.tianqi.ui.activity;

import com.example.tianqi.base.BaseMainActivity;
import com.example.tianqi.model.bean.RegisterBean;
import com.example.tianqi.presenter.Impl.FindPwdPresentImpl;
import com.example.tianqi.ui.custom.DiyToolbar;
import com.example.tianqi.ui.custom.LoginView;
import com.example.tianqi.utils.Contents;
import com.example.tianqi.utils.Md5Util;
import com.example.tianqi.presenter.views.IFindPwdCallback;
import com.tamsiree.rxkit.view.RxToast;
import com.tamsiree.rxui.view.dialog.RxDialogLoading;
import com.tiantian.tianqi.R;

import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;

public class ChangePwdActivity extends BaseMainActivity implements IFindPwdCallback {


    @BindView(R.id.change_toolbar)
    DiyToolbar mDiyToolbar;


    @BindView(R.id.find_pwd_view)
    LoginView mFindPwdView;

    private FindPwdPresentImpl mFindPwdPresent;

    private RxDialogLoading mRxDialogLoading;


    @Override
    public int getLayoutId() {
        return R.layout.activity_change_pwd;
    }

    @Override
    protected void intView() {
        mDiyToolbar.setTitle(getString(R.string.find_word_tx));
        mFindPwdView.setLoginBtText(getString(R.string.find_word_tx));
    }


    @Override
    protected void intPresent() {
        mFindPwdPresent = FindPwdPresentImpl.getInstance();
        mFindPwdPresent.registerCallback(this);
    }

    @Override
    protected void intEvent() {
        mDiyToolbar.finishActivity(this);

        mFindPwdView.setonStateClickListener(new LoginView.onStateClickListener() {
            @Override
            public void getVerificationCodeClick(String number) {
                if (mFindPwdPresent != null) {
                    mFindPwdPresent.getVerificationCode(number);
                }
            }

            @Override
            public void onLoginClick(String phone, String code, String password) {
                String md5Pwd= Md5Util.md5(password);
                Map<String, String> map = new TreeMap<>();
                map.put(Contents.CODE, code);
                map.put(Contents.MOBILE, phone);
                map.put(Contents.PASSWORD, md5Pwd);
                if (mFindPwdPresent != null) {
                    mFindPwdPresent.findPwd(map);
                }
            }
        });

    }


    @Override
    public void onLoadCode() {
        RxToast.success(this, "验证码已发送").show();
    }

    @Override
    public void onFindPwdSuccess(RegisterBean registerBean) {
        mRxDialogLoading.dismiss();
        int ret = registerBean.getRet();
        if (ret == 200) {
            RxToast.success(this, "修改密码成功").show();
            finish();
        } else {
            RxToast.error(this, registerBean.getMsg()).show();
        }



    }

    @Override
    public void onLoading() {
        mRxDialogLoading = new RxDialogLoading(this);
        mRxDialogLoading.setCancelable(false);
        mRxDialogLoading.show();

    }

    @Override
    public void onError() {
        mRxDialogLoading.dismiss();
    }

    @Override
    protected void release() {
        if (mFindPwdPresent != null) {
            mFindPwdPresent.unregisterCallback(this);
        }
    }
}