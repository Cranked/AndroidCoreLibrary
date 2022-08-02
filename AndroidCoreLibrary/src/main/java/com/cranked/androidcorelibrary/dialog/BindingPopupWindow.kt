package com.cranked.androidcorelibrary.dialog

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BindingPopupWindow<VDB : ViewDataBinding> constructor(
    private val activity: Activity,
    @LayoutRes private val layoutRes: Int,
) {
    protected abstract fun onBindingCreate(popupBinding: VDB)
    var popupBinding: VDB = DataBindingUtil.inflate(LayoutInflater.from(activity),
        layoutRes, null, false)
    private var popupWindow: PopupWindow? = null

    init {
        popupWindow = PopupWindow(
            popupBinding.root,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    constructor(activity: Activity, @LayoutRes layoutRes: Int, width: Int, height: Int) : this(activity, layoutRes) {
        popupWindow = PopupWindow(popupBinding.root, width, height)
    }

    fun showPopup(view: View) {
        popupWindow!!.showAsDropDown(view)
    }

    fun showPopup(view: View, xoff: Int, yoff: Int) {
        popupWindow!!.showAsDropDown(view, xoff, yoff)
    }


    fun showPopup(view: View, xoff: Int, yoff: Int, gravity: Int) {
        popupWindow!!.showAsDropDown(view, xoff, yoff, gravity)
    }

    fun dismiss() {
        popupWindow!!.dismiss()
    }

    fun getBinding(): VDB {
        return popupBinding
    }

    fun getPopupWindow(): PopupWindow? {
        return popupWindow!!
    }
}