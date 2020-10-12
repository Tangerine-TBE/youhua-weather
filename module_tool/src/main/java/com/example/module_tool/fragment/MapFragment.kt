package com.sr.cejuyiczclds.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.mapcore.util.ge
import com.amap.api.mapcore.util.it
import com.amap.api.maps.AMap
import com.amap.api.maps.AMapUtils
import com.amap.api.maps.UiSettings
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MyLocationStyle
import com.example.module_tool.R
import com.example.module_tool.activity.SettingActivity
import com.example.module_tool.base.BaseConstant
import com.example.module_tool.base.BaseFragment
import com.example.module_tool.utils.MyLocation
import com.example.module_tool.utils.SPUtil
import com.example.module_tool.utils.toast
import kotlinx.android.synthetic.main.fragment_map.*
import java.lang.Exception
import java.lang.StringBuilder
import java.text.NumberFormat
import kotlin.math.abs


class MapFragment: BaseFragment(), MyLocation.LocationCallback {

    companion object{
        const val TO_SETTING_REQUEST_CODE=1254
    }
    private var aMap:AMap?=null
    private var uiSettings:UiSettings?=null
    private var mLocationClient:AMapLocationClient?=null
    private val locationManager = BaseConstant.application.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private lateinit var sensorManager: SensorManager
    private var pressure: Sensor? = null//压力传感器
    private var orientation:Sensor?=null

    private var haibaUnit=SettingActivity.HAI_BA_VALUE_M
    private var gpsUnit=SettingActivity.GPS_VALUE_XS
    private var qiyaUnit=SettingActivity.QI_YA_VALUE_KPA
    private var latitude:Double=0.0
    private var longitude:Double=0.0

    private var astro=false
    private var isGpsEnabled=false

