package com.tino.user.login

import androidx.lifecycle.MutableLiveData
import com.tino.base.livedata.BaseLiveData
import com.tino.base.viewmodel.BaseViewModel
import com.tino.common.db.bean.User
import com.tino.user.repository.UserRepository



class LoginViewModel : BaseViewModel() {

    private val userRepository: UserRepository by lazy {
        UserRepository()
    }
    val user: MutableLiveData<User> by lazy {
        MutableLiveData(User())
    }
    val loginSuccess: BaseLiveData<Boolean> =BaseLiveData()

    /**
     * 登录
     */
    fun login() {
        vLaunchWithLoading {
            userRepository.checkUser(user.value!!,loginSuccess)
        }
    }

}