package com.tino.common.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tino.common.db.bean.Future
import com.tino.common.db.bean.Weather
import com.tino.common.db.bean.User
import com.tino.common.db.bean.Contact
import com.tino.common.db.dao.ContactDao
import com.tino.common.db.dao.FutureDao
import com.tino.common.db.dao.UserDao
import com.tino.common.db.dao.WeatherDao


@Database(
    entities = [Weather::class, Future::class, User::class, Contact::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao
    abstract fun getFutureDao(): FutureDao
    abstract fun getUserDao(): UserDao
    abstract fun getContactDao(): ContactDao

    companion object {
        private const val DATABASE_NAME = "MVVM"
        /**
         * 单例模式
         */
        @Volatile private var INSTANCE: AppDataBase? = null
        fun getInstance(context: Context): AppDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        AppDataBase::class.java,
                        DATABASE_NAME
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }

//        /**
//         * 版本升级迁移到6 在数据库中新增一个笔记表
//         */
//        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                //todo 数据库升级操作
//            }
//        }
    }
}