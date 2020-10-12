package com.example.tianqi.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tiantian.tianqi.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    FrameLayout mFrameLayout;
    private Unbinder mBind;
    private View mSuccessView;
    private View mLoadingView;
    private View mErrorView;

    @BindView(R.id.home_bg)
    ImageView mErrorVw;

    @OnClick(R.id.error_view)
    public void reload() {
        intReload();
    }

    public enum ViewState {
        NONE,LOADING,SUCCESS,ERROR
    }

    private ViewState mCurrentState=ViewState.NONE;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_base,container,false);
        mFrameLayout= view.findViewById(R.id.base_container);

        loadView();
        mBind = ButterKnife.bind(this, view);
       // ChangeBgUtil.selectBg(mErrorVw,R.mipmap.loading_day,R.mipmap.home_night_bg);
        intView();
        intPresent();
        intLoad();
        intEvent();
        return view;
    }

    protected void intReload() {

    }

    protected void intLoad() {

    }

    protected void intPresent() {

    }

    protected void intEvent() {

    }

    protected void intView() {

    }

    protected void loadView() {
        mSuccessView = successView();
        mFrameLayout.addView(mSuccessView);

        mLoadingView = loadingView();
        mFrameLayout.addView(mLoadingView);

        mErrorView = errorView();
        mFrameLayout.addView(mErrorView);

        setViewState(ViewState.NONE);
    }

    public void setViewState(ViewState currentState) {
        mCurrentState=currentState;
        mLoadingView.setVisibility(mCurrentState==ViewState.LOADING?View.VISIBLE:View.GONE);
        mSuccessView.setVisibility(mCurrentState==ViewState.SUCCESS?View.VISIBLE:View.GONE);
        mErrorView.setVisibility(mCurrentState==ViewState.ERROR?View.VISIBLE:View.GONE);
    }

    private View  loadingView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_loading,mFrameLayout,false);
    }

    private View  errorView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_error,mFrameLayout,false);
    }

    private  View successView() {
        int id=getChildLayout();
        return LayoutInflater.from(getContext()).inflate(id,mFrameLayout,false);
    }

     public abstract int getChildLayout();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBind != null) {
            mBind=null;
        }

        release();
    }

    protected void release() {

    }
}