    private val orientationAngle= listOf(Pair(0f , "北"),Pair(360f , "北"),Pair(45f , "东北"),Pair(90f , "东"),Pair(135f , "东南"),Pair(180f , "南"),Pair(225f , "西南"),Pair(270f , "西"),Pair(315f , "西北"))
    //传感器事件监听
    private val sensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }
        @SuppressLint("SetTextI18n")
        override fun onSensorChanged(event: SensorEvent?) {
            if (event == null) return
            when(event.sensor.type){
                Sensor.TYPE_PRESSURE->{
                    val sPV = event.values[0]//气压值
                    val numberFormat=NumberFormat.getNumberInstance()
                    when(qiyaUnit){
                        SettingActivity.QI_YA_VALUE_KPA ->{
                            numberFormat.minimumFractionDigits=2
                            numberFormat.maximumFractionDigits=2
                            airPressure.text=numberFormat.format(sPV/10)+" kpa"
                        }
                        SettingActivity.QI_YA_VALUE_MBAR ->{
                            numberFormat.minimumFractionDigits=1
                            numberFormat.maximumFractionDigits=1
                            airPressure.text=numberFormat.format(sPV)+" mbar"
                        }
                        SettingActivity.QI_YA_VALUE_ATM ->{
                            numberFormat.minimumFractionDigits=5
                            numberFormat.maximumFractionDigits=5
                            airPressure.text=numberFormat.format(sPV*0.0009869f)+" atm"
                        }
                        SettingActivity.QI_YA_VALUE_MMHG ->{
                            numberFormat.minimumFractionDigits=2
                            numberFormat.maximumFractionDigits=2
                            airPressure.text=numberFormat.format(sPV*0.7500617f)+" mmHg"
                        }
                        else->{}
                    }
                }
                Sensor.TYPE_ORIENTATION->{
                    val value=event.values[0]
                    val absValue=orientationAngle.map { it.first }.map { abs(it-value) }
                    val min=absValue.min()?:return
                    val index=absValue.indexOf(min)
                    bearing.text=orientationAngle[index].second
                }
            }
        }
    }

    override fun getLayoutId()= R.layout.fragment_map

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)
    }

    override fun initView() {
        super.initView()
        aMap=mapView.map.also {
            val maxZoom=it.maxZoomLevel
            Log.i("maxZoom-------",maxZoom.toString())
            it.maxZoomLevel=maxZoom
            it.minZoomLevel=17f
        }
        uiSettings=aMap?.uiSettings
        uiSettings?.apply {
            this.isZoomControlsEnabled = false
            this.isCompassEnabled=true
            this.isMyLocationButtonEnabled=true
        }
        initLocationMap()
        locationInfo()

        sensorManager = context!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        orientation=sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        if (pressure == null) {
            airPressure.setText(R.string.Unsupported)
        }
        refreshShow()

        astro=true

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            val dialog = AlertDialog.Builder(context)
                    .setMessage("请打开GPS以获取海拔高度")
                    .setPositiveButton("是") { _, _ ->
                        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    }
                    .setNegativeButton("否") { _, _ ->

                    }
                    .show()
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE)
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
        }

        barSetting.setOnClickListener {
            startActivityForResult(Intent(activity,SettingActivity::class.java),TO_SETTING_REQUEST_CODE)
        }

    }

    private var previousLatLng:LatLng?=null
    private var previousTime:Long=0L
    private var saveLocationType=-1
    private fun locationInfo(){
        mLocationClient = AMapLocationClient(context!!)
        mLocationClient?.setLocationListener { aMapLocation ->
            if (aMapLocation!=null&&aMapLocation.errorCode==0){
                Log.i("定位成功",aMapLocation.address)
                Log.i("定位精度",aMapLocation.accuracy.toString())
                Log.i("定位来源",aMapLocation.locationType.toString())
                Log.i("经纬度","aMapLocation.latitude:${aMapLocation.latitude},aMapLocation.longitude:${aMapLocation.longitude}")
                Log.i("海拔高度",aMapLocation.altitude.toString()+"m")
                Log.i("移动速度",aMapLocation.speed.toString()+"m/s")
                if (saveLocationType!=aMapLocation.locationType){
                    if(pressure==null){
                        isGpsEnabled=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                        if (aMapLocation.locationType!=1){
                            MyLocation.instance.addCallback(this)
                            MyLocation.instance.startLocationProvidersChanged()
                        }else{
                            MyLocation.instance.removeCallback(this)
                            MyLocation.instance.removeLocationProvidersChanged()
                        }
                    }


                    saveLocationType=aMapLocation.locationType
                    toast("定位精度："+aMapLocation.accuracy.toString()+"米"+"   定位类型：${aMapLocation.locationType}")
                    if(aMapLocation.locationType==1||pressure!=null){
                        outdoorsTips.visibility=View.GONE
                    }else{
                        outdoorsTips.visibility=View.VISIBLE
                    }
                }
                //计算速度
                if (aMapLocation.hasSpeed()){
                    speed.text=NumberFormat.getInstance().let {
                        it.minimumFractionDigits=1
                        it.maximumFractionDigits=1
                        it.format(aMapLocation.speed)+"m/s"
                    }
                }else{
                    val currentLatLng=LatLng(aMapLocation.latitude,aMapLocation.longitude)
                    if (previousLatLng==null){
                        previousLatLng=currentLatLng
                        previousTime=System.currentTimeMillis()
                    }
                    previousLatLng.let { latLng ->
                        if(latLng!=null){
                            if (!(latLng.longitude==currentLatLng.longitude&&latLng.latitude==currentLatLng.latitude)){
                                speed.text=NumberFormat.getInstance().let {
                                    it.minimumFractionDigits=1
                                    it.maximumFractionDigits=1
                                    it.format(computeSpeed(latLng,currentLatLng,System.currentTimeMillis()-previousTime))+"m/s"
                                }
                                previousLatLng=currentLatLng
                                previousTime=System.currentTimeMillis()
                            }
                        }
                    }
                }


                //海拔高度
                if (aMapLocation.hasAltitude()&&aMapLocation.altitude!=0.0&&!isGpsEnabled){
                    setAltitude(aMapLocation.altitude)
                }

                latitude=aMapLocation.latitude
                longitude=aMapLocation.longitude

                when(gpsUnit){
                    SettingActivity.GPS_VALUE_XS ->{
                        latLng.text=resources.getString(R.string.latLng,aMapLocation.latitude.toString(),aMapLocation.longitude.toString())
                    }
                    SettingActivity.GPS_VALUE_JD ->{
                        val lat=StringBuilder()
                                .append(aMapLocation.latitude.toInt().toString()+"°")
                                .append(((aMapLocation.latitude%1f)*60).toInt().toString()+"′")
                                .append(((((aMapLocation.latitude%1f)*60)%1f)*60).toInt().toString()+"″")
                                .toString()
                        val long=StringBuilder()
                                .append(aMapLocation.longitude.toInt().toString()+"°")
                                .append(((aMapLocation.longitude%1f)*60).toInt().toString()+"′")
                                .append(((((aMapLocation.longitude%1f)*60)%1f)*60).toInt().toString()+"″")
                                .toString()
                        latLng.text=resources.getString(R.string.latLng,lat,long)
                    }
                    else->{}
                }
                address.text=aMapLocation.address
                if(!astro){
                    setAstro(aMapLocation.latitude.toString(),aMapLocation.longitude.toString())
                }
            }else{
                Log.e(this.javaClass.simpleName+"定位失败","errorCode：${aMapLocation.errorCode},errorInfo:${aMapLocation.errorInfo}")
            }
        }
        val option = AMapLocationClientOption()
        option.isSensorEnable = true
        option.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        option.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.Sport//定位场景
        option.interval = 1000
        option.isNeedAddress = true//返回地址
        option.httpTimeOut = 20000
        option.isLocationCacheEnable = false
        mLocationClient?.let {
            it.setLocationOption(option)
            it.stopLocation()
            it.startLocation()
        }

        value5?.text =activity?.getSharedPreferences("sun_sp",Context.MODE_PRIVATE)?.getString("sun","日出  06:15   日落  18:66")
    }

    private fun initLocationMap(){
        val myLocationStyle = MyLocationStyle() //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000) //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap?.myLocationStyle = myLocationStyle //设置定位蓝点的Style
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
        aMap?.uiSettings?.also {
            it.isScaleControlsEnabled=true
            it.isMyLocationButtonEnabled=false
            it.isCompassEnabled=false
        }
        myLocationStyle.showMyLocation(true)
        aMap?.isMyLocationEnabled = true // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
    }

    private fun computeSpeed(previousLatLng:LatLng,currentLatLag:LatLng,interval:Long):Float{
        val distance = AMapUtils.calculateLineDistance(previousLatLng, currentLatLag)
        return distance/interval*1000
    }

    private fun setAltitude(altitudeValue:Double){
        var altitudeValueText=altitudeValue
        when(haibaUnit){
            SettingActivity.HAI_BA_VALUE_M ->{
                haibaUnitV.text=resources.getString(R.string.altitudeTips,"m")
            }
            SettingActivity.HAI_BA_VALUE_FT ->{
                haibaUnitV.text=resources.getString(R.string.altitudeTips,"英尺")
                altitudeValueText=altitudeValue*3.2808399
            }
            else->{}
        }
        val numberFormat=NumberFormat.getNumberInstance()
        numberFormat.also {
            it.minimumFractionDigits=2
            it.maximumFractionDigits=2
        }
        altitude.text =numberFormat.format(altitudeValueText)
    }

    fun refreshShow(){
        if (!isAdded){
            return
        }
        haibaUnit=SPUtil.getInstance().getString(SettingActivity.HAI_BA_UNIT_KEY, SettingActivity.HAI_BA_VALUE_M)
        gpsUnit=SPUtil.getInstance().getString(SettingActivity.GPS_UNIT_KEY, SettingActivity.GPS_VALUE_XS)
        when(gpsUnit){
            SettingActivity.GPS_VALUE_XS ->{
                latLng.text=resources.getString(R.string.latLng,latitude.toString(),longitude.toString())
            }
            SettingActivity.GPS_VALUE_JD ->{
                val lat=StringBuilder()
                        .append(latitude.toInt().toString()+"°")
                        .append(((latitude%1f)*60).toInt().toString()+"′")
                        .append(((((latitude%1f)*60)%1f)*60).toInt().toString()+"″")
                        .toString()
                val long=StringBuilder()
                        .append(longitude.toInt().toString()+"°")
                        .append(((longitude%1f)*60).toInt().toString()+"′")
                        .append(((((longitude%1f)*60)%1f)*60).toInt().toString()+"″")
                        .toString()
                latLng.text=resources.getString(R.string.latLng,lat,long)
            }
            else->{}
        }
        qiyaUnit=SPUtil.getInstance().getString(SettingActivity.QI_YA_UNIT_KEY, SettingActivity.QI_YA_VALUE_KPA)
        when(SPUtil.getInstance().getString(SettingActivity.DI_BU_SHOW_KEY, SettingActivity.DI_BU_VALUE_1)){
            SettingActivity.DI_BU_VALUE_1 ->{
                if (viewSwitcher.visibility!=View.VISIBLE){
                    viewSwitcher.visibility=View.VISIBLE
                }
                if (view5.visibility==View.VISIBLE){
                    view5.visibility=View.INVISIBLE
                }
                if (viewSwitcher.currentView!=latLngC){
                    viewSwitcher.showNext()
                }
            }
            SettingActivity.DI_BU_VALUE_2 ->{
                viewSwitcher.visibility=View.INVISIBLE
                view5.visibility=View.VISIBLE
            }
            SettingActivity.DI_BU_VALUE_3 ->{
                if (viewSwitcher.visibility!=View.VISIBLE){
                    viewSwitcher.visibility=View.VISIBLE
                }
                if (view5.visibility==View.VISIBLE){
                    view5.visibility=View.INVISIBLE
                }
                if (viewSwitcher.currentView==latLngC){
                    viewSwitcher.showNext()
                }
            }
            else->{}
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== TO_SETTING_REQUEST_CODE){
                    refreshShow()

        }
    }

    private fun setAstro(latitude:String,longitude:String){
        value5?.text =activity?.getSharedPreferences("sun_sp",Context.MODE_PRIVATE)?.getString("sun","日出  06:15   日落  18:66")


    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
        mLocationClient?.startLocation()
        sensorManager.registerListener(sensorEventListener, orientation, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(sensorEventListener, pressure, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
        sensorManager.unregisterListener(sensorEventListener)
        mLocationClient?.stopLocation()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        mapView.onDestroy()
        MyLocation.instance.removeCallback(this)
        mLocationClient?.onDestroy()
        super.onDestroyView()
    }

    override fun onLocationEnabled(enabled: Boolean) {
        outdoorsTips?:return
        isGpsEnabled=enabled
        if (!enabled){
            outdoorsTips.visibility=View.VISIBLE
            altitude.text="----"
        }else{
            outdoorsTips.visibility=View.GONE
        }
    }

    override fun onLocationResult(location: Location) {
        outdoorsTips?:return
        if (location.hasAltitude()){
            if (outdoorsTips.visibility!=View.GONE){
                outdoorsTips.visibility=View.GONE
            }
            setAltitude(location.altitude)
        }
    }
}