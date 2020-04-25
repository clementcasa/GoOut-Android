package com.meero.photograph.app.ui.widgets

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import io.clemcasa.goout.R

open class CustomConstraintLayout(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private val CORNER_TOP_LEFT = 1
    private val CORNER_TOP_RIGHT = 2
    private val CORNER_BOTTOM_RIGHT = 4
    private val CORNER_BOTTOM_LEFT = 8

    private var shape = GradientDrawable()

    init {
        var backgroundColor: Int
        var cornerRadius: Float
        var drawCorner: Int
        var strokeWidth: Int
        var strokeColor: Int
        with(context.obtainStyledAttributes(attrs, R.styleable.CustomConstraintLayout, 0, 0)) {
            backgroundColor = getColor(R.styleable.CustomConstraintLayout_backgroundColor, ContextCompat.getColor(context, android.R.color.white))
            cornerRadius = getDimension(R.styleable.CustomConstraintLayout_backgroundCornerRadius, 0.0f)
            drawCorner = getInt(R.styleable.CustomConstraintLayout_backgroundCornerRadiusMask, 0)
            strokeWidth = getInt(R.styleable.CustomConstraintLayout_strokeThickness, 0)
            strokeColor = getColor(R.styleable.CustomConstraintLayout_strokeColor, ContextCompat.getColor(context, android.R.color.white))
            recycle()
        }
        val cornerRadii = FloatArray(4)
        if (drawCorner.containsFlag(CORNER_TOP_LEFT)) {
            cornerRadii[0] = cornerRadius
        }
        if (drawCorner.containsFlag(CORNER_TOP_RIGHT)) {
            cornerRadii[1] = cornerRadius
        }
        if (drawCorner.containsFlag(CORNER_BOTTOM_RIGHT)) {
            cornerRadii[2] = cornerRadius
        }
        if (drawCorner.containsFlag(CORNER_BOTTOM_LEFT)) {
            cornerRadii[3] = cornerRadius
        }
        setCornerRadii(cornerRadii[0], cornerRadii[1], cornerRadii[2], cornerRadii[3])
        setBackgroundColor(backgroundColor)
        setStroke(strokeWidth, strokeColor)
        background = shape
    }

    fun setCornerRadii(topLeft: Float = 0.0f, topRight: Float = 0.0f, bottomRight: Float = 0.0f, bottomLeft: Float = 0.0f) {
        val cornerRadii = FloatArray(8)
        cornerRadii[0] = topLeft
        cornerRadii[1] = topLeft
        cornerRadii[2] = topRight
        cornerRadii[3] = topRight
        cornerRadii[4] = bottomRight
        cornerRadii[5] = bottomRight
        cornerRadii[6] = bottomLeft
        cornerRadii[7] = bottomLeft
        shape.cornerRadii = cornerRadii
    }

    final override fun setBackgroundColor(color: Int) {
        shape.color = ColorStateList.valueOf(color)
    }

    fun setStroke(width: Int, color: Int) {
        shape.setStroke(width, color)
    }

    private fun Int.containsFlag(flag: Int): Boolean = (this or flag) == this
}
