package com.mcarving.stocktracker.portfolios

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.widget.ImageView
import com.mcarving.stocktracker.R

class CustomImageView : ImageView{

    lateinit var mImage : Bitmap

    constructor(context: Context): this(context, null){
        init()
    }

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0){
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr){
        init()
    }

    fun init(){
        mImage = BitmapFactory.decodeResource(resources, R.drawable.run)
        // getWidth() and getHeight() will return 0 since the view hasn't finished constructing
        // option 1: might override onMeasure() method to get width and height
        // option 2: use getViewTreeObserver()

        viewTreeObserver.addOnGlobalLayoutListener (

            object: ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {

                    viewTreeObserver.removeOnGlobalLayoutListener(this)

                    mImage = getResizedBitmap(mImage, width / 2, width / 3)
                }

        })

    }


    override fun onDraw(canvas: Canvas?) {
        val imageX = (width - mImage.width)/2
        val imageY = (height - mImage.height)/2

        canvas?.apply {
            drawBitmap(mImage, imageX.toFloat(), imageY.toFloat(), null)
        }
    }

    fun getResizedBitmap(bitmap: Bitmap, reqWidth: Int, reqHeight : Int): Bitmap{
        val matrix : Matrix = Matrix()

        val src = RectF(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat())
        val dst = RectF(0f, 0f, reqWidth.toFloat(), reqHeight.toFloat())

        matrix.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}