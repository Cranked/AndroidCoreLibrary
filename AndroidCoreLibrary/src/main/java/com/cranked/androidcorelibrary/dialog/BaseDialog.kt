package com.cranked.androidcorelibrary.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.cranked.androidcorelibrary.R

class BaseDialog(
    context: Context,
    layoutId: Int,
) {
    protected var dialog: AlertDialog
    val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
    val view =
        (layoutInflater as LayoutInflater).inflate(layoutId, null)

    init {

        this.dialog = AlertDialog.Builder(context)
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
