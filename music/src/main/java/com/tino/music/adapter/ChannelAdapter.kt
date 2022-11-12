package com.tino.music.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tino.music.R
import com.tino.music.databinding.MusicItemChannelsBinding
import com.tino.music.interfaces.IChannelListener


class ChannelAdapter(mDatas: List<String>?)
: RecyclerView.Adapter<ChannelAdapter.ViewHolder>() {

    private val mDatas : List<String>?
    private lateinit var mListener : IChannelListener


    fun setOnItemListener(listener : IChannelListener){
        this.mListener = listener
    }

    class ViewHolder(val mBinding: MusicItemChannelsBinding) : RecyclerView.ViewHolder(
        mBinding.root
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: MusicItemChannelsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.music_item_channels,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mBinding.itemdata = mDatas?.get(position)
        holder.mBinding.tvChannel.setOnClickListener{
            if(mListener!=null){
                mListener.onItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return mDatas?.size ?: 0
    }

    init {
        this.mDatas = mDatas
    }
}