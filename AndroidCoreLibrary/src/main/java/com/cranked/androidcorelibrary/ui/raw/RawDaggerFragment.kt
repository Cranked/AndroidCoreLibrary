package com.cranked.androidcorelibrary.ui.raw

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import dagger.android.support.DaggerFragment

abstract class RawDaggerFragment: DaggerFragment() {
    protected open fun onBundle(bundle: Bundle) = Unit
    protected open fun createLiveData(viewLifecycleOwner: LifecycleOwner) = Unit
    protected open fun createListeners() = Unit
    protected open fun onRequestPermissionResultForRuntime(isGranted: Boolean) = Unit
    open fun onBaseCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?) = Unit
    protected fun startActivity(sClass: Class<*>, finishState: Boolean = false) {
        val intent = Intent(activity, sClass)
        startActivity(intent)
        if (finishState) activity!!.finish()
    }

    protected fun startActivity(sClass: Class<*>, bundle: Bundle) {
        val intent = Intent(activity, sClass)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    protected fun startActivityResult(sClass: Class<*>, req: Int) {
        val intent = Intent(activity, sClass)
        startActivityForResult(intent, req)
    }


    protected fun reStartApp(sClass: Class<*>) {
        val intent = Intent(activity, sClass)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        activity!!.finish()
        System.exit(0)
    }

    protected fun requestPermissionsForRuntime(permissions: Array<out String>) {
        var checkSelf = true
        permissions.forEach { per ->
            val result = ContextCompat.checkSelfPermission(activity!!, per)
            if (result == PackageManager.PERMISSION_DENIED) {
                checkSelf = false
            }
        }
        if (!checkSelf) {
            ActivityCompat.requestPermissions(activity!!, permissions, 1122)
        } else {
            onRequestPermissionResultForRuntime(true)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1122 && grantResults.isNotEmpty()) {
            val size = grantResults.filter { it == PackageManager.PERMISSION_GRANTED }.size
            if (size == grantResults.size) {
                onRequestPermissionResultForRuntime(true)
            } else {
                onRequestPermissionResultForRuntime(false)
            }
        }
    }

    protected fun hideKeyboard() {
        activity?.currentFocus?.let {
            val inputManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputManager?.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }
}