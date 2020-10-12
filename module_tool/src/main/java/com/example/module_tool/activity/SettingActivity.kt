package com.example.module_tool.activity


import com.amap.api.maps.model.MyLocationStyle
import com.example.module_ad.utils.MyStatusBarUtil
import com.example.module_tool.R
import com.example.module_tool.base.BaseActivity
import com.example.module_tool.utils.ColorUtil
import com.example.module_tool.utils.SPUtil
import com.example.module_tool.widget.DiyToolbar
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.activity_setting.db1
import kotlinx.android.synthetic.main.activity_setting.db2
import kotlinx.android.synthetic.main.activity_setting.db3
import kotlinx.android.synthetic.main.activity_setting.dbGroup
import kotlinx.android.synthetic.main.activity_setting.dfm
import kotlinx.android.synthetic.main.activity_setting.gpsGroup
import kotlinx.android.synthetic.main.activity_setting.haibaGroup
import kotlinx.android.synthetic.main.activity_setting.mi
import kotlinx.android.synthetic.main.activity_setting.qy1
import kotlinx.android.synthetic.main.activity_setting.qy2
import kotlinx.android.synthetic.main.activity_setting.qy3
import kotlinx.android.synthetic.main.activity_setting.qy4
import kotlinx.android.synthetic.main.activity_setting.qyGroup
import kotlinx.android.synthetic.main.activity_setting.xs
import kotlinx.android.synthetic.main.activity_setting.yc
import kotlinx.android.synthetic.main.activity_setting_map.*


class SettingActivity : BaseActivity() {
    companion object{
        const val HAI_BA_UNIT_KEY="hai_ba_unit_k"
        const val HAI_BA_VALUE_M="value_m"
        const val HAI_BA_VALUE_FT="value_inch"

        const val GPS_UNIT_KEY="gps_unit_k"
        const val GPS_VALUE_XS="gps_value_1"
        const val GPS_VALUE_JD="gps_value_2"

        const val QI_YA_UNIT_KEY="qi_ya_unit_k"
        const val QI_YA_VALUE_KPA="qi_ya_1"
        const val QI_YA_VALUE_MBAR="qi_ya_2"
        const val QI_YA_VALUE_ATM="qi_ya_3"
        const val QI_YA_VALUE_MMHG="qi_ya_4"

        const val DI_BU_SHOW_KEY="di_bu_show_k"
        const val DI_BU_VALUE_1="di_bu_1"
        const val DI_BU_VALUE_2="di_bu_2"
        const val DI_BU_VALUE_3="di_bu_3"

    }

    override fun isActionBar()=true

    override fun getLayoutId()= R.layout.activity_setting_map

    override fun initView() {
        MyStatusBarUtil.setColor(this,ColorUtil.WHITE)
        toolbar_haiba.setTitle("设置")
        toolbar_haiba.setColorBackground(ColorUtil.WHITE)
        toolbar_haiba.isShowRightIcon(true)
        toolbar_haiba.setTitleColor(ColorUtil.BLACK)
        toolbar_haiba.finishActivity(this)

        when(SPUtil.getInstance().getString(HAI_BA_UNIT_KEY,HAI_BA_VALUE_M)){
            HAI_BA_VALUE_M->{
                mi.isChecked=true
            }
            HAI_BA_VALUE_FT->{
                yc.isChecked=true
            }
            else->{}
        }
        when(SPUtil.getInstance().getString(GPS_UNIT_KEY,GPS_VALUE_XS)){
            GPS_VALUE_XS->{
                xs.isChecked=true
            }
            GPS_VALUE_JD->{
                dfm.isChecked=true
            }
            else->{}
        }
        when(SPUtil.getInstance().getString(QI_YA_UNIT_KEY, QI_YA_VALUE_KPA)){
            QI_YA_VALUE_KPA->{
                qy1.isChecked=true
            }
            QI_YA_VALUE_MBAR->{
                qy2.isChecked=true
            }
            QI_YA_VALUE_ATM->{
                qy3.isChecked=true
            }
            QI_YA_VALUE_MMHG->{
                qy4.isChecked=true
            }
            else->{}
        }
        when(SPUtil.getInstance().getString(DI_BU_SHOW_KEY, DI_BU_VALUE_1)){
            DI_BU_VALUE_1->{
                db1.isChecked=true
            }
            DI_BU_VALUE_2->{
                db2.isChecked=true
            }
            DI_BU_VALUE_3->{
                db3.isChecked=true
            }
            else->{}
        }
        initListener()
    }

    private fun initListener(){
        haibaGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                mi.id->{
                    SPUtil.getInstance().putString(HAI_BA_UNIT_KEY, HAI_BA_VALUE_M)
                }
                yc.id->{
                    SPUtil.getInstance().putString(HAI_BA_UNIT_KEY, HAI_BA_VALUE_FT)
                }
            }
        }
        gpsGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                dfm.id->{
                    SPUtil.getInstance().putString(GPS_UNIT_KEY, GPS_VALUE_JD)
                }
                xs.id->{
                    SPUtil.getInstance().putString(GPS_UNIT_KEY, GPS_VALUE_XS)
                }
            }
        }
        qyGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                qy1.id->{
                    SPUtil.getInstance().putString(QI_YA_UNIT_KEY, QI_YA_VALUE_KPA)
                }
                qy2.id->{
                    SPUtil.getInstance().putString(QI_YA_UNIT_KEY, QI_YA_VALUE_MBAR)
                }
                qy3.id->{
                    SPUtil.getInstance().putString(QI_YA_UNIT_KEY, QI_YA_VALUE_ATM)
                }
                qy4.id->{
                    SPUtil.getInstance().putString(QI_YA_UNIT_KEY, QI_YA_VALUE_MMHG)
                }
            }
        }
        dbGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                db1.id->{
                    SPUtil.getInstance().putString(DI_BU_SHOW_KEY, DI_BU_VALUE_1)
                }
                db2.id->{
                    SPUtil.getInstance().putString(DI_BU_SHOW_KEY, DI_BU_VALUE_2)
                }
                db3.id->{
                    SPUtil.getInstance().putString(DI_BU_SHOW_KEY, DI_BU_VALUE_3)
                }
            }
        }
    }


}