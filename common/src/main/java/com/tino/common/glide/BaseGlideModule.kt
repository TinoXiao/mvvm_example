package com.tino.common.glide

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@GlideModule(glideName = "GlideApp")
class BaseGlideModule: AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)

        val options: RequestOptions = RequestOptions()
             .placeholder(android.R.drawable.stat_notify_error)
             .error(android.R.drawable.stat_notify_error)
             .centerInside()

        builder.setDefaultRequestOptions(options)

    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}