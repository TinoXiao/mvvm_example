package com.tino.contact.viewmodel

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.tino.base.application.BaseApplication
import com.tino.base.error.BaseError
import com.tino.base.livedata.BaseLiveData
import com.tino.base.viewmodel.BaseViewModel
import com.tino.common.db.bean.Contact
import com.tino.contact.ContactApplication
import com.tino.contact.repository.ContactRepository
import kotlinx.coroutines.flow.Flow


class ContactViewModel : BaseViewModel() {

    var hasPermission: BaseLiveData<Boolean>  = BaseLiveData()
    private val contactRepository: ContactRepository by lazy {
        ContactRepository()
    }

    val pagingData: Flow<PagingData<Contact>> by lazy {
        Pager(
            config = PagingConfig(
                pageSize = 15,
                enablePlaceholders = true,
                maxSize = 50
            )
        ){ contactRepository.getPagingContact() }.flow.cachedIn(viewModelScope).also {
            getContacts()
        }
    }


    /**
     * 获取通讯录数据
     * 1、从数据库获取
     * 2、实时获取
     */
    private fun getContacts(){
        vLaunch {
            val has = contactRepository.has()
            if(!has){
                Log.e("AAA","本地无数据")
                if(PackageManager.PERMISSION_GRANTED ==
                    BaseApplication.mContext.checkCallingOrSelfPermission
                        (Manifest.permission.READ_CONTACTS)){
                    Log.e("AAA","有权限，开始获取数据")
                    getNewContact()
                }else{
                    Log.e("AAA","无权限，开始申请权限")
                    hasPermission.postValue(false)
                }
            }
        }
    }

    fun getNewContact(){

        vLaunchWithLoading {
            var newlist = contactRepository.getAllContactsFromPhone()
            if(newlist.isNotEmpty()){
                contactRepository.insertContacts(newlist)
            }else{
                throw BaseError("无通讯录信息")
            }
        }

    }

}