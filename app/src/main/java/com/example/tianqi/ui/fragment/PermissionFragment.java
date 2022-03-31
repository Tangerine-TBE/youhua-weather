package com.example.tianqi.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSON;
import com.amap.api.maps.MapsInitializer;
import com.example.module_ad.advertisement.TTAdManagerHolder;
import com.example.module_ad.bean.AdBean;
import com.example.module_ad.utils.CommonUtil;
import com.example.module_ad.utils.LogUtils;
import com.example.tianqi.base.BaseApplication;
import com.example.tianqi.base.BaseFragment;
import com.example.tianqi.presenter.Impl.AdPresentImpl;
import com.example.tianqi.presenter.views.IAdCallback;
import com.example.tianqi.ui.activity.AgreementActivity;
import com.example.tianqi.ui.activity.BeginActivity;
import com.example.tianqi.ui.activity.FirstLocationActivity;
import com.example.tianqi.ui.activity.PrivacyActivity;
import com.example.tianqi.utils.ColorUtil;
import com.example.tianqi.utils.Contents;
import com.example.tianqi.utils.ImmersionUtil;
import com.example.tianqi.utils.PackageUtil;
import com.example.tianqi.utils.SpUtils;
import com.permissionx.guolindev.PermissionX;
import com.tamsiree.rxkit.view.RxToast;
import com.tamsiree.rxui.view.dialog.RxDialogSureCancel;
import com.tiantian.tianqi.R;
import com.umeng.commonsdk.UMConfigure;

import butterknife.BindView;


@RequiresApi(api = Build.VERSION_CODES.M)
public class PermissionFragment extends BaseFragment implements IAdCallback {


    @BindView(R.id.go_main)
    Button mGoMainBt;

    @BindView(R.id.user_agreement)
    TextView mAgreementTv;

    @BindView(R.id.permissions_title)
    TextView mTitle;

    @BindView(R.id.bt_try)
    TextView mTry;

    @BindView(R.id.permission_container)
    FrameLayout mAdContainer;



    private RxDialogSureCancel mRxDialogSureCancel;
    private AdPresentImpl mAdPresent;



    @Override
    public int getChildLayout() {
        getActivity().getWindow().getDecorView().setBackgroundColor(ColorUtil.WHITE);
        return  R.layout.activity_permissions;
    }


    @Override
    protected void intView() {
        setViewState(ViewState.SUCCESS);

        mTitle.setText( PackageUtil.difPlatformName(getActivity(),R.string.welcome));

        mRxDialogSureCancel = new RxDialogSureCancel(getContext());

        String str = getResources().getString(R.string.user_agreement);
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(str);
        TextViewSpan1 span1 = new TextViewSpan1();
        stringBuilder.setSpan(span1,10,18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextViewSpan2 span2 = new TextViewSpan2();
        stringBuilder.setSpan(span2,19,25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mAgreementTv.setText(stringBuilder);
        //一定要记得设置这个方法  不是不起作用
        mAgreementTv.setMovementMethod(LinkMovementMethod.getInstance());

    }

    @Override
    protected void intPresent() {
        mAdPresent = AdPresentImpl.getInstance();
        mAdPresent.registerCallback(this);
        if (CommonUtil.isNetworkAvailable(getContext())) {
            if (mAdPresent != null) {
                mAdPresent.toRequestAd();
            }
        }
    }

    @Override
    public void onLoadAdMsgSuccess(AdBean adBean) {
        if (adBean != null) {
            AdBean.DataBean data = adBean.getData();
            String ad = JSON.toJSONString(data);
            BaseApplication.getAppContext().getSharedPreferences(Contents.AD_INFO_SP, Context.MODE_PRIVATE).edit().putString(Contents.AD_INFO, ad).apply();
        }
    }


    @Override
    public void onLoadAdMsgError() {
    }

    private class TextViewSpan1 extends ClickableSpan {
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(ColorUtil.COLOR_SHENG_MING);
            //设置是否有下划线
            //  ds.setUnderlineText(true);
        }
        @Override
        public void onClick(View widget) {
            //点击事件
            LogUtils.i(this,"onClick------11111111111---------->");
            ImmersionUtil.startActivity(getActivity(), AgreementActivity.class,false);
        }
    }
    private class TextViewSpan2 extends ClickableSpan {
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(ColorUtil.COLOR_SHENG_MING);
            //设置是否有下划线
            //  ds.setUnderlineText(true);
        }
        @Override
        public void onClick(View widget) {
            //点击事件
            LogUtils.i(this,"onClick-------22222222--------->");
            ImmersionUtil.startActivity(getActivity(), PrivacyActivity.class,false);
        }
    }



    private  String[] permissions = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };



    @Override
    protected void release() {
        if (mAdPresent!= null) {
            mAdPresent.unregisterCallback(this);
        }
    }

    @Override
    protected void intEvent() {
        mGoMainBt.setOnClickListener(view -> {
            SpUtils.getInstance().putBoolean(Contents.SP_AGREE,true);
            Activity activity=getActivity();
            if (activity instanceof BeginActivity){
                ((BeginActivity) activity).ttttt();
            }
            //穿山甲广告
            TTAdManagerHolder.init(getContext().getApplicationContext());
            UMConfigure.init(getContext(), UMConfigure.DEVICE_TYPE_PHONE,"5f8d051ba88dfc3eb93ab173");
            if (SpUtils.getInstance().getBoolean(Contents.SP_REFUSE_PERMISSION)) {
                goHome();
                requireActivity().finish();
            } else {
                checkRuntimePermission();
            }
        });

        mTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity=getActivity();
                if (activity!=null) {
                    activity.finish();
                }
            }
        });

    }

    private void checkRuntimePermission() {
        PermissionX.init(getActivity())
                .permissions(permissions)
                .setDialogTintColor(Color.parseColor(ColorUtil.COLOR_THEME), Color.parseColor(ColorUtil.COLOR_THEME))
                .onExplainRequestReason((scope, deniedList, beforeRequest) -> {
                    SpUtils.getInstance().putBoolean(Contents.SP_REFUSE_PERMISSION,true);
                 /*  String msg="即将申请的权限是程序必须依赖的权限";
                    scope.showRequestReasonDialog(deniedList,msg,"开启","取消");*/

                })
                .onForwardToSettings((scope, deniedList) -> {

                    String msg="您需要去应用程序设置当中手动开启权限";
                    scope.showForwardToSettingsDialog(deniedList,msg,"开启","取消");
                })
                .request((allGranted, grantedList, deniedList) -> {
                    goHome();
                });

    }

    private void goHome() {
        ImmersionUtil.startActivity(getActivity(), FirstLocationActivity.class,false);
    }
}