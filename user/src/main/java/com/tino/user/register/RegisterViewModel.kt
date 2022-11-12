package com.tino.user.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tino.base.livedata.BaseLiveData
import com.tino.base.viewmodel.BaseViewModel
import com.tino.common.db.bean.User
import com.tino.user.repository.UserRepository
import kotlinx.coroutines.delay

class RegisterViewModel : BaseViewModel() {

    private val userRepository: UserRepository by lazy {
        UserRepository()
    }
    val user: MutableLiveData<User> by lazy {
        MutableLiveData(User())
    }
    var success: BaseLiveData<Boolean> = BaseLiveData()

    /**
     * 注册
     */
    fun register() {
        vLaunchWithLoading {
            userRepository.registerUser(user.value!!,success)
        }
    }
}