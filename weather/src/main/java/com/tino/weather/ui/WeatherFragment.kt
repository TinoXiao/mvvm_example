package com.tino.weather.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tino.common.fragment.NavFragment
import com.tino.weather.R
import com.tino.weather.databinding.WeatherFragmentWeatherBinding

class WeatherFragment : NavFragment() {

    private val mViewModel: WeatherViewModel by activityViewModels<WeatherViewModel> {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return WeatherViewModel("深圳") as T
            }
        }
    }


    var binding: WeatherFragmentWeatherBinding? = null
    var city = "深圳"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.weather_fragment_weather, container, false)
        binding?.lifecycleOwner = viewLifecycleOwner
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding?.viewmodel = mViewModel
        addObserve()
    }

    private fun addObserve() {

        requireActivity().title = "天气"

        binding?.btWeather?.setOnClickListener {
            mViewModel.getNewestWeather(city)
        }
        binding?.btFuture?.setOnClickListener {
            var bundle = Bundle()
            bundle.putSerializable("weather",mViewModel.weathersMutableLiveData.value!!.weather)
            navigation(R.id.weather_action_weather_weatherfragment_to_weather_futurefragment,bundle);
        }
        mViewModel.loading.observe(viewLifecycleOwner){ aBoolean ->
            if (aBoolean) {
                showLoading("正在加载数据")
            }else{
                dismissLoading()
            }
        }
        mViewModel.failed.observe(viewLifecycleOwner) { s ->
            dismissLoading()
            showMsg(s)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding=null
    }

}