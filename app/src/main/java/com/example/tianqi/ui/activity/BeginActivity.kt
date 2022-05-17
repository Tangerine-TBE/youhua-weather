package com.example.tianqi.ui.activity

import android.os.Build
import android.util.Log
import android.view.KeyEvent
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import com.amap.api.location.AMapLocationClient
import com.example.module_ad.AdUtil
import com.example.module_ad.utils.MyStatusBarUtil
import com.example.tianqi.base.BaseApplication
import com.example.tianqi.base.BaseMainActivity
import com.example.tianqi.ui.fragment.AdFragment
import com.example.tianqi.ui.fragment.PermissionFragment
import com.example.tianqi.utils.Contents
import com.example.tianqi.utils.PackageUtil
import com.example.tianqi.utils.SpUtils
import com.tiantian.tianqi.R

@RequiresApi(api = Build.VERSION_CODES.M)
class BeginActivity : BaseMainActivity() {
    //隐私政策 R.string.privacy
    private var mPermissionFragment: PermissionFragment? = null
    private var mAdFragment: AdFragment? = null
    private var mFragmentManager: FragmentManager? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_ad
    }

    override fun intView() {
        BaseApplication.getAppContext().getSharedPreferences("no_back_sp", MODE_PRIVATE).edit().putBoolean("no_back", true).apply()
        mAdFragment = AdFragment()
        mPermissionFragment = PermissionFragment()
        mFragmentManager = supportFragmentManager

        AdUtil.askADConfig(channel = PackageUtil.getAppMetaData(BaseApplication.getAppContext(), Contents.PLATFORM_KEY))

        AMapLocationClient.updatePrivacyShow(this, true, true)
        Log.e("地图", "第一步")
        val one = SpUtils.getInstance().getBoolean(Contents.IS_FIRST, true)
        if (!one) {
            ttttt()
            mFragmentManager!!.beginTransaction().add(R.id.fragment_container, mAdFragment!!).commit()
        } else {
            mFragmentManager!!.beginTransaction().add(R.id.fragment_container, mPermissionFragment!!).commit()
        }
    }

    fun ttttt() {
        Log.e("地图", "第二步")
        AMapLocationClient.updatePrivacyAgree(this, true)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        MyStatusBarUtil.fullScreenWindow(hasFocus, this)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) true else super.onKeyDown(keyCode, event) //不执行父类点击事件
        //继续执行父类其他点击事件
    }
}