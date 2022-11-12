package com.tino.kotlinmvvm

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.tino.base.application.BaseApplication
import com.tino.common.net.NetSet

class AppApplication : BaseApplication() {

    companion object {
        const val isDebug = true
        private lateinit var mApplication : Application
    }

    override fun onCreate() {
        super.onCreate()

        mApplication = this

        if (isDebug) {
            ARouter.openLog() //打印日志
            ARouter.openDebug() //开启调试模式，线上需关闭
        }
        ARouter.init(this)

    }

}