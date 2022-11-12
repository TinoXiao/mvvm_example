package com.tino.user.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.template.IProvider
import com.tino.common.db.bean.User
import com.tino.user.UserApplication

@Route(path = "/User/UserProvider")
class UserProvider : IProvider{

    public suspend fun getLoginUser(): User {
        return UserApplication.userDao.getLoginUser()
    }

    override fun init(context: Context?) {

    }
}