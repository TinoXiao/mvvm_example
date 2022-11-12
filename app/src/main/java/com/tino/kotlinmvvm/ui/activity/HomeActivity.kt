package com.tino.kotlinmvvm.ui.activity

import android.os.Bundle
import android.view.KeyEvent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.tino.base.util.MVUtils
import com.tino.base.util.ToastUtil.toast
import com.tino.common.activity.NavActivity
import com.tino.common.net.NetSet
import com.tino.kotlinmvvm.R
import com.tino.kotlinmvvm.databinding.ActivityHomeBinding
import com.tino.user.UserConstant.Companion.IS_LOGIN

@Route(path = "/Home/HomeActivity")
class HomeActivity : NavActivity() {

    lateinit var binding: ActivityHomeBinding
    lateinit var viewModel: HomeViewModel

    override fun controllerId(): Int {
        return R.id.nav_host_fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NetSet.handleSSLHandshake()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding.viewModel = viewModel
        addObserve()
        initView()
        initData()

    }


    private fun initData() {
        viewModel.getLoginUser()
    }

    private fun addObserve() {
        viewModel.user.observe(this, Observer{
//            Glide.with(this).load(it.picUrl).into(binding.ivAvatar)
        })
    }

    /**
     * 初始化
     */
    private fun initView() {

        setSupportActionBar(binding.toolbar)

        val navController = Navigation.findNavController(this,R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)

        val appBarConfiguration = AppBarConfiguration.Builder(
            com.tino.weather.R.id.weather_weatherfragment,
            com.tino.contact.R.id.contact_contactfragment,
            com.tino.music.R.id.music_channelfragment,
        ).setDrawerLayout(binding.drawerLayout).build()
        NavigationUI.setupWithNavController(binding.toolbar,navController,appBarConfiguration)

        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_about -> {
                    "jetpack全家桶，kotlin实战".toast()
                }
                R.id.item_logout -> logout()
                else -> {}
            }
            true
        }
    }

    /**
     * 退出登录
     */
    private fun logout() {
        showMsg("退出登录")
        MVUtils.getInstance()!!.put(IS_LOGIN, false)
        ARouter.getInstance().build("/User/UserActivity")
            .navigation()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return super.doubleclickexit(keyCode, event)
    }

}