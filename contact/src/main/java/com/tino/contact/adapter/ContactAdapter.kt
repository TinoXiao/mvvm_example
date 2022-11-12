/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tino.contact.adapter

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tino.base.application.BaseApplication
import com.tino.base.util.ToastUtil.toast
import com.tino.common.db.bean.Contact
import com.tino.contact.R
import com.tino.contact.databinding.ContactItemContactBinding


class ContactAdapter :
    PagingDataAdapter<Contact, ContactAdapter.ContactViewHolder>(diffCallback) {

    class ContactViewHolder(var binding: ContactItemContactBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem.contactName == newItem.contactName
            }
            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = getItem(position)
        holder.binding.itemdata=contact
        holder.binding.contactCall.setOnClickListener{
            call(contact?.phoneNumber)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {

        val binding: ContactItemContactBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.contact_item_contact,
            parent,
            false
        )
        return ContactViewHolder(binding)

    }

    private fun call(no:String?){
        try {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.data = Uri.parse("tel:$no")
            BaseApplication.mContext.startActivity(intent)
        }catch (e:Exception){
            "拨号失败".toast()
        }
    }
}

