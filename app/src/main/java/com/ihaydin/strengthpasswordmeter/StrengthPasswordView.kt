package com.ihaydin.strengthpasswordmeter

/**
 * Created by ismailhakkiaydin on 03,September,2021
 */

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt

import kotlin.math.roundToInt


class StrengthPasswordView : View {
    private var paint: Paint? = null
    private var mStrengthValue = 0
    private var mRadius = 0
    private var mTintSize = 0
    private var mTintColor = 0
    private var mStrengthBarSizeValue = 0
    private var mStrengthBarSpacerValue = 0
    private var mStrengthTextHeightValue = 0
    private var mListSelection = 0
    private var mList = listOf<State>()


    constructor(context: Context?) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val TEXT_HEIGHT = mStrengthTextHeightValue
        val SPACER = mStrengthBarSpacerValue
        val HEIGHT = (mStrengthTextHeightValue / 5)
        val WIDTH = mStrengthBarSizeValue
        val TOP = ((TEXT_HEIGHT / 5) * 3)
        for (i in 0 until mStrengthValue) {
            paint!!.textSize = TEXT_HEIGHT.toFloat()
            if (i < mTintSize) {
                paint!!.color = mList[mListSelection].color
                paint!!.style = Paint.Style.FILL

            } else {
                paint!!.color = resources.getColor(android.R.color.darker_gray)
            }
            canvas.drawRoundRect(
                RectF(
                    (i * WIDTH + SPACER * i).toFloat(),
                    TOP.toFloat(),
                    ((i + 1) * WIDTH + i * SPACER).toFloat(),
                    (TOP + HEIGHT).toFloat()
                ), mRadius.toFloat(), mRadius.toFloat(), paint!!
            )
            if (i == mStrengthValue - 1) {
                canvas.drawText(
                    mList[mListSelection].text!!,
                    ((i + 1) * WIDTH + (i + 1) * SPACER + 10).toFloat(), TEXT_HEIGHT.toFloat(),
                    paint!!
                )
            }
        }
        requestLayout()
        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val desiredWidth =
            (mStrengthValue) * (mStrengthBarSizeValue + mStrengthBarSpacerValue) + 4 * paint?.measureText(
                mList[mListSelection].text
            )!! + mStrengthTextHeightValue
        val desiredHeight = (mStrengthTextHeightValue - paint?.fontMetrics?.top!!).roundToInt()

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
        mStrengthValue = values.getInt(R.styleable.StrengthPasswordView_cv_strength_size, 0)
        mRadius = values.getInt(R.styleable.StrengthPasswordView_cv_strength_bar_radius, 0)
        mTintSize = values.getInt(R.styleable.StrengthPasswordView_cv_strength_bar_tint_size, 0)
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        values.recycle()
    }

    data class State(
        val text: String,
        @ColorInt val color: Int
    )

    fun setSelection(value : Int){
        mListSelection = value
    }

    fun setList(color: List<State>) {
        mList = color
    }

    fun setTintColor(color: Int) {
        mTintColor = color
    }

    fun setTintSize(size: Int) {
        mTintSize = size
    }

    fun setStrengthValue(value: Int) {
        mStrengthValue = value
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
