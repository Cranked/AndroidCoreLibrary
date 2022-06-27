package com.cranked.androidcorelibrary.ui.raw

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cranked.androidcorelibrary.extension.hideKeyboard
import com.cranked.androidcorelibrary.local.LocaleManager

abstract class RawActivity : AppCompatActivity() {
    protected open fun onBundle(bundle: Bundle) = Unit
    protected open fun createLiveData() = Unit
    protected open fun createListeners() = Unit
    protected open fun onRequestPermissionResultForRuntime(isGranted: Boolean) = Unit
    protected fun startActivity(sClass: Class<*>, finishState: Boolean = false) {
        val intent = Intent(this, sClass)
        startActivity(intent)
        if (finishState) finish()
    }

    protected fun startActivity(sClass: Class<*>, bundle: Bundle) {
        val intent = Intent(this, sClass)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    protected fun startActivityResult(sClass: Class<*>, req: Int) {
        val intent = Intent(this, sClass)
        startActivityForResult(intent, req)
    }

    protected fun setNewLocale(language: String, localeManager: LocaleManager) {
        localeManager.setNewLocale(this, language)
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
        finish()
    }

    protected fun reStartApp(sClass: Class<*>) {
        val intent = Intent(applicationContext, sClass)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
        System.exit(0)
    }
    protected fun requestPermissionsForRuntime(permissions: Array<out String>) {
        var checkSelf = true
        permissions.forEach { per ->
            val result = ContextCompat.checkSelfPermission(this, per)
            if (result == PackageManager.PERMISSION_DENIED) {
                checkSelf = false
            }
        }

        if (!checkSelf) {
            ActivityCompat.requestPermissions(this, permissions, 1122)
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
    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }
}