package com.tino.kotlinmvvm.view

import android.app.Dialog
import android.content.Context
import android.widget.TextView
import com.tino.kotlinmvvm.R
import android.view.animation.Animation
import android.view.Gravity
import android.view.animation.AnimationUtils
import android.widget.ImageView

/**
 * 自定义弹窗 - Java
 */
class LoadingDialog : Dialog {
    var tvLoadingTx: TextView
    var ivLoading: ImageView

    constructor(context: Context?) : this(context, R.style.loading_dialog, "Loading...") {}
    constructor(context: Context?, string: String?) : this(
        context,
        R.style.loading_dialog,
        string
    ) {
    }

    constructor(context: Context?, close: Boolean) : this(
        context,
        R.style.loading_dialog,
        "Loading...",
        close
    ) {
    }

    constructor(context: Context?, theme: Int, string: String?) : super(
        context!!, theme
    ) {
        setCanceledOnTouchOutside(false) //点击其他区域时   true  关闭弹窗  false  不关闭弹窗
        setContentView(R.layout.dialog_loading) //加载布局
        tvLoadingTx = findViewById(R.id.tv_loading_tx)
        tvLoadingTx.text = string
        ivLoading = findViewById(R.id.iv_loading)
        // 加载动画
        val hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
            context, R.anim.loading_animation
        )
        // 使用ImageView显示动画
        ivLoading.startAnimation(hyperspaceJumpAnimation)
        window!!.attributes.gravity = Gravity.CENTER //居中显示
        window!!.attributes.dimAmount = 0.5f //背景透明度  取值范围 0 ~ 1
    }

    constructor(
        context: Context?,
        theme: Int,
        string: String?,
        isOtherOnClickClose: Boolean
    ) : super(
        context!!, theme
    ) {
        setCanceledOnTouchOutside(false) //点击其他区域时   true  关闭弹窗  false  不关闭弹窗
        setContentView(R.layout.dialog_loading) //加载布局
        tvLoadingTx = findViewById(R.id.tv_loading_tx)
        tvLoadingTx.text = string
        ivLoading = findViewById(R.id.iv_loading)
        // 加载动画
        val hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
            context, R.anim.loading_animation
        )
        // 使用ImageView显示动画
        ivLoading.startAnimation(hyperspaceJumpAnimation)
        window!!.attributes.gravity = Gravity.CENTER //居中显示
        window!!.attributes.dimAmount = 0.5f //背景透明度  取值范围 0 ~ 1
    }

    //关闭弹窗
    override fun dismiss() {
        super.dismiss()
    }
}