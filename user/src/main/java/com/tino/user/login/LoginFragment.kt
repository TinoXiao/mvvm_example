package com.tino.user.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alibaba.android.arouter.launcher.ARouter
import com.tino.base.fragment.BaseFragment
import com.tino.user.R
import com.tino.user.databinding.UserFragmentLoginBinding

class LoginFragment : BaseFragment() {

    private lateinit var viewModel: LoginViewModel
    private var binding: UserFragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.user_fragment_login, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding?.viewModel = viewModel
        initView()
        addObserve()
    }

    private fun initView() {

    }

    private fun addObserve() {
        binding?.btnLogin?.setOnClickListener{
            viewModel.login()
        }
        binding?.tvRegisiter?.setOnClickListener{
            val navController: NavController =
                Navigation.findNavController(requireActivity(), R.id.nav_user_fragment)
            navController.navigate(R.id.user_action_user_loginfragment_to_user_registerfragment)
        }
        viewModel.failed.observe(viewLifecycleOwner){
            showMsg(it)
        }
        viewModel.loading.observe(viewLifecycleOwner){
            loading(it)
        }
        viewModel.loginSuccess.observe(viewLifecycleOwner){
            if(it){
                ARouter.getInstance().build("/Home/HomeActivity")
                    .navigation()
                activity?.finish()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}