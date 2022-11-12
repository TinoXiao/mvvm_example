package com.tino.weather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tino.common.db.bean.Future
import com.tino.weather.R
import com.tino.weather.databinding.WeatherItemFutureBinding

class FutureAdapter(fragment: Context, mDatas: List<Future>)
    : RecyclerView.Adapter<FutureAdapter.ViewHolder>() {
    private val mDatas: List<Future>
    private val mFragment: Context

    class ViewHolder(val mBinding: WeatherItemFutureBinding) : RecyclerView.ViewHolder(
        mBinding.root
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: WeatherItemFutureBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
            R.layout.weather_item_future,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val future: Future = mDatas[position]
        holder.mBinding.itemdata=future
        Glide.with(mFragment).load(future.day_weather_pic).into(holder.mBinding.ivWeather)
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    init {
        this.mDatas = mDatas
        this.mFragment = fragment;
    }
}