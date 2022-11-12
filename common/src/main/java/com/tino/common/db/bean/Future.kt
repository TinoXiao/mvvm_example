package com.tino.common.db.bean

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "future",
    foreignKeys = [ForeignKey(
        entity = Weather::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("wid"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("wid")]
)
data class Future (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var wid: Long = 0,
    var daytime: String? = null,
    var day_weather: String? = null,
    var day_weather_code: String? = null,
    var day_weather_pic: String? = null,
    var day_high_temperature: String? = null,
    var day_wind_direction: String? = null,
    var day_wind_power: String? = null,
    var night_weather: String? = null,
    var night_weather_code: String? = null,
    var night_weather_pic: String? = null,
    var night_low_temperature: String? = null,
    var night_wind_direction: String? = null,
    var night_wind_power: String? = null
)