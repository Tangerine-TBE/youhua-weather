package com.example.tianqi.ui.activity;

import android.text.TextUtils;

import com.example.module_ad.utils.LogUtils;
import com.example.tianqi.base.BaseMainActivity;
import com.example.tianqi.model.bean.LoginBean;
import com.example.tianqi.model.bean.RegisterBean;
import com.example.tianqi.presenter.Impl.LoginPresentImpl;
import com.example.tianqi.presenter.Impl.RegisterPresentImpl;
import com.example.tianqi.ui.custom.DiyToolbar;
import com.example.tianqi.ui.custom.LoginView;
import com.example.tianqi.utils.ColorUtil;
import com.example.tianqi.utils.Contents;
import com.example.tianqi.utils.Md5Util;
import com.example.tianqi.utils.PackageUtil;
import com.example.tianqi.utils.SpUtil;
import com.example.tianqi.presenter.views.ILoginCallback;
import com.example.tianqi.presenter.views.IRegisterCallback;
import com.tamsiree.rxkit.view.RxToast;
import com.tamsiree.rxui.view.dialog.RxDialogLoading;
import com.tiantian.tianqi.R;

import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;

;

public class RegisterActivity extends BaseMainActivity implements IRegisterCallback, ILoginCallback {


    @BindView(R.id.register_toolbar)
    DiyToolbar mDiyToolbar;

    @BindView(R.id.register_view)
    LoginView mRegisterView;

    private RegisterPresentImpl mRegisterPresent;
    private RxDialogLoading mRxDialogLoading;
    private LoginPresentImpl mLoginPresent;

    private String phoneNumber;
    private String pwdNumber;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void intView() {
        mDiyToolbar.setTitle("账号注册");
        mRxDialogLoading = new RxDialogLoading(this);
    }


    @Override
    protected void intPresent() {
        mRegisterPresent = RegisterPresentImpl.getInstance();
        mRegisterPresent.registerCallback(this);

        mLoginPresent = LoginPresentImpl.getInstance();
        mLoginPresent.registerCallback(this);
    }

    @Override
    protected void intEvent() {
        //关闭页面
        mDiyToolbar.finishActivity(this);


        mRegisterView.setonStateClickListener(new LoginView.onStateClickListener() {

            //获取验证码
            @Override
            public void getVerificationCodeClick(String number) {
                if (!TextUtils.isEmpty(number)) {
                    if (mRegisterPresent != null) {
                        mRegisterPresent.getVerificationCode(number);
                        LogUtils.i(RegisterActivity.this, "---------------------->" + number);
                    }
                } else {
                    RxToast.normal(RegisterActivity.this, "请输入手机号码").show();
                }
            }

            //注册
            @Override
            public void onLoginClick(String phone, String code, String password) {
                phoneNumber = phone;
                pwdNumber = Md5Util.md5(password);
                Map<String, String> map = new TreeMap<>();
                map.put(Contents.MOBILE, phone);
                map.put(Contents.PASSWORD, password);
                map.put(Contents.CODE, code);
                map.put(Contents.PACKAGE, Contents.APP_PACKAGE);
                map.put(Contents.PLATFORM, PackageUtil.getAppMetaData(RegisterActivity.this, Contents.PLATFORM_KEY));
                if (mRegisterPresent != null) {
                    mRegisterPresent.registerNumber(map);
                }
            }
        });

    }

    //获取验证码成功
    @Override
    public void onLoadCode() {
        RxToast.normal(this, "验证码已发送").show();
    }


    @Override
    public void onRegisterSuccess(RegisterBean registerBean) {
        int ret = registerBean.getRet();
            if (ret == 200) {
                Map<String, String> loginMap = new TreeMap<>();
                loginMap.put(Contents.MOBILE, phoneNumber);
                loginMap.put(Contents.PASSWORD, pwdNumber);
                if (mLoginPresent != null) {
                    mLoginPresent.toLogin(loginMap);
                }
                RxToast.success(this, "注册成功").show();
            } else {
                mRxDialogLoading.dismiss();
                RxToast.error(this, registerBean.getMsg()).show();
            }

    }

    @Override
    public void onLoading() {
        mRxDialogLoading.setLoadingColor(ColorUtil.COLOR_TOOLBAR);
        mRxDialogLoading.setCancelable(false);
        mRxDialogLoading.show();

    }

    @Override
    protected void release() {
        if (mRegisterPresent != null && mLoginPresent != null) {
            mRegisterPresent.unregisterCallback(this);
            mLoginPresent.unregisterCallback(this);
        }


    }

    @Override
    public void onError() {
        mRxDialogLoading.dismiss();
        RxToast.error(this,"登陆失败").show();
    }

    @Override
    public void onLoginSuccess(LoginBean loginBean) {
        SpUtil.saveUserInfo(this,loginBean);
        mRxDialogLoading.dismiss();
        finish();
    }

    @Override
    public void onLoginError() {
        mRxDialogLoading.dismiss();
    }

}