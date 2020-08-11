package com.xiaoyu.verificationcodeinputview

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.InputFilter
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.widget.addTextChangedListener
import com.xiaoyu.verificationcodeinputview.databinding.LayoutVerificationCodeInputViewBinding

class VerificationCodeInputView : FrameLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?)
            : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleInt: Int)
            : super(context, attrs, defStyleInt) {
        init(attrs, defStyleInt)
    }

    private lateinit var mDataBinding: LayoutVerificationCodeInputViewBinding

    //输入回调
    var onInputFinishCallback: OnInputFinishCallback? = null

    //字符数量
    var codeCount: Int = 4

    //字符颜色
    var codeColor: Int = ContextCompat.getColor(context, R.color.black)

    //字符大小 单位sp
    var codeSize: Float = 14f

    //字符背景
    var codeBackground: Drawable? =
        ContextCompat.getDrawable(context, R.drawable.default_code_background)

    //字符间距
    var codePadding: Int = context.resources.getDimensionPixelOffset(R.dimen.default_padding)

    private fun init(attrs: AttributeSet?, defStyleInt: Int) {
        mDataBinding =
            LayoutVerificationCodeInputViewBinding.inflate(LayoutInflater.from(context), this, true)
        context.obtainStyledAttributes(attrs, R.styleable.VerificationCodeInputView, defStyleInt, 0)
            .apply {
                codeCount = getInt(R.styleable.VerificationCodeInputView_code_count, codeCount)
                codeColor = getInt(R.styleable.VerificationCodeInputView_code_color, codeColor)
                codeSize = getDimension(R.styleable.VerificationCodeInputView_code_size, codeSize)
                codeBackground =
                    getDrawable(R.styleable.VerificationCodeInputView_code_background)
                        ?: codeBackground
                codePadding = getDimensionPixelOffset(
                    R.styleable.VerificationCodeInputView_code_padding,
                    codePadding
                )
            }.recycle()

        //设置输入限制
        mDataBinding.inputView.filters = arrayOf(InputFilter.LengthFilter(codeCount))

        mDataBinding.inputView.addTextChangedListener { setCode(it?.toString() ?: "") }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initShowView()
    }

    private fun initShowView() {
        //创建View
        for (i in 1..codeCount) {
            mDataBinding.showView.addView(createTextView(i))
        }
    }

    /**
     *创建View
     */
    private fun createTextView(index: Int): TextView {
        return TextView(context).apply {
            gravity = Gravity.CENTER
            setTextSize(TypedValue.COMPLEX_UNIT_SP, codeSize)
            setTextColor(codeColor)
            ViewCompat.setBackground(this, codeBackground)
            layoutParams = LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.MATCH_PARENT,
                1f
            ).apply {
                rightMargin = if (index == codeCount) 0 else codePadding
                gravity = Gravity.CENTER_VERTICAL
            }
        }
    }

    private fun setCode(newCode: String) {
        for (i in 0 until codeCount) {
            val view = mDataBinding.showView.getChildAt(i)
            if (view is TextView) {
                view.text = if (i >= newCode.length) "" else newCode[i].toString()
            }
        }
        if (newCode.length == codeCount) {
            onInputFinishCallback?.onInputFinish(this, newCode)
        }
    }

    interface OnInputFinishCallback {
        fun onInputFinish(view: VerificationCodeInputView, code: String)
    }
}