package com.tino.weather.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.tino.common.db.bean.Weather
import com.tino.common.fragment.NavFragment
import com.tino.weather.R
import com.tino.weather.adapter.FutureAdapter
import com.tino.weather.databinding.WeatherFragmentFutureBinding

class FutureFragment : NavFragment() {

    val mViewModel: FutureViewModel by viewModels()

    var binding: WeatherFragmentFutureBinding? = null

    lateinit var adapter: FutureAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.weather_fragment_future, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding?.viewmodel = mViewModel
        addObserve()
        initData()
    }

    private fun addObserve() {
        mViewModel.loading.observe(viewLifecycleOwner){ aBoolean ->
            if (aBoolean) {
                showLoading("正在加载数据")
            }else{
                dismissLoading()
            }
        }
        mViewModel.failed.observe(viewLifecycleOwner,){
            showMsg(it)
        }

        mViewModel.weathersMutableLiveData.observe(viewLifecycleOwner) {
            if (it != null && it.futureList!!.isNotEmpty()) {
                adapter = FutureAdapter(this.requireContext(),it.futureList!!)
                binding?.rvFuture?.adapter = adapter
            }
        }

    }

    private fun initData() {

        if(arguments!=null){
            val w = arguments?.get("weather") as Weather
            mViewModel.getFuture(w)
            requireActivity().title = "${w.city}一周天气"
        }else{
            Log.e("AAA","没有天气数据")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("AAA","FutureFragment onDestroyView")
        binding = null
    }

}