
package com.example.module_tool.utils

import android.app.Activity
import android.app.Application
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.module_base.BuildConfig
import com.example.module_tool.base.BaseApplication
import java.security.MessageDigest


/**
 * Author : Gupingping
 * Date : 2018/7/15
 * Mail : gu12pp@163.com
 */

fun Activity.toast(msg: String) {
    Handler(mainLooper).post {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
fun Activity.toast(msgRes: Int) {
    Handler(mainLooper).post {
        Toast.makeText(this, msgRes, Toast.LENGTH_SHORT).show()
    }
}

fun Fragment.toast(msg: String) {
    Handler(BaseApplication.application.mainLooper).post {
        Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()
    }
}
fun Fragment.toast(msgRes: Int) {
    Handler(BaseApplication.application.mainLooper).post {
        Toast.makeText(this.context, msgRes, Toast.LENGTH_SHORT).show()
    }
}



fun Application.toast(msg: String) {
    Handler(mainLooper).post {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
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

fun Any.iLog(text: String,tag:String=this.javaClass.simpleName){
    if (!BuildConfig.DEBUG){
        return
    }
    Log.e("myLog,$tag",text)
}

