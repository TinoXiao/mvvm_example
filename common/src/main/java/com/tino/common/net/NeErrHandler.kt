package com.tino.common.net

import android.accounts.NetworkErrorException
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.MalformedJsonException
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object NetErrHandler {

    open fun handleE(e:Throwable):BaseError{

        var err = BaseError()
        err.from = e
        when(e){
            is HttpException-> {
                catchHttpException(e,err)
            }
            is SocketTimeoutException -> {
                err.msg = "服务器响应失败"
            }
            is UnknownHostException, is NetworkErrorException -> {
                err.msg = "网络错误"
            }
            is MalformedJsonException, is JsonSyntaxException -> {
                err.msg = "数据解析错误"
            }
            is InterruptedIOException -> {
                err.msg = "服务器连接失败，请稍后重试"
            }
            is ConnectException -> {
                err.msg = "连接服务器失败"
            }
            is BaseError -> {
                return e
            }
            else -> {
                err.msg = "发生异常，请重试"
            }
        }
        return err
    }

    /**
     * 处理网络异常
     */
    private fun catchHttpException(e: HttpException, err :BaseError) {
        val code = e.code()
        if (code in 200 until 300)
            return// 成功code则不处理
        err.code = code
        when (code) {
            in 500..600 -> err.msg = "服务器错误"
            in 400 until 500 -> err.msg = "请求错误"
            else -> err.msg = "网络发生异常"
        }
    }

}

class BaseError(var code:Int = -1,var msg:String,var from:Throwable?): Throwable(msg,from) {
    constructor() : this(-1,"",null)
    constructor(msg:String) : this(-1,msg,null)
    constructor(msg:String,e:Throwable) : this(-1,"",e)
}