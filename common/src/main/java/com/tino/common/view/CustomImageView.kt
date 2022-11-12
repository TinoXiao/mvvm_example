package com.tino.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.google.android.material.imageview.ShapeableImageView
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.Glide
import androidx.databinding.BindingAdapter

/**
 * 自定义View
 *
 */
class CustomImageView(context: Context?, attrs: AttributeSet?) :
    ShapeableImageView(context, attrs) {

    companion object {

        private val OPTIONS = RequestOptions()
            .placeholder(android.R.drawable.ic_menu_gallery) //图片加载出来前，显示的图片
            .fallback(android.R.drawable.ic_menu_report_image) //url为空的时候,显示的图片
            .error(android.R.drawable.ic_menu_report_image) //图片加载失败后，显示的图片
        private val OPTIONS_LOCAL = RequestOptions()
            .placeholder(android.R.drawable.ic_menu_gallery) //图片加载出来前，显示的图片
            .fallback(android.R.drawable.ic_menu_report_image) //url为空的时候,显示的图片
            .error(android.R.drawable.ic_menu_report_image) //图片加载失败后，显示的图片
            .diskCacheStrategy(DiskCacheStrategy.NONE) //不做磁盘缓存
            .skipMemoryCache(true)

        /**
         * 本地地址图片
         *
         * @param imageView 图片视图
         * @param url       本地url
         */
        @BindingAdapter(value = ["localUrl"], requireAll = false)
        @JvmStatic fun loadLocal(imageView: ImageView, url: String?) {
            if(url!=null && url.isNotEmpty()){
                Glide.with(imageView.context).load(url).apply(OPTIONS_LOCAL).into(imageView)
            }
        }


        /**
         * 普通网络地址图片
         *
         * @param imageView 图片视图
         * @param url       网络url
         */
        @BindingAdapter(value = ["networkUrl"], requireAll = false)
        @JvmStatic fun loadNetUrl(imageView: ImageView, url: String) {
            if(url!=null && url.isNotEmpty())
                Glide.with(imageView.context).load(url).apply(OPTIONS).into(imageView)
        }
    }


}