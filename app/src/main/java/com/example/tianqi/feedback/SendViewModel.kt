package com.example.tianqi.feedback

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.module_tool.base.BaseConstant
import com.example.module_tool.utils.iLog
import com.example.module_tool.utils.toMd5
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.File
import java.io.IOException

class SendViewModel: ViewModel() {
    private val okHttpClient by lazy {
        OkHttpClient()
    }

    fun send(content:String,contact:String,imageUrl:List<String>,successAction:()->Unit,failAction: (String) -> Unit){
        val map=mapOf<String,String>(
                "content" to content,
                "contact" to contact,
                "user_id" to "111",
                "user_system" to "1",
                "user_package" to BaseConstant.application.packageName,
                "package_chn" to BaseConstant.application.packageManager.getApplicationInfo(BaseConstant.application.packageName,0).loadLabel(BaseConstant.application.packageManager).toString()+"太短"
        )
        val imageP=HashMap<String,String>()
        if (imageUrl.size>=1){
            imageP["img_one"]=imageUrl.first()
        }
        if (imageUrl.size>=2){
            imageP["img_two"]=imageUrl[1]
        }
        if (imageUrl.size>=3){
            imageP["img_three"]=imageUrl[2]
        }
        sendPostSecret("http://position.aisou.club/usercenter/public/feedback",map,{
            iLog(it,"反馈")
            BaseConstant.mainHandler.post {
                try {
                    val data=JSONObject(it)
                    val code=data.getString("code")
                    if (code=="200"){
                        successAction.invoke()
                    }else{
                        failAction.invoke(data.getString("msg"))
                    }
                }catch (e:Exception){
                    failAction.invoke("服务异常")
                }
            }

        },{
            iLog(it,"反馈")
            BaseConstant.mainHandler.post {
                failAction.invoke("服务异常")
            }
        },imageP)
    }

    fun pullImageView(uri:Uri,action:(String)->Unit,failAction:(String)->Unit){
        val mediaType= "application/octet-stream; charset=utf-8".toMediaTypeOrNull() ?:return

        val file=uriToTempFile(uri)
        val body=RequestBody.create(mediaType,file)
        val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("thumb", file.name, body)
                .build()
        val request=Request.Builder()
                .url("http://position.aisou.club/usercenter/position/uploadImg")
                .post(requestBody)
                .build()
        okHttpClient.newCall(request).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                iLog("异常","上传图片")
                failAction.invoke("网络异常")
            }

            override fun onResponse(call: Call, response: Response) {
                val bodyString=response.body?.string()
                iLog(bodyString?:"","上传图片")
                if (bodyString!=null){
                    try {
                        val jsonObject=JSONObject(bodyString)
                        action.invoke(jsonObject.getJSONObject("data").getString("img_url"))
                    }catch (e:Exception){
                        failAction.invoke("服务器异常")
                    }
                }else{
                    failAction.invoke("服务器异常")
                }
            }
        })
    }

    private fun uriToTempFile(uri: Uri):File{
        val file=File(BaseConstant.application.cacheDir,"temp")
        if (file.exists()){
            file.delete()
        }
        BaseConstant.application.contentResolver.openInputStream(uri)?.use { inputStream ->
            val byteArray=ByteArray(1024)
            var length=0
            file.outputStream().use { outputStream ->
                while (inputStream.read(byteArray).let {
                            length=it
                            it!=-1
                        }){
                    outputStream.write(byteArray,0,length)
                }
            }
        }
        return file
    }


    private fun sendPostSecret(url: String, parameter:Map<String,String>,success: (String) -> Unit, fail: (msg: String) -> Unit,parameter2:Map<String,String>?=null){
        val t=(System.currentTimeMillis()/1000).toString()
        val builder=FormBody.Builder()
        parameter.forEach {
            builder.add(it.key, it.value)
        }
        parameter2?.forEach {
            builder.add(it.key,it.value)
        }
        builder.add("timestamp",t)
        builder.add("signature",getSignature(url, parameter,t))
        val request=Request.Builder()
                .url(url)
                .post(builder.build())
                .build()
        okHttpClient.newCall(request).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                fail.invoke(e.message?:"出错了")
            }

            override fun onResponse(call: Call, response: Response) {
                val s=response.body?.string()?:return fail.invoke("出错了")
                success.invoke(s)
            }

        })
    }

    private fun getSignature(url: String, parameter:Map<String,Any>, t:String):String{
        val stringBuilder=StringBuilder()
        parameter.values.map { it.toString() }.sorted().forEach {
            stringBuilder.append(it)
        }
        val d=stringBuilder.toString()
        val timestamp=t
        val token="x389fh^feahykge"
        val nonce=523146
        val service=url.substringAfter("/").substringAfter("?").split("/").let { list ->
            if (list.size>=2){
                val sb=StringBuilder()
                list.takeLast(2).forEach {
                    sb.append(it)
                    sb.append("/")
                }
                sb.removeSuffix("/")
                sb.toString()
            }else{
                ""
            }.removeSuffix("/")
        }
        val signature=(token+timestamp+nonce+service+d).toMd5()

        return signature
    }

    override fun onCleared() {
        super.onCleared()
        okHttpClient.dispatcher.cancelAll()
    }
}