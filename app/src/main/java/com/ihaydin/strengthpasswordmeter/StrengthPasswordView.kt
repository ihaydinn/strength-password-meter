package com.ihaydin.strengthpasswordmeter

/**
 * Created by ismailhakkiaydin on 03,September,2021
 */

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import kotlin.math.max


class StrengthPasswordView : View {
    private var paint: Paint? = null
    private var mStrengthCountSize = 0
    private var mStrengthBarRadius = 0
    private var mStrengthTintCountSize = 0
    private var mStrengthCountTintColor = 0
    private var mStrengthBarSizeValue = 0
    private var mStrengthBarSpacerValue = 0
    private var mStrengthTextHeightValue = 0
    private var mListSelection = 0
    private var mTextPaddingStart = 0f
    private var mList = listOf<State>()

    private val density = resources.displayMetrics.density

    constructor(context: Context?) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val TEXT_HEIGHT = mStrengthTextHeightValue * density
        val SPACER = mStrengthBarSpacerValue
        val WIDTH = mStrengthBarSizeValue * density

        val r = Rect()
        canvas.getClipBounds(r)

        for (i in 0 until mStrengthCountSize) {
            paint?.textSize = TEXT_HEIGHT.toFloat()
            if (i < mStrengthTintCountSize) {
                paint?.color = mList[mListSelection].color
            } else {
                paint?.color = resources.getColor(android.R.color.darker_gray)
            }

            canvas.drawRoundRect(
                RectF(
                    (i * WIDTH + SPACER * i).toFloat(),
                    r.height() / 2f - 4f,
                    ((i + 1) * WIDTH + i * SPACER).toFloat(),
                    r.height() / 2f + 4f
                ), mStrengthBarRadius.toFloat(), mStrengthBarRadius.toFloat(), paint!!
            )


            if (i == mStrengthCountSize - 1) {
                paint?.color = mList[mListSelection].color
                drawText(
                    canvas,
                    paint!!,
                    mList[mListSelection].text,
                    maxWidth + mTextPaddingStart + ((i + 1) * WIDTH + (i + 1) * SPACER).toFloat()
                )
            }
        }

        requestLayout()
        super.onDraw(canvas)
    }

    private fun drawText(canvas: Canvas, paint: Paint, text: String, posX: Float) {
        val r = Rect()
        canvas.getClipBounds(r)
        val cHeight: Int = r.height()
        val cWidth: Int = r.width()
        paint.textAlign = Paint.Align.RIGHT
        paint.getTextBounds(text, 0, text.length, r)
        val x: Float = posX
        val y: Float = cHeight / 2f + r.height() / 2f - r.bottom
        canvas.drawText(text, x, y, paint)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val bounds = Rect()
        paint?.getTextBounds(
            mList[mListSelection].text,
            0,
            mList[mListSelection].text.length,
            bounds
        )

        val desiredWidth =
            (mStrengthCountSize) * (mStrengthBarSizeValue * density + mStrengthBarSpacerValue) + getMaxTextWidth() + mTextPaddingStart
        val desiredHeight = getMaxTextHeight()

        val widthResult = when (widthMode) {
            MeasureSpec.EXACTLY -> {
                if (widthSize < desiredWidth) widthSize or View.MEASURED_STATE_TOO_SMALL
                else widthSize
            }
            MeasureSpec.AT_MOST -> {
                if (widthSize < desiredWidth) widthSize or View.MEASURED_STATE_TOO_SMALL
                else desiredWidth
            }
            MeasureSpec.UNSPECIFIED -> desiredWidth
            else -> throw RuntimeException("Unknown width mode $widthMode")
        }
        val heightResult = when (heightMode) {
            MeasureSpec.EXACTLY -> {
                if (heightSize < desiredHeight) heightSize or View.MEASURED_STATE_TOO_SMALL
                else heightSize
            }
            MeasureSpec.AT_MOST -> {
                if (heightSize < desiredHeight) heightSize or View.MEASURED_STATE_TOO_SMALL
                else desiredHeight
            }
            MeasureSpec.UNSPECIFIED -> desiredHeight
            else -> throw RuntimeException("Unknown width mode $widthMode")
        }
        setMeasuredDimension(widthResult.toInt(), heightResult)
    }


    private fun init(context: Context, attrs: AttributeSet?) {
        val values =
            context.theme.obtainStyledAttributes(attrs, R.styleable.StrengthPasswordView, 0, 0)
        mStrengthCountSize = values.getInt(R.styleable.StrengthPasswordView_cv_strength_count_size, 0)
        mStrengthBarRadius = values.getInt(R.styleable.StrengthPasswordView_cv_strength_bar_radius, 0)
        mStrengthTintCountSize = values.getInt(R.styleable.StrengthPasswordView_cv_strength_tint_count_size, 0)
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        values.recycle()
    }

    private fun getMaxTextHeight(): Int {
        var maxHeight = 0
        mList.forEachIndexed { index, state ->
            val bounds = Rect()
            paint?.getTextBounds(
                mList[index].text,
                0,
                mList[index].text.length,
                bounds
            )
            maxHeight = max(maxHeight, bounds.height())
        }
        return maxHeight
    }

    var maxWidth = 0
    private fun getMaxTextWidth(): Int {

        mList.forEachIndexed { index, state ->
            val bounds = Rect()
            paint?.getTextBounds(
                mList[index].text,
                0,
                mList[index].text.length,
                bounds
            )
            maxWidth = max(maxWidth, bounds.width())
        }
        return maxWidth
    }

    data class State(
        val text: String,
        @ColorInt val color: Int
    )

    fun setSelection(value: Int) {
        mListSelection = value
        requestLayout()
    }

    fun setTextPaddingStart(value: Float) {
        mTextPaddingStart = value * density
    }

    fun setList(color: List<State>) {
        mList = color
    }

    fun setStrengthCountTintColor(color: Int) {
        mStrengthCountTintColor = color
    }

    fun setTintSize(size: Int) {
        mStrengthTintCountSize = size
    }

    fun setStrengthCountSize(size: Int) {
        mStrengthCountSize = size
    }

    fun setStrengthBarSizeValue(value: Int) {
        mStrengthBarSizeValue = value
    }

    fun setStrengthBarSpacerValue(value: Int) {
        mStrengthBarSpacerValue = value
    }

    fun setStrengthTextHeightValue(value: Int) {
        mStrengthTextHeightValue = value
    }

}
