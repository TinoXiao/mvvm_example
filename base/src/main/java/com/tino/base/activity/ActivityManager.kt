package com.tino.base.activity

import android.app.Activity
import java.util.ArrayList

/**
 * Activity栈管理
 */
class ActivityManager {

    companion object {
        private lateinit var mInstance: ActivityManager
        @Synchronized
        fun instance(): ActivityManager {
            if (!::mInstance.isInitialized)
                mInstance = ActivityManager()
            return mInstance
        }
    }

    /**
     * 保存所有创建的Activity
     */
    private val activityList: MutableList<Activity> = ArrayList()

    /**
     * 添加Activity
     * @param activity
     */
    fun addActivity(activity: Activity?) {
        if (activity != null) {
            activityList.add(activity)
        }
    }

    /**
     * 移除Activity
     * @param activity
     */
    fun removeActivity(activity: Activity?) {
        if (activity != null) {
            activityList.remove(activity)
        }
    }

    /**
     * 关闭所有Activity
     */
    fun finishAllActivity() {
        for (activity in activityList) {
            activity.finish()
        }
    }


}