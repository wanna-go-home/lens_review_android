package com.wannagohome.lens_review_android.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.wannagohome.lens_review_android.R
import com.wannagohome.lens_review_android.databinding.LensCustomTitleBarBinding
import com.wannagohome.lens_review_android.extension.visible


class LensCustomTitleBar : FrameLayout {
    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context, attrs)
    }

    private lateinit var binding: LensCustomTitleBarBinding

    val title get() = binding.title
    val leftBtn get() = binding.leftBtn
    val rightBtn get() = binding.rightBtn

    val iconRightOfTitle get() = binding.iconRightOfTitle


    private fun initView(context: Context, attrs: AttributeSet?) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = LensCustomTitleBarBinding.inflate(inflater, this, true)

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.LensCustomTitleBar)

            a.getString(R.styleable.LensCustomTitleBar_titleName)?.let {
                val titleText = binding.title
                titleText.text = it
            }

            a.getDrawable(R.styleable.LensCustomTitleBar_leftImg)?.let {
                val leftBtn = binding.leftBtn
                leftBtn.background = it
                leftBtn.visibility = View.VISIBLE
            }

            a.getString(R.styleable.LensCustomTitleBar_leftText)?.let {
                val leftBtn = binding.leftBtn
                leftBtn.text = it
                leftBtn.visibility = View.VISIBLE
            }

            a.getDrawable(R.styleable.LensCustomTitleBar_rightImg)?.let {
                val rightBtn = binding.rightBtn
                rightBtn.background = it
                rightBtn.visibility = View.VISIBLE
            }

            a.getString(R.styleable.LensCustomTitleBar_rightText)?.let {
                val rightBtn = binding.rightBtn
                rightBtn.text = it
                rightBtn.visibility = View.VISIBLE
            }
            a.getDrawable(R.styleable.LensCustomTitleBar_iconRightOfTitle)?.let {
                val iconRightOfTitle = binding.iconRightOfTitle
                iconRightOfTitle.background = it
                iconRightOfTitle.visible()
            }

            a.recycle()
        }
    }


}