package com.cranked.androidcorelibrary.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.cranked.androidcorelibrary.ui.raw.RawFragment
import com.cranked.androidcorelibrary.viewmodel.BaseViewModel

abstract class BaseFragment<VM : BaseViewModel, VDB : ViewDataBinding>(
    private val viewModelClass: Class<VM>,
    @LayoutRes private val layoutRes: Int
) : RawFragment() {
    protected val binding by lazy {
        DataBindingUtil.setContentView(activity!!, layoutRes) as VDB
    }
    protected val viewModel by lazy {
        ViewModelProvider(this).get(viewModelClass)
    }
    abstract fun initViewModel(viewModel: VM)
    protected fun onBindingCreate(binding: VDB) = Unit
    protected open fun onBindingClear(binding: VDB) = Unit

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createLiveData(viewLifecycleOwner)
        createListeners()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.setViewDataBinding(binding)
        initViewModel(viewModel)
        onBindingCreate(binding)
        arguments?.let {
            onBundle(it)
        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyboard()
        onBindingClear(binding)
    }
}