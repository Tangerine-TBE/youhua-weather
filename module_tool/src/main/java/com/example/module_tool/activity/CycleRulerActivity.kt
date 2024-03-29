package com.example.module_tool.activity

import android.Manifest
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.ImageReader
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.util.SparseIntArray
import android.view.Surface
import android.view.TextureView
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.module_tool.R
import com.example.module_tool.base.BaseActivity
import com.example.module_tool.utils.DeviceUtils
import com.example.module_tool.utils.getCloselyPreSize
import kotlinx.android.synthetic.main.activity_cycler_ruler_cjy.*


/**
 * Author : Gupingping
 * Date : 2019/3/4
 * QQ : 464955343
 */
class CycleRulerActivity : BaseActivity(), View.OnClickListener {

    private var mCameraManager: CameraManager? = null//摄像头管理器
    private var childHandler: Handler? = null
    private var mainHandler: Handler? = null
    private var mCameraID: String? = null//摄像头Id 0 为后  1 为前
    private var mImageReader: ImageReader? = null
    private var mCameraCaptureSession: CameraCaptureSession? = null
    private var mCameraDevice: CameraDevice? = null
    private var hasCapture = false
    private lateinit var surface: Surface
    private lateinit var surfaceTextureListener: TextureView.SurfaceTextureListener
    private var surfaceTextureAvailable=false

    override fun getLayoutId(): Int = R.layout.activity_cycler_ruler_cjy


    override fun isActionBar(): Boolean = false

    override fun initView() {

            CycleRulerView_toolbar?.setTitle("量角器")

        initCallBack()
        take.setOnClickListener(this)
        textureView.surfaceTextureListener=surfaceTextureListener
    }

    override fun initEvent() {
        CycleRulerView_toolbar.finishActivity(this)
    }

    private fun initCallBack(){
        surfaceTextureListener=object :TextureView.SurfaceTextureListener{
            override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {

            }

            override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {

            }

            override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean {
                return false
            }

            override fun onSurfaceTextureAvailable(p0: SurfaceTexture, p1: Int, p2: Int) {
                surfaceTextureAvailable=true
                initCamera2()
            }
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            take.id -> {
                if (!hasCapture) {
                    takePicture()
                    hasCapture = true
                    take.setText(R.string.preview)
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        initCamera2()
                        hasCapture = false
                        take.setText(R.string.photograph)
                    }
                }
            }
        }

    }


    override fun onResume() {
        super.onResume()
        if (surfaceTextureAvailable){
            initCamera2()
        }
    }

    override fun onPause() {
        mCameraDevice?.close()
        mCameraCaptureSession?.close()
        super.onPause()
    }

    /**
     * 初始化Camera2
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun initCamera2() {
        val handlerThread = HandlerThread("Camera2")
        handlerThread.start()
        childHandler = Handler(handlerThread.looper)
        mainHandler = Handler(mainLooper)
        mCameraID = "" + CameraCharacteristics.LENS_FACING_FRONT//后摄像头
        mImageReader = ImageReader.newInstance(1080, 1920, ImageFormat.JPEG, 1)
        mImageReader?.setOnImageAvailableListener(ImageReader.OnImageAvailableListener { reader ->
            //可以在这里处理拍照得到的临时照片 例如，写入本地
            mCameraDevice?.close()
            textureView?.visibility = View.GONE
//            iv_show?.visibility = View.VISIBLE
            // 拿到拍照照片数据
            val image = reader.acquireNextImage()
            val buffer = image.planes[0].buffer
            val bytes = ByteArray(buffer.remaining())
            buffer.get(bytes)//由缓冲区存入字节数组
//            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
//            if (bitmap != null) {
//                iv_show?.setImageBitmap(bitmap)
//            }
        }, mainHandler)
        //获取摄像头管理
        mCameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            //打开摄像头
            mCameraManager?.openCamera(mCameraID!!, stateCallback, mainHandler)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

    }

    /**
     * 摄像头创建监听
     */
    private val stateCallback = @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            mCameraDevice = camera
            //开启预览

            takePreview()
        }

        override fun onDisconnected(camera: CameraDevice) {
            if (null != mCameraDevice) {

                mCameraDevice?.close()
                mCameraDevice = null
            }
        }

        override fun onError(camera: CameraDevice, error: Int) {
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun takePreview() {
        try {
            // 创建预览需要的CaptureRequest.Builder
            val previewRequestBuilder = mCameraDevice?.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            val surfaceTexture=textureView.surfaceTexture
            if (mCameraID==null) return
            val outputSizes=mCameraManager?.getCameraCharacteristics(mCameraID!!)?.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)?.getOutputSizes(SurfaceTexture::class.java)?.toList()?:return
            val size=getCloselyPreSize(true, DeviceUtils.getScreenWidth(this@CycleRulerActivity),DeviceUtils.getScreenHeight(this@CycleRulerActivity),outputSizes)
            if (size!=null)
                surfaceTexture!!.setDefaultBufferSize(size.width,size.height)
            surface= Surface(surfaceTexture)
            previewRequestBuilder?.addTarget(surface)
            // 创建CameraCaptureSession，该对象负责管理处理预览请求和拍照请求
            mCameraDevice?.createCaptureSession(listOf(surface, mImageReader?.surface), object : CameraCaptureSession.StateCallback() {
                override fun onConfigureFailed(session: CameraCaptureSession) {

                }

                override fun onConfigured(session: CameraCaptureSession) {
                    if (null == mCameraDevice) return
                    // 当摄像头已经准备好时，开始显示预览
                    mCameraCaptureSession = session

                    try {
                        // 自动对焦
                        previewRequestBuilder?.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
                        // 打开闪光灯
                        previewRequestBuilder?.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH)
                        // 显示预览
                        val previewRequest = previewRequestBuilder?.build()
                        if (previewRequest != null)
                            mCameraCaptureSession?.setRepeatingRequest(previewRequest, null, childHandler)
                    } catch (e: CameraAccessException) {
                    }
                }

            }, childHandler)
        } catch (e: CameraAccessException) {
        }
    }


    /**
     * 拍照
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun takePicture() {
        if (mCameraDevice == null) return
        // 创建拍照需要的CaptureRequest.Builder
        val captureRequestBuilder: CaptureRequest.Builder
        try {
            captureRequestBuilder = mCameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
            // 将imageReader的surface作为CaptureRequest.Builder的目标
            captureRequestBuilder.addTarget(surface)
            // 自动对焦
            captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
            // 自动曝光
            captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH)
            // 获取手机方向
            val rotation = windowManager.defaultDisplay.rotation
            // 根据设备方向计算设置照片的方向
            captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation))
            //拍照
            val mCaptureRequest = captureRequestBuilder.build()

            mCameraCaptureSession?.stopRepeating()
            mCameraCaptureSession?.abortCaptures()
            mCameraCaptureSession?.capture(mCaptureRequest, null, childHandler)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }


    }
    override fun onDestroy() {
        super.onDestroy()
        mCameraDevice?.close()
        mCameraCaptureSession?.close()
    }

    companion object {
        private var ORIENTATIONS = SparseIntArray()

        init {
            ORIENTATIONS.append(Surface.ROTATION_0, 90)
            ORIENTATIONS.append(Surface.ROTATION_90, 0)
            ORIENTATIONS.append(Surface.ROTATION_180, 270)
            ORIENTATIONS.append(Surface.ROTATION_270, 180)
        }
    }

}