package com.tino.user

import android.os.Bundle
import android.view.KeyEvent
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.tino.base.activity.BaseActivity
import com.tino.base.application.BaseApplication
import com.tino.user.databinding.UserActivityUserBinding

@Route(path = "/User/UserActivity")
class UserActivity : BaseActivity() {

    lateinit var binding : UserActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.user_activity_user)
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return super.doubleclickexit(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        BaseApplication.mActivityManager.removeActivity(this)
    }
}