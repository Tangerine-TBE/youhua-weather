package com.example.tianqi.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 * @name WeatherOne
 * @class name：com.example.tianqi.ui.custom
 * @class describe
 * @author wujinming QQ:1245074510
 * @time 2020/10/9 16:44
 * @class describe
 */
class RadAqiView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var outPaint:Paint=Paint()
    private var linePaint:Paint= Paint()
    private var paintWidth=60f
    init {
        outPaint.color=Color.WHITE
        outPaint.style=Paint.Style.STROKE
        outPaint.isAntiAlias=true
        outPaint.strokeWidth=paintWidth

        linePaint.color=Color.WHITE
        linePaint.isAntiAlias=true
        linePaint.strokeWidth=5f


    }

    private var mWidth=0f
    private var mHeight=0f
    private var mProgress=0f
    private var mRotate=0f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        mWidth=width.toFloat()
        mHeight=height.toFloat()
        setMeasuredDimension(width,height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val paintSize = paintWidth / 2+20
        val radius=mWidth/2f
        canvas.save()
        canvas.rotate(mRotate,radius,mHeight)
        canvas.drawLine(radius, radius-20, radius, mHeight-85, linePaint)
        canvas.restore()

        canvas.translate(radius, mHeight)
        val rectF = RectF(-radius+paintSize, -radius+paintSize, radius-paintSize, radius-paintSize)
        canvas.drawArc(rectF,-180f,mProgress,false,outPaint)
        outPaint.color=Color.parseColor("#47ffffff")
        canvas.drawArc(rectF,-180f,180f,false,outPaint)


    }

    fun setProgress(progress:Float) {
        this.mProgress=progress
        setRotate(progress)
        invalidate()
    }


    private fun setRotate(progress:Float) {
        when {
            progress==90f -> mRotate=0f
            progress>90 -> mRotate=progress-90
            progress<90 ->mRotate=progress-90
        }
    }
}