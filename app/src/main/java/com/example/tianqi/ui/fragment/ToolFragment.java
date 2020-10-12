package com.example.tianqi.ui.fragment;


import android.Manifest;
import android.content.Intent;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module_tool.activity.CompassActivity;
import com.example.module_tool.activity.CycleRulerActivity;
import com.example.module_tool.activity.DistanceActivity;
import com.example.module_tool.activity.HandleActivity;
import com.example.module_tool.activity.HorizontalActivity;
import com.example.module_tool.activity.MirrorActivity;
import com.example.module_tool.activity.RulerActivity2;
import com.example.module_tool.activity.SoundActivity;
import com.example.module_tool.flashlight_controller.FlashLightManager;
import com.example.tianqi.base.BaseFragment;
import com.example.tianqi.model.bean.ToolBean;
import com.example.tianqi.ui.adapter.ToolAdapter;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallbackWithBeforeParam;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;
import com.tamsiree.rxkit.RxBarTool;
import com.tamsiree.rxkit.view.RxToast;
import com.tiantian.tianqi.R;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class ToolFragment extends BaseFragment {


    private ToolAdapter mHotAdapter;
    private ToolAdapter mOtherAdapter;



    @BindView(R.id.toolbar4)
    Toolbar mToolbar;

    @BindView(R.id.rv_hot)
    RecyclerView rv_hot;

    @BindView(R.id.rv_Other)
    RecyclerView rv_Other;

    private List<ToolBean> mToolBeanList=new ArrayList<>();


    @Override
    public int getChildLayout() {
        return R.layout.fragment_tool;
    }

    @Override
    protected void intView() {
        setViewState(ViewState.SUCCESS);
        FlashLightManager.getInstance().init(getActivity());
        int height = RxBarTool.getStatusBarHeight(getContext());
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mToolbar.getLayoutParams();
        layoutParams.topMargin=height;
        mToolbar.setLayoutParams(layoutParams);

        mToolBeanList.add(new ToolBean("距离测量","距离高度测量",getResources().getDrawable(R.mipmap.tool_juli)));
        mToolBeanList.add(new ToolBean("尺子 ","量尺长度测量",getResources().getDrawable(R.mipmap.tool_chizi)));
        mToolBeanList.add(new ToolBean("量角器 ","物体角度测量",getResources().getDrawable(R.mipmap.tool_liangjiao)));
        mToolBeanList.add(new ToolBean("水平仪 ","物体水平测量",getResources().getDrawable(R.mipmap.tool_shuiping)));
        mToolBeanList.add(new ToolBean("高清镜子 ","手机上的魔法镜",getResources().getDrawable(R.mipmap.tool_jinzhi)));
        mToolBeanList.add(new ToolBean("分贝仪 ","周围噪音检测",getResources().getDrawable(R.mipmap.tool_fby)));
        mToolBeanList.add(new ToolBean("手电筒 ","实用手机电筒",getResources().getDrawable(R.mipmap.tool_sdt)));
        mToolBeanList.add(new ToolBean("插画校对 ","精确调整画框",getResources().getDrawable(R.mipmap.tool_chjd)));
        mToolBeanList.add(new ToolBean("指南针 ","方向一看便知",getResources().getDrawable(R.mipmap.tool_znz)));


        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        rv_hot.setLayoutManager(manager);
        mHotAdapter = new ToolAdapter(mToolBeanList.subList(0,5));
        rv_hot.setAdapter(mHotAdapter);

        GridLayoutManager manager1 = new GridLayoutManager(getActivity(), 2);
        rv_Other.setLayoutManager(manager1);
        mOtherAdapter = new ToolAdapter(mToolBeanList.subList(5,9));
        rv_Other.setAdapter(mOtherAdapter);

    }


    @Override
    protected void intEvent() {
        mHotAdapter.setOnItemClickListener(new ToolAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        checkRuntimePermission(DistanceActivity.class);
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), RulerActivity2.class));
                        break;
                    case 2:
                        checkRuntimePermission(CycleRulerActivity.class);
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), HorizontalActivity.class));
                        break;
                    case 4:
                        checkRuntimePermission(MirrorActivity.class);
                        break;
                }
            }
        });

        mOtherAdapter.setOnItemClickListener(new ToolAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        checkRuntimePermission(SoundActivity.class);
                        break;
                    case 1:
                        FlashLightManager.getInstance().startFlashLight(!FlashLightManager.getInstance().getFlashLightState());
                        break;
                    case 2:
                        checkRuntimePermission(HandleActivity.class);
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), CompassActivity.class));
                        break;
                }
            }
        });
    }


    private  String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
    };


    private void checkRuntimePermission(Class aClass) {

        PermissionX.init(getActivity())
                .permissions(permissions)
                .onExplainRequestReason(new ExplainReasonCallbackWithBeforeParam() {
                    @Override
                    public void onExplainReason(ExplainScope scope, List<String> deniedList, boolean beforeRequest) {
                        RxToast.normal(getActivity(),"该功能必须开启此权限").show();
                        //   scope.showRequestReasonDialog(deniedList, "即将申请的权限是程序必须依赖的权限", "我已明白");
                    }
                })
                .onForwardToSettings(new ForwardToSettingsCallback() {
                    @Override
                    public void onForwardToSettings(ForwardScope scope, List<String> deniedList) {
                        RxToast.normal(getActivity(),"您需要去应用程序设置当中手动开启权限").show();
                        //  scope.showForwardToSettingsDialog(deniedList, "您需要去应用程序设置当中手动开启权限", "我已明白");
                    }
                })
                .request(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        if (allGranted) {
                            startActivity(new Intent(getActivity(), aClass));
                        }
                    }
                });

    }

}