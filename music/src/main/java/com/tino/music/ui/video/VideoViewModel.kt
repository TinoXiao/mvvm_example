package com.tino.music.ui.video

import com.tino.base.livedata.BaseLiveData
import com.tino.base.viewmodel.BaseViewModel
import com.tino.common.net.NetApi
import com.tino.common.net.NetConstant
import com.tino.music.bean.Video
import com.tino.music.net.VideoService

class VideoViewModel : BaseViewModel() {

    val video = BaseLiveData<Video>()

    fun getVideo() {
        vLaunch {
            val data = NetApi.createService(VideoService::class.java, NetConstant.HOST5).getVideo()
            video.postValue(data)
        }
    }
}