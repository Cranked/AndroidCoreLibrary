package com.cranked.androidcorelibrary.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialog<VDB : ViewDataBinding>(@LayoutRes private val layoutId: Int) :
    BottomSheetDialogFragment() {
    private lateinit var binding: VDB


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            (it as View).setBackgroundColor(Color.TRANSPARENT)
        }
        hideKeyboard()
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val inf = LayoutInflater.from(context)

        binding = DataBindingUtil.inflate(
            inf,
            layoutId,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindingCreate(binding)
    }

    protected fun hideKeyboard() {
        activity?.currentFocus?.let {
            val inputManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputManager?.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    protected abstract fun onBindingCreate(binding: VDB)
}