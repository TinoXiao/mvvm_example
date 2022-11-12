package com.tino.music.ui.video

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.player.IjkPlayerManager
import com.shuyu.gsyvideoplayer.player.PlayerFactory
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.GSYADVideoPlayer
import com.tino.music.R
import com.tino.music.databinding.VideoFragmentVideoBinding


class VideoFragment : Fragment() {

    private var binding: VideoFragmentVideoBinding? = null
    private lateinit var viewModel: VideoViewModel
    private lateinit var mediaController: MediaController
    private lateinit var orientationUtils :OrientationUtils
    private lateinit var player : GSYADVideoPlayer
    private var hasLoad = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("AAA","video onCreateView")
        binding = DataBindingUtil.inflate(inflater, R.layout.video_fragment_video,container,false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("AAA","video onActivityCreated")
        viewModel = ViewModelProvider(this)[VideoViewModel::class.java]
        initView()
        addObserve()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("AAA","video onViewCreated")
    }

    private fun initView() {
        setUpPlayer()
    }

    private fun initData() {
        if(!hasLoad){
            viewModel.getVideo()
            hasLoad = true
        }
    }

    private fun reLoad(){
        viewModel.getVideo()
    }

    private fun addObserve() {
        viewModel.video.observe(viewLifecycleOwner){
            if(it.mp4!=null){
                val url = "https:"+it.mp4
                Log.e("AAA","url=$url")

                player.setUp(url,true,"")
                player.startPlayLogic()
            }
        }
    }

    /**
     * 初始化视频播放器
     */
    private fun setUpPlayer(){
        player = binding?.detailPlayer!!
        orientationUtils = OrientationUtils(activity,binding?.detailPlayer)
        orientationUtils.isEnable = false

        binding?.detailPlayer?.fullscreenButton?.setOnClickListener {
//            orientationUtils.resolveByClick()
//            binding?.detailPlayer?.startWindowFullscreen(context,true,true)
        }
        player.isAutoFullWithSize = false

        PlayerFactory.setPlayManager(IjkPlayerManager::class.java)

        val builder = GSYVideoOptionBuilder()
        builder.setIsTouchWiget(true)
            .setRotateViewAuto(false)
            .setLockLand(false)
            .setCacheWithPlay(true)
            .setAutoFullWithSize(true)
            .setShowFullAnimation(false)
            .setNeedLockFull(true)
//                    .setVideoTitle("美女")
            .setVideoAllCallBack(object : GSYSampleCallBack() {
                override fun onPrepared(url: String?, vararg objects: Any?) {
                    super.onPrepared(url, *objects)
                    Log.e("AAA","onPrepared")
                }

                override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                    super.onQuitFullscreen(url, *objects)
                }

                override fun onAutoComplete(url: String?, vararg objects: Any?) {
                    super.onAutoComplete(url, *objects)
                    reLoad()
                }
            }).setLockClickListener { view, lock ->
                if(orientationUtils!=null)
                    orientationUtils.isEnable = !lock
            }.build(player)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("AAA","video onDestroyView")
        binding = null
        player.release()
    }

    override fun onPause() {
        super.onPause()
        player.onVideoPause()
        Log.e("AAA","video onPause")
    }

    override fun onResume() {
        super.onResume()
        initData()
        player.onVideoResume(false)
        requireActivity().title = "视频"
        Log.e("AAA","video onResume")
    }
}