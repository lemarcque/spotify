package io.capsulo.spotify

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.R.attr.radius
import android.R.attr.bitmap
import android.graphics.*


/**
 * Todo : Add class description
 */
class Artwork : ImageView {


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

       /* val shader: BitmapShader
        shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        val paint = Paint()
        paint.setAntiAlias(true)
        paint.setShader(shader)

        val rect = RectF(0.0f, 0.0f, width.toFloat(), height.toFloat())

        // rect contains the bounds of the shape
        // radius is the radius in pixels of the rounded corners
        // paint contains the shader that will texture the shape
        canvas?.drawRoundRect(rect, radius.toFloat(), radius.toFloat(), paint)*/
    }
}