package com.tino.common.db.bean.entity

import com.tino.common.db.bean.Future
import com.tino.common.db.bean.Weather


data class Weathers(var weather: Weather, var futureList: List<Future>){
    constructor():this(Weather(), mutableListOf())
}