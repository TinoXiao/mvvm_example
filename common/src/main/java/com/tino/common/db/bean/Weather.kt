package com.tino.common.db.bean

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "weather")
data class Weather(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,
    var sd: String? = null,
    var aqi: String? = null,
    var weather: String? = null,
    var weather_pic: String? = null,
    var temperature: String? = null,
    var wind_direction: String? = null,
    var wind_power: String? = null,
    var city: String? = null,
    var province: String? = null,
    var areaId: String? = null,
    var last_cache: String? = null
):Serializable{
    constructor():this(0,"","","","","","","","","","","")
}