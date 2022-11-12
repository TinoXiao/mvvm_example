package com.tino.common.fragment

import android.content.Context
import android.os.Bundle
import androidx.annotation.IdRes
import com.tino.base.fragment.BaseFragment
import com.tino.common.activity.NavActivity

abstract class NavFragment : BaseFragment() {

    /**
     * 获取RouterActivity方便调用navigation进行页面切换
     */
    private var activity: NavActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavActivity) {
            activity = context
        }
    }

    fun onBackPressed(){
        activity?.onBackPressedDispatcher?.onBackPressed()
    }

    fun navigation(@IdRes resId:Int) {
        activity?.navigate(resId, null)
    }

    fun navigation(@IdRes resId:Int, bundle: Bundle? = null) {
        activity?.navigate(resId, bundle)
    }

}