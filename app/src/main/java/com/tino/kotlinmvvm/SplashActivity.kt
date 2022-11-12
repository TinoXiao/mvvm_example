package com.tino.kotlinmvvm

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.launcher.ARouter
import com.tino.base.activity.BaseActivity
import com.tino.base.util.MVUtils
import com.tino.kotlinmvvm.databinding.ActivitySplashBinding
import com.tino.kotlinmvvm.ui.activity.HomeActivity
import com.tino.user.UserConstant
import kotlinx.coroutines.*


/**
 * 欢迎页面
 *
 */
class SplashActivity : BaseActivity() {

     lateinit var binding:ActivitySplashBinding

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

         GlobalScope.launch(Dispatchers.Main) {
             delay(1000)

             if (MVUtils.getInstance().getBoolean(UserConstant.IS_LOGIN)) {
                 jumpActivityFinish(HomeActivity::class.java)
             } else {
                 ARouter.getInstance().build("/User/UserActivity")
                     .navigation()
             }
             finish()
         }
     }
}

