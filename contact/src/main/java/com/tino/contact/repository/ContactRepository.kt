package com.tino.contact.repository

import android.database.Cursor
import android.provider.ContactsContract
import androidx.paging.PagingSource
import com.tino.base.application.BaseApplication
import com.tino.common.db.bean.Contact
import com.tino.contact.ContactApplication

class ContactRepository {

    suspend fun has():Boolean{
        return ContactApplication.contactDao.hasContact()
    }

    fun getPagingContact(): PagingSource<Int, Contact> {
        return ContactApplication.contactDao.getPagingData()
    }

    suspend fun insertContacts(list:List<Contact>){
        ContactApplication.contactDao.insertAll(list)
    }

    /**
     * 获取所有手机里的通讯录
     */
    fun getAllContactsFromPhone(): List<Contact> {
        val contactList: MutableList<Contact> = ArrayList<Contact>()
        try {
            val PHONES_PROJECTION = arrayOf(
                ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.CUSTOM_RINGTONE
            )
            val resolver = BaseApplication.mContext.contentResolver
            // 获取手机联系人
            val phoneCursor: Cursor? = resolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                PHONES_PROJECTION, ContactsContract.CommonDataKinds.Phone.MIMETYPE + "='"
                        + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'", null, null
            )
            if (phoneCursor != null) {
                while (phoneCursor.moveToNext()) {
                    // 得到手机号码
                    var index = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    if(index!=-1){
                        val phoneNumber: String = phoneCursor.getString(index)
                        if (phoneNumber != null) {

                            val model = Contact()
                            model.phoneNumber=phoneNumber

                            index = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID)
                            if(index!=-1){
                                val id = phoneCursor.getString(index)
                                model.rawId=id
                            }

                            index = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
                            if(index!=-1){
                                val contactId = phoneCursor.getString(index)
                                model.contactId=contactId
                            }

                            index = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                            if(index!=-1){
                                val contactName = phoneCursor.getString(index)
                                model.contactName=contactName?:""
                            }

                            index = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CUSTOM_RINGTONE)
                            if(index!=-1){
                                val customRingtone = phoneCursor.getString(index)
                                model.customRingtone=customRingtone
                            }

                            contactList.add(model)
                        }
                    }
                }
                phoneCursor.close()
            }
        }catch (e:Exception){
            e.printStackTrace()
        }finally {
            return contactList
        }
    }

}