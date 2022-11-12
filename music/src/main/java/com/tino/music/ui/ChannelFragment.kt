package com.tino.music.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.tino.common.fragment.NavFragment
import com.tino.music.R
import com.tino.music.databinding.MusicFragmentChannelBinding
import com.tino.music.ui.music.MusicFragment
import com.tino.music.ui.video.VideoFragment


class ChannelFragment : NavFragment() {

    var binding : MusicFragmentChannelBinding? = null
//    private var adapter: ChannelAdapter? = null
    private val tabList = listOf("音乐","视频")
    private val fragmentList = listOf(MusicFragment(),VideoFragment())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.music_fragment_channel,container,false)
        binding?.lifecycleOwner = this

        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        binding?.musicVp2?.offscreenPageLimit = fragmentList.size-1
        binding?.musicVp2?.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return fragmentList.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragmentList[position]
            }
        }

        TabLayoutMediator(binding?.musicTab!!, binding?.musicVp2!!) { tab, positon->
            tab.text = tabList[positon]
        }.attach()
        
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}