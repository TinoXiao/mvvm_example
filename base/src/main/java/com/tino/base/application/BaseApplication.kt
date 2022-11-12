package com.tino.base.application

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.tino.base.activity.ActivityManager
import com.tino.base.util.LogUtils
import java.lang.reflect.Method


abstract class BaseApplication : Application() {

    private var moduleApps: MutableList<Application> = mutableListOf()

    companion object {
        private const val TAG = "BaseApplication"
        // 全局共享的 Application
        lateinit var mContext: Context
        lateinit var mActivityManager : ActivityManager

        private fun a() = ::mContext.isInitialized
    }

    private fun baseInit(){
        mActivityManager = ActivityManager.instance()
        LogUtils.Builder(mContext)
    }

    override fun attachBaseContext(context: Context) {
        Log.i(TAG,"BaseApplication  attachBaseContext")
        super.attachBaseContext(context)

        initModuleApplication(context)
    }

    override fun onCreate() {
        Log.i(TAG,"BaseApplication  onCreate")
        super.onCreate()
        moduleApps.forEach { it.onCreate() }
    }

    override fun onLowMemory() {
        Log.i(TAG,"BaseApplication  onLowMemory")
        super.onLowMemory()
        moduleApps.forEach { it.onLowMemory() }
    }

    override fun onTrimMemory(level: Int) {
        Log.i(TAG,"BaseApplication  onTrimMemory")
        super.onTrimMemory(level)
        moduleApps.forEach { it.onTrimMemory(level) }
    }

    override fun onTerminate() {
        Log.i(TAG,"BaseApplication  onTerminate")
        super.onTerminate()
        moduleApps.forEach { it.onTerminate() }
    }

    /**
     * 反射创建组件Application
     * manifest文件合并后，application的meta-data也会合并
     * 通过反射对module中的Application进行Attach
     */
    private fun initModuleApplication(context: Context) {
        if(a()){
            return
        }else{
            mContext = context
            baseInit()
        }
        var info = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
        if (info.metaData == null)
            return
        var apps = info.metaData.keySet()
        apps.forEach {
            Log.i(TAG,"IT=$it")
            try {
                var cla = Class.forName(it.toString())
                var app = cla.newInstance()
                if (app is Application && cla.name != this::class.java.name) {
                    initModuleAppAttach(app)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 反射对组件Application进行初始化调用
     */
    private fun initModuleAppAttach(app: Application) {
        val method: Method? = Application::class.java.getDeclaredMethod("attach", Context::class.java)
        if (method != null) {
            method.isAccessible = true
            method.invoke(app, baseContext)
            moduleApps.add(app)
        }
    }
}