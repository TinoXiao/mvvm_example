package com.tino.base.util

import android.widget.Toast
import com.tino.base.application.BaseApplication

object ToastUtil {

    fun String.toast(): Toast {
        return Toast.makeText(BaseApplication.mContext, this.toString(), Toast.LENGTH_SHORT)
            .apply { show() }
    }

    fun String.toastLong() : Toast {
        return Toast.makeText(BaseApplication.mContext, this.toString(), Toast.LENGTH_LONG)
            .apply { show() }
    }
}