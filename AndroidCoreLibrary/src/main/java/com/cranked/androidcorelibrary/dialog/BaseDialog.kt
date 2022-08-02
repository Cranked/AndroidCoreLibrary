package com.cranked.androidcorelibrary.dialog

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog

class BaseDialog(
    activity: Activity,

    ) {
    private var dialog: AlertDialog
    val layoutInflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
    private lateinit var view: View

    init {
        this.dialog = AlertDialog.Builder(activity)
            .create()
        val color = ColorDrawable(Color.TRANSPARENT)
        dialog.window?.let {
            it.setBackgroundDrawable(color)
        }
    }

    constructor(activity: Activity, view: View, themeResId: Int) : this(activity) {
        this.dialog = AlertDialog.Builder(activity, themeResId)
            .setView(view)
            .create()
    }

    constructor(activity: Activity, view: View) : this(activity) {
        this.dialog = AlertDialog.Builder(activity)
            .setView(view)
            .create()
        val color = ColorDrawable(Color.TRANSPARENT)
        dialog.window?.let {
            it.setBackgroundDrawable(color)
        }
    }

    constructor(activity: Activity, layoutId: Int) : this(activity) {
        this.view = (layoutInflater as LayoutInflater).inflate(layoutId, null)
        this.dialog = AlertDialog.Builder(activity)
            .setView(view)
            .create()
        val color = ColorDrawable(Color.TRANSPARENT)
        dialog.window?.let {
            it.setBackgroundDrawable(color)
        }
    }

    constructor(activity: Activity, layoutId: Int, themeResId: Int) : this(activity) {
        this.view = (layoutInflater as LayoutInflater).inflate(layoutId, null)
        this.dialog = AlertDialog.Builder(activity, themeResId)
            .setView(view)
            .create()
        val color = ColorDrawable(Color.TRANSPARENT)
        dialog.window?.let {
            it.setBackgroundDrawable(color)
        }
    }

    fun getDialog() = this.dialog
}
