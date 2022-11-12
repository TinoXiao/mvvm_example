package com.tino.base.eventbus


data class BaseEvent(val what:Int,val obj:Any?) {

    fun handleEvent(what:Int):Any?{
        return if(this.what == what){
            obj
        }else{
            null
        }
    }

}

const val Mes1 = 1
const val Mes2 = 2
const val PERMISSION_CONTACTS = 3