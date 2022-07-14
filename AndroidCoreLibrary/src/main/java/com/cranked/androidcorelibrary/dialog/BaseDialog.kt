package com.cranked.androidcorelibrary.dialog

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.cranked.androidcorelibrary.R

class BaseDialog(
    activity: Activity,
    layoutId: Int,
) {
    protected var dialog: AlertDialog
    protected var view: View = activity.layoutInflater.inflate(layoutId, null)

    init {

        this.dialog = AlertDialog.Builder(activity)
            .setView(view)
            .create()
        val color = ColorDrawable(Color.TRANSPARENT)
        dialog.window?.let {
            it.attributes.windowAnimations = R.style.DialogScale
            it.setBackgroundDrawable(color)
        }
    }


    @JvmName("getDialog1")
    fun getDialog() = this.dialog
}
