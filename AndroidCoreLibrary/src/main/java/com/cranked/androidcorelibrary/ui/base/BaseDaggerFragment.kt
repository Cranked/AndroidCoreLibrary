package com.cranked.androidcorelibrary.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.cranked.androidcorelibrary.di.factory.AppViewModelFactory
import com.cranked.androidcorelibrary.ui.raw.RawDaggerFragment
import com.cranked.androidcorelibrary.viewmodel.BaseViewModel
import javax.inject.Inject

abstract class BaseDaggerFragment<VM : BaseViewModel, VDB : ViewDataBinding>(
    private val viewmodelClass: Class<VM>,
    @LayoutRes private val layoutRes: Int
) : RawDaggerFragment() {
    @Inject
    lateinit var providerFactory: AppViewModelFactory
    protected lateinit var binding: VDB

    protected val viewModel by lazy {
        ViewModelProvider(this,providerFactory).get(viewmodelClass)
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
        onBaseCreateView(inflater, container, savedInstanceState)
        viewModel.setViewDataBinding(binding)
        initViewModel(viewModel)
        onBindingCreate(binding)

        arguments?.let {
            onBundle(it)
        }
        return binding.root
    }
    protected abstract fun getViewDataBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup?
    ): VDB
    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyboard()
        onBindingClear(binding)
    }
}