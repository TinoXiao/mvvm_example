package com.tino.base.activity

import android.app.UiModeManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.tino.base.application.BaseApplication


/**
 * 基础Activity
 *
 */
abstract class BaseActivity : AppCompatActivity() {
    protected var context: AppCompatActivity? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        BaseApplication.mActivityManager.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        BaseApplication.mActivityManager.removeActivity(this)
    }

    protected fun showMsg(msg: CharSequence) {
        if (msg.isNotEmpty())
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    protected fun showLongMsg(msg: CharSequence) {
        if (msg.isNotEmpty())
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    /**
     * 跳转页面
     *
     * @param clazz 目标页面
     */
    protected fun jumpActivity(clazz: Class<*>?) {
        startActivity(Intent(context, clazz))
    }

    /**
     * 跳转页面并关闭当前页面
     *
     * @param clazz 目标页面
     */
    protected fun jumpActivityFinish(clazz: Class<*>?) {
        startActivity(Intent(context, clazz))
        finish()
    }

    protected fun back(toolbar: Toolbar) {
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    protected fun backAndFinish(toolbar: Toolbar) {
        toolbar.setNavigationOnClickListener { finish() }
    }

    /**
     * 退出应用程序
     */
    protected fun exitTheProgram() {
        BaseApplication.mActivityManager.finishAllActivity()
    }

    /**
     * 当前是否在Android11.0及以上
     */
    protected val isAndroid11: Boolean
         get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R

    /**
     * 当前是否在Android10.0及以上
     */
    protected val isAndroid10: Boolean
         get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

    /**
     * 当前是否在Android7.0及以上
     */
    protected val isAndroid7: Boolean
         get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

    /**
     * 当前是否在Android6.0及以上
     */
    protected val isAndroid6: Boolean
         get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    @get:RequiresApi(api = Build.VERSION_CODES.R)
    protected val isStorageManager: Boolean
         get() = Environment.isExternalStorageManager()


    /**
     * 请求外部存储管理 Android11版本时获取文件读写权限时调用 新的方式
     */
    protected fun requestManageExternalStorage(intentActivityResultLauncher: ActivityResultLauncher<Intent?>) {
        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
        intent.setData(Uri.parse("package:" + getPackageName()))
        intentActivityResultLauncher.launch(intent)
    }

    protected val isNight: Boolean
         get() {
            val uiModeManager: UiModeManager =
                context?.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
            return uiModeManager.getNightMode() == UiModeManager.MODE_NIGHT_YES
        }
    private var timeMillis: Long = 0

    /**
     *
     * @param keyCode
     * @param event
     * @return
     */
    fun doubleclickexit(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - timeMillis > 2000) {
                showMsg("再次按下退出应用程序")
                timeMillis = System.currentTimeMillis()
            } else {
                exitTheProgram()
            }
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

}