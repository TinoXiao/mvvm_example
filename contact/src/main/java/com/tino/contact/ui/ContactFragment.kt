package com.tino.contact.ui


import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.permissionx.guolindev.PermissionX
import com.tino.base.activity.BaseActivity
import com.tino.base.eventbus.BaseEvent
import com.tino.base.eventbus.FlowEventBus
import com.tino.base.eventbus.PERMISSION_CONTACTS
import com.tino.base.fragment.BaseFragment
import com.tino.base.util.ToastUtil.toast
import com.tino.contact.R
import com.tino.contact.adapter.ContactAdapter
import com.tino.contact.databinding.ContactFragmentContactBinding
import com.tino.contact.viewmodel.ContactViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ContactFragment : BaseFragment() {

    private lateinit var viewModel: ContactViewModel
    private var binding: ContactFragmentContactBinding? = null
    private var adapter: ContactAdapter? = ContactAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.contact_fragment_contact, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ContactViewModel::class.java]
        binding?.viewmodel = viewModel

        binding?.rvMail?.adapter = adapter

        addObserver()
    }

    private fun addObserver() {

        lifecycleScope.launch {
            viewModel.pagingData?.collectLatest {
                adapter?.submitData(it)
            }
        }

        viewModel.hasPermission.observe(viewLifecycleOwner, Observer {
            if(!it) checkPermission()
        })
        viewModel.failed.observe(viewLifecycleOwner) { showMsg(it) }
    }

    private fun checkPermission() {
        //检查权限是否授权，这个检查的权限是读取手机联系人
        PermissionX.init(this)
            .permissions(Manifest.permission.READ_CONTACTS)
            .onExplainRequestReason { scope, deniedList ->
                //拒绝后回调，可以解释需要该权限的理由
                scope.showRequestReasonDialog(deniedList, "功能正常使用基于这些权限，请允许使用该权限", "OK", "Cancel")
            }
            .onForwardToSettings { scope, deniedList ->
                //拒绝且不在申请时候调用
                scope.showForwardToSettingsDialog(deniedList, "您需要手动在设置中允许必要的权限", "OK", "Cancel")
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    //todo申请完所有权限
                    viewModel.getNewContact()
                } else {
                    //部分权限没有申请
                    "These permissions are denied: $deniedList".toast()
                }
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("AAA","MailListFragment onDestroyView")
        adapter = null
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("AAA","MailListFragment onViewCreated")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("AAA","MailListFragment onDestroy")
    }

}