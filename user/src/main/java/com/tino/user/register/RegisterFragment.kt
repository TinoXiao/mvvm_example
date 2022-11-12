package com.tino.user.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.tino.base.fragment.BaseFragment
import com.tino.user.R
import com.tino.user.databinding.UserFragmentRegisterBinding

class RegisterFragment : BaseFragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var viewModel: RegisterViewModel
    private var binding:UserFragmentRegisterBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.user_fragment_register, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        binding?.viewModel = viewModel
        initView()
        addObserve()
    }

    private fun initView() {
        binding?.toolbar?.setNavigationIcon(android.R.drawable.ic_menu_revert)
        binding?.toolbar?.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun addObserve() {
        binding?.btnRegister?.setOnClickListener{
            viewModel.register()
        }
        viewModel.failed.observe(viewLifecycleOwner){
            showMsg(it)
        }
        viewModel.loading.observe(viewLifecycleOwner){
            if(it){
                showLoading("正在注册")
            }else{
                dismissLoading()
            }
        }
        viewModel.success.observe(viewLifecycleOwner){
            if(it){
                showMsg("注册成功")
                activity?.onBackPressed()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}