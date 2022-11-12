package com.tino.common.db.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.tino.common.db.bean.Contact


@Dao
interface ContactDao {

    @Insert
    suspend fun insertAll(list:List<Contact>)

    @Query("SELECT * FROM contact ORDER BY contactName COLLATE NOCASE ASC")
    fun getPagingData(): PagingSource<Int, Contact>

    @Query("select 1 from contact where id = id limit 1")
    fun hasContact():Boolean

}