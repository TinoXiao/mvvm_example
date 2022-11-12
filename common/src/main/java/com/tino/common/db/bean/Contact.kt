package com.tino.common.db.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class Contact public constructor(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    var rawId:String?,
    var contactId:String?,
    var contactName:String,
    var phoneNumber:String?,
    var customRingtone:String?
    ){
    constructor():this(0,"","","","","")
}