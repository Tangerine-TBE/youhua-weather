package com.example.tianqi.ui.activity;

import android.os.Build;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
//
//    @BindView(R.id.text)
//    TextView mWordAlignTextView;
    @BindView(R.id.webView)
    WebView webView;


    @Override
    protected void intView() {
//        mDiyToolbar.setTitle(getString(R.string.user_Privacy));
//        mWordAlignTextView.setText(PackageUtil.difPlatformName(this,R.string.privacy));
        initWebView();
    }

    @Override
    protected void intEvent() {
        mDiyToolbar.finishActivity(this);
    }

    private void initWebView() {
        webView.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
//        webSettings.useWideViewPort = true
//        webSettings.loadWithOverviewMode = true

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        webSettings.setDomStorageEnabled(true);//不加这句有些h5登陆窗口出不来 H5页面使用DOM storage API导致的页面加载问题
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setAllowFileAccess(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
//            override fun onPageFinished(view: WebView?, url: String?) {
//                super.onPageFinished(view, url)
//                webView.loadUrl("javascript:androidSetAppName('${packageManager.getApplicationLabel(applicationInfo)}')")
//                webView.loadUrl("javascript:androidSetCompanyName('${com}')")
//                webView.loadUrl("javascript:androidSetEmail('${email}')")
//            }

        //加载网络资源
        webView.loadUrl("https://catapi.aisou.club/android/privacy_policy.html?app_name="+getPackageManager().getApplicationLabel(getApplicationInfo())+"&pack_name="+getPackageName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }else{
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }
}