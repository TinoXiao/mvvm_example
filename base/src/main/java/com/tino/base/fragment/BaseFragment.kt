package com.tino.base.fragment

import android.app.Dialog
import android.app.ProgressDialog
import android.app.UiModeManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tino.base.util.ToastUtil.toast

/**
 * 基础Fragment
 *
 */
abstract class BaseFragment :  Fragment() {
    protected var context: AppCompatActivity? = null
    private lateinit var loadingDialog: ProgressDialog

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AppCompatActivity) {
            this.context = context
            loadingDialog = ProgressDialog(context)
        }
    }

    override fun onDetach() {
        super.onDetach()
        context = null
    }

    protected fun showMsg(msg: String?) {
        if (msg != null && msg.isNotEmpty())
            msg.toast()
    }

    protected fun loading(isShow:Boolean){
        if(isShow)
            showLoading("")
        else
            dismissLoading()
    }

    /**
     * 显示加载弹窗
     */
    protected fun showLoading(title:String) {
        if(!loadingDialog.isShowing){
            loadingDialog.setTitle(title)
            loadingDialog.show()
        }
    }

    /**
     * 隐藏加载弹窗
     */
    protected fun dismissLoading() {
        if (loadingDialog != null&&loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
    }

    protected val isNight: Boolean
        protected get() {
            val uiModeManager: UiModeManager =
                requireContext().getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
            return uiModeManager.nightMode == UiModeManager.MODE_NIGHT_YES
        }
}