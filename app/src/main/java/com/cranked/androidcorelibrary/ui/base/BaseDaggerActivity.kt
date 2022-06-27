package com.cranked.androidcorelibrary.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.cranked.androidcorelibrary.di.factory.AppViewModelFactory
import com.cranked.androidcorelibrary.ui.raw.RawDaggerActivity
import com.cranked.androidcorelibrary.viewmodel.BaseViewModel
import javax.inject.Inject

abstract class BaseDaggerActivity<VM : BaseViewModel, VDB : ViewDataBinding>(
    private val viewModelClass: Class<VM>,
    @LayoutRes layoutRes: Int
) : RawDaggerActivity() {

    @Inject
    lateinit var factory: AppViewModelFactory

    protected val binding by lazy {
        DataBindingUtil.setContentView(this, layoutRes) as VDB
    }
    protected val viewModel by lazy {
        ViewModelProvider(this,factory).get(viewModelClass)
    }
    abstract fun initViewModel(viewModel: VM)
    protected fun onBindingCreate(binding: VDB) = Unit
    protected open fun onBindingClear(binding: VDB) = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setViewDataBinding(binding)
        initViewModel(viewModel)
        onBindingCreate(binding)
        intent.extras?.let {
            onBundle(it)
        }
        createLiveData()
        createListeners()
    }
    override fun onDestroy() {
        super.onDestroy()
        onBindingClear(binding)
    }

}