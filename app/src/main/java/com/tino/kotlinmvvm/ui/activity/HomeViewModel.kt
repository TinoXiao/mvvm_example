package com.tino.kotlinmvvm.ui.activity


import com.alibaba.android.arouter.launcher.ARouter
import com.tino.base.livedata.BaseLiveData
import com.tino.base.viewmodel.BaseViewModel
import com.tino.common.db.bean.User
import com.tino.user.provider.UserProvider


/**
 * HomeViewModel
 */
class HomeViewModel : BaseViewModel() {

    var user: BaseLiveData<User> = BaseLiveData(User())

    fun getLoginUser(){
        vLaunch {
            user.postValue(ARouter.getInstance().navigation(UserProvider::class.java).getLoginUser())
        }
    }

}