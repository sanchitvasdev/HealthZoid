package com.sanchit.healthzoid.customDrawables

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import com.sanchit.healthzoid.R

class TextViewDrawable constructor(
    resources: Resources,
    private val text: CharSequence
): Drawable(){
    val bound: Rect = bounds
    private val paint = Paint()
        .apply {
            color = ResourcesCompat.getColor(resources,
                R.color.primaryLightColor,null)
            isAntiAlias = true
            strokeWidth = resources.getDimension(R.dimen.strokewidth)
            textSize = resources.getDimension(R.dimen.textSize)
        }

    override fun draw(canvas: Canvas) {
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText(text.toString(),-24f,16f,paint)
    }

    override fun setAlpha(alpha: Int) {
        TODO("Not yet implemented")
    }

    override fun getOpacity(): Int {
        TODO("Not yet implemented")
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        TODO("Not yet implemented")
    }
}