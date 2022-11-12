package com.tino.contact

import com.tino.base.application.BaseApplication
import com.tino.common.db.AppDataBase
import com.tino.common.db.dao.ContactDao

class ContactApplication : BaseApplication() {
    companion object{
        lateinit var contactDao: ContactDao
    }

    override fun onCreate() {
        super.onCreate()
        contactDao = AppDataBase.getInstance(mContext).getContactDao()

    }
}