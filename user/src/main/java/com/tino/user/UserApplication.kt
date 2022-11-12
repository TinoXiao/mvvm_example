package com.tino.user

import com.tino.base.application.BaseApplication
import com.tino.common.db.AppDataBase
import com.tino.common.db.dao.UserDao

class UserApplication :BaseApplication() {

    companion object{
        lateinit var userDao : UserDao
    }

    override fun onCreate() {
        super.onCreate()
        userDao = AppDataBase.getInstance(mContext).getUserDao()

    }
}