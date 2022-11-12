package com.tino.base.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.tino.base.error.BaseError
import com.tino.base.livedata.BaseLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    val loading: BaseLiveData<Boolean> = BaseLiveData()
    val failed: BaseLiveData<String> = BaseLiveData()

    fun clearUIState(){

    }

//    /**
//     * 网络请求协程，请求过程异常统一处理
//     */
//    fun vLaunch(
//        block: suspend () -> Unit,
//        error: suspend (Throwable) -> Unit,
//        complete: suspend () -> Unit
//    ) {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                block()
//            } catch (e: Exception) {
//                error(e)
//            } finally {
//                complete()
//            }
//        }
//    }

    fun vLaunch(
        block: suspend () -> Unit,
        final: () ->Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                block()
            } catch (e: Throwable) {
                e.printStackTrace()
                val err = handleThrowable(e)
                failed.postValue(err.msg)
            } finally {
                final()
            }
        }
    }

    fun vLaunch(
        block: suspend () -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                block()
            } catch (e: Throwable) {
                e.printStackTrace()
                val err = handleThrowable(e)
                failed.postValue(err.msg)
            } finally {
            }
        }
    }

    fun vLaunchWithLoading(
        block: suspend () -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            kotlin.runCatching {
                loading.postValue(true)
                block()
            }.onSuccess {
                loading.postValue(false)
            }.onFailure {
                loading.postValue(false)
                it.printStackTrace()
                val err = handleThrowable(it)
                failed.postValue(err.msg)
            }
        }
    }

    private fun handleThrowable(e: Throwable):BaseError{
        var err = BaseError()
        err.from = e

        when(e){
            is BaseError -> {
                return e
            }
            else -> {
                err.msg = "发生异常，请重试"
            }
        }

        return err
    }
}