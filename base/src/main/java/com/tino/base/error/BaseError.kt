package com.tino.base.error

class BaseError(var code:Int = -1,var msg:String,var from:Throwable?): Throwable(msg,from) {
    constructor() : this(-1,"",null)
    constructor(msg:String) : this(-1,msg,null)
    constructor(msg:String,e:Throwable) : this(-1,"",e)
}