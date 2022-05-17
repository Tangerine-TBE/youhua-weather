package com.example.module_ad

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.umeng.analytics.MobclickAgent
import java.security.MessageDigest
import java.util.regex.Pattern

fun Any.dLog(text:String,tag:String=this.javaClass.simpleName){
    if (!BuildConfig.DEBUG){
        return
    }
    Log.d("myLog,$tag",text)
}

fun Any.iLog(text:String?,tag:String=this.javaClass.simpleName){
    if (!BuildConfig.DEBUG){
        return
    }
    Log.i("myLog,$tag",text?:"null")
}

fun Any.eLog(text:String,tag:String=this.javaClass.simpleName){
    if (!BuildConfig.DEBUG){
        return
    }
    Log.e("myLog,$tag",text)
}
var toast:Toast?=null

fun Any.toast(context: Context?,text: String){
    context?:return
    toast?.cancel()
    toast=Toast.makeText(context,text,Toast.LENGTH_SHORT)
    toast?.show()
}
fun Fragment.toast(text: String){
    context?:return
    toast?.cancel()
    toast=Toast.makeText(context,text,Toast.LENGTH_SHORT)
    toast?.show()
}

fun Context.toast(text: String){
    toast?.cancel()
    toast=Toast.makeText(this,text,Toast.LENGTH_SHORT)
    toast?.show()
}

fun Context.longToast(text: String){
    toast?.cancel()
    toast=Toast.makeText(this,text,Toast.LENGTH_LONG)
    toast?.show()
}

fun String.toMd5():String{
    val string=this
    if (string.isEmpty()){
        return string
    }
    val md5= MessageDigest.getInstance("MD5")
    val byteArray=md5.digest(string.toByteArray())
    return try {
        val result=StringBuilder()
        byteArray.forEach {
            var t=Integer.toHexString(it.toInt() and 0xff)
            if (t.length==1){
                t= "0$t"
            }
            result.append(t)
        }
        result.toString()
    }catch (e:Exception){
        ""
    }
}



fun Context.getSign():String?{//caae455783afb444ff33e4e8e796d304
    return packageManager
            .getPackageInfo(packageName, PackageManager.GET_SIGNATURES)//GET_SIGNING_CERTIFICATES
//            .signingInfo.apkContentsSigners
            .signatures
            .find { it!=null }
            ?.toByteArray()
            ?.let { bytes ->
                val md=MessageDigest.getInstance("MD5")
                md.update(bytes)
                md.digest().map {
                    Integer.toHexString(it.toInt() and 0xFF)
                }.map {
                    if (it.length<2){
                        "0$it"
                    }else{
                        it
                    }
                }.let {
                    val str= java.lang.StringBuilder()
                    it.forEach {
                        str.append(it)
                    }
                    str.toString()
                }
            }
}

fun Context.isSignOk():Boolean{
    return getSign()=="caae455783afb444ff33e4e8e796d304"
}

/**
 * 去掉手机号内除数字外的所有字符
 *
 * @param phoneNum 手机号
 * @return
 */
fun String.formatPhoneNum(): String {
    val regex = "(\\+86)|[^0-9]"
    val pattern= Pattern.compile(regex)
    val matcher= pattern.matcher(this)
    return matcher.replaceAll("")
}

fun Activity.requestPermission(permission:Array<String>, message:String, requestCode:Int=0,negativeAction:(()->Unit)?=null):Boolean{
    if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
        return true
    }
    val isGranted=permission.map { ContextCompat.checkSelfPermission(this,it) }.all { it==PackageManager.PERMISSION_GRANTED }
    if (isGranted){
        return true
    }else{
        AlertDialog.Builder(this)
                .setTitle("请求权限")
                .setMessage(message)
                .setPositiveButton("去申请"){dialog,which->
                    try {
                        requestPermissions(permission,requestCode)
                    }catch (e:Exception){
                        iLog(e.stackTraceToString())
                    }
                }
                .setNegativeButton("取消"){dialog,which->
                    negativeAction?.invoke()
                }
                .show()
        return false
    }
}