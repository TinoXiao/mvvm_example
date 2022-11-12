package com.tino.user.repository

import com.tino.base.error.BaseError
import com.tino.base.livedata.BaseLiveData
import com.tino.base.util.MVUtils
import com.tino.common.db.bean.User
import com.tino.user.UserApplication
import com.tino.user.UserConstant


/**
 * 用户数据存储操作类
 */
class UserRepository {

    /**
     * 登录检查
     * @param user
     */
    suspend fun checkUser(user: User, loginSuccess: BaseLiveData<Boolean>) {
        try {
            if (user.account.isEmpty()) {
                throw BaseError("请输入账号")
                return
            }
            if (user.pwd.isEmpty()) {
                throw BaseError("请输入密码")
                return
            }
            var localUser = UserApplication.userDao.getUser(user.account)
            if (localUser!=null) {
                if (localUser.account!=user.account || localUser.pwd!=user.pwd) {
                    throw BaseError("账号或密码错误")
                } else {
                    //更新数据库登录状态
                    UserApplication.userDao.updateLogin()
                    UserApplication.userDao.updateLoginById(localUser.id)
                    //记录登录成功信息
                    MVUtils.getInstance().put(UserConstant.IS_LOGIN, true)
                    loginSuccess.postValue(true)
                }
            } else {
                throw BaseError("该账号还未注册")
            }
        }catch (e:Exception){
            throw BaseError("登录发生错误",e)
        }

    }

    /**
     * 用户注册
     * @param user
     */
    suspend fun registerUser(user: User,success: BaseLiveData<Boolean>) {
        try {
            if (user.account.isEmpty()) {
                throw BaseError("请输入账号")
                return
            }
            if (user.pwd.isEmpty()) {
                throw BaseError("请输入密码")
                return
            }
            if (user.pwd2.isEmpty()) {
                throw BaseError("请确认密码")
                return
            }
            if (user.pwd != user.pwd2) {
                throw BaseError("两次输入密码不一致")
                return
            }
            var localUser = UserApplication.userDao.getUser(user.account)
            if (localUser!=null) {
                throw BaseError("该用户已经注册")
            } else {
                UserApplication.userDao.insertUser(user)
                success.postValue(true)
            }
        }catch (e:Exception){
            throw BaseError("注册发生错误",e)
        }
    }

    suspend fun getLoginUser(): User {
        return UserApplication.userDao.getLoginUser()
    }

}