package com.tino.common.activity

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.tino.base.activity.BaseActivity
import com.tino.base.util.ToastUtil.toast

/**
 * 路由类，方便模块之间调用
 */
abstract class NavActivity : BaseActivity() {

    private lateinit var navController: NavController
    private var exitTime = 0L

    /**
     * NavController的视图id
     */
    abstract fun controllerId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - exitTime > 2000) {
                    exitTime = System.currentTimeMillis()
                    val msg = "再按一次退出"
                    msg.toast()
                } else {
                    Log.e("AAA","else.............")
                    moveTaskToBack(true)
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        navController = Navigation.findNavController(this,controllerId())
    }

    fun navigate(@IdRes resId: Int, args: Bundle? = null) {
        navController.navigate(
            resId, args
        )
    }

    /**
     * 通过正则匹配“{}”内的参数并替换
     */
    fun navigate(deepLink: String) {
        var newDeepLink = "http://$deepLink"

        navController.navigate(
            Uri.parse(newDeepLink)
        )
    }

    fun popBackStack(@IdRes destinationId: Int, inclusive: Boolean) {
        navController.popBackStack(destinationId, inclusive)
    }

}