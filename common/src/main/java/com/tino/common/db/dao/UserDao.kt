package com.tino.common.db.dao

import androidx.room.*
import com.tino.common.db.bean.User
import io.reactivex.Single

@Dao
interface UserDao {
    @Delete
    fun delete(user: User)

    @Insert
    suspend fun insertUser(user: User)

    /**
     * 插入后返回id
     * @param user
     * @return
     */
    @Insert
    fun insertUser2(user: User): Single<Long>

    @Update
    fun updateUser(user: User)

    @Query("update user set login = 1 where id=:id")
    suspend fun updateLoginById(id: Long)

    @Query("update user set login = 0")
    suspend fun updateLogin()

    @Query("select * from user")
    fun getAllUser(): List<User>

    @Query("select * from user where id=:id")
    fun getUser(id: Int): List<User>

    @Query("select distinct * from user where account=:account")
    suspend fun getUser(account: String): User?

    @Query("select * from user where login = 1")
    suspend fun getLoginUser(): User

}