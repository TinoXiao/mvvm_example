package com.tino.common.db.bean

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
data class User public constructor(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var account: String,
    var pwd: String,
    var nickname: String,
    var introduce: String,
    var login: Boolean,
    var picUrl: String,
):Serializable{
    constructor() : this(0,"","","","",false,"")
    constructor(account: String,pwd: String) : this(0,"","","","",false,"")
    @Ignore
    var pwd2: String = ""
}