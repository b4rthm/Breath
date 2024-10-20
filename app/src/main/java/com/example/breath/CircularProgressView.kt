package com.example.breath

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class CircularProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val backgroundPaint = Paint().apply {
        color = Color.LTGRAY
        style = Paint.Style.STROKE
        strokeWidth = 20f
        isAntiAlias = true
    }

    private val progressPaint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.STROKE
        strokeWidth = 20f
        isAntiAlias = true
    }

    private var progressRect = RectF()
    private var progress: Float = 0f // Percentage progress (0 to 100)

    init {
        // Initialize the progress rect with default values
        post {
            val padding = 20f
            progressRect.set(
                padding, padding,
                width - padding, height - padding
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw background circle
        canvas.drawArc(progressRect, 0f, 360f, false, backgroundPaint)

        // Draw progress circle (from 270° - top of the circle)
        val sweepAngle = (progress / 100f) * 360f
        canvas  .drawArc(progressRect, -90f, sweepAngle, false, progressPaint)
    }

    fun setProgress(progress: Float) {
        this.progress = progress
        invalidate() // Redraw the view with the new progress value
    }
}
