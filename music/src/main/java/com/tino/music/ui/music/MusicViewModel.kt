package com.tino.music.ui.music

import com.tino.base.livedata.BaseLiveData
import com.tino.base.viewmodel.BaseViewModel
import com.tino.common.net.NetApi
import com.tino.common.net.NetConstant
import com.tino.music.bean.MusicData
import com.tino.music.net.MusicService



class MusicViewModel : BaseViewModel() {

    val music : BaseLiveData<MusicData> = BaseLiveData()

    fun getMusic(sort:String){
        vLaunchWithLoading {
            val channels = NetApi.createService(MusicService::class.java, NetConstant.HOST4).getRandomMusic(sort)
            music.postValue(channels)
        }
    }
}