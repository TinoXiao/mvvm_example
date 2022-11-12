package com.tino.music.ui.music

import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.tino.base.util.PlayerUtil
import com.tino.base.util.ToastUtil.toast
import com.tino.common.fragment.NavFragment
import com.tino.music.R
import com.tino.music.bean.Music
import com.tino.music.databinding.MusicFragmentMusicBinding
import java.util.*


class MusicFragment : NavFragment() {

    var binding : MusicFragmentMusicBinding? = null

    private lateinit var viewModel: MusicViewModel
    private lateinit var currentSong : Music
    private var playing = false
    private var timer : Timer = Timer()
    private var title = "音乐"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("AAA","MusicFragment onCreateView")
        binding = DataBindingUtil.inflate(inflater, R.layout.music_fragment_music,container,false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("AAA","MusicFragment onActivityCreated")
        viewModel = ViewModelProvider(this)[MusicViewModel::class.java]
        binding?.viewmodel = viewModel
        addObserve()
        initData()
    }

    private fun initData() {
//        val channel = arguments?.getSerializable("Channel").toString()
        viewModel.getMusic("热歌榜")
    }

    private fun addObserve() {

        PlayerUtil.getInstance().setPreparedListener {
            it.start()
            setState(true)
        }

        PlayerUtil.getInstance().setCompletionListener {
            setState(false)
        }

        PlayerUtil.getInstance().setLoadListener { mp, percent ->
            binding?.sbProgress?.secondaryProgress = percent
        }

        viewModel.loading.observe(viewLifecycleOwner){
            if(it){
                showLoading("正在加载")
            }else{
                dismissLoading()
            }
        }

        viewModel.failed.observe(viewLifecycleOwner){
            it.toast()
        }

        viewModel.music.observe(viewLifecycleOwner){
            currentSong = it.info
            getContext()?.let { it1 -> Glide.with(it1).load(currentSong.picUrl).into(binding!!.ivAlbum) }
            startMusic()
            title = currentSong.name +"-"+ currentSong.auther
            setTitle()
        }

        binding?.sbProgress?.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.progress?.let { PlayerUtil.getInstance().seek(it) }
            }

        })

        binding?.btNext?.setOnClickListener{
            initData()
        }

        binding?.btPlayorpause?.setOnClickListener{
            btnClick(!playing)
        }
    }

    private fun setTitle(){
        requireActivity().title = title
    }

    /**
     * 切换播放状态
     * @param play Boolean true开始播放，false暂停
     */
    private fun btnClick(play:Boolean){
        if(play&&!play){
            PlayerUtil.getInstance().Play()
            setState(!playing)
            return
        }

        if(!play&&playing){
            PlayerUtil.getInstance().Pause()
            setState(!playing)
            return
        }
    }

    private fun setState(flag:Boolean){
        playing = flag
        if(flag){
            binding?.btPlayorpause?.setBackgroundResource(android.R.drawable.ic_media_pause)
            timer = Timer()
            timer.schedule(object :TimerTask(){
                override fun run() {
                    try {
                        val pro = PlayerUtil.getInstance().progress
                        val max = PlayerUtil.getInstance().max
                        if(max!=0){
                            binding?.sbProgress?.progress = pro*100/max
                        }
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }
            },0,1000)

        }else{
            binding?.btPlayorpause?.setBackgroundResource(android.R.drawable.ic_media_play)
            if(timer!=null){
                timer.cancel()
            }
        }
    }

    private fun startMusic() {
        PlayerUtil.getInstance().Load(currentSong.mp3url)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("AAA","music onDestroyView")
        PlayerUtil.getInstance().Stop()
        timer.cancel()
        binding = null
    }

    override fun onPause() {
        super.onPause()
        Log.e("AAA","MusicFragment onPause")
        btnClick(false)
    }

    override fun onResume() {
        super.onResume()
        Log.e("AAA","MusicFragment onResume")
        btnClick(true)
        setTitle()
    }
}