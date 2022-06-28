package com.cranked.androidcorelibrary.local

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import java.util.*

class LocaleManager constructor(private val prefManager: PrefManager) {


    private fun updateResources(context: Context, language: String): Context {
        val newLocale = Locale(language)
        val res = context.resources
        val dm = res.displayMetrics
        val config = Configuration(res.configuration)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(newLocale)
            val localeList = LocaleList(newLocale)
            LocaleList.setDefault(localeList)
            config.setLocales(localeList)
            val context = context.createConfigurationContext(config)
            config.setLocale(newLocale)
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
            return context
        } else {
            config.setLocale(newLocale)
            val context = context.createConfigurationContext(config)
            config.setLocale(newLocale)
            res.updateConfiguration(config, dm)
            return context
        }

    }

    fun setLocale(c: Context): Context {
        return updateResources(c, prefManager.getLanguage())
    }

    fun setNewLocale(c: Context, language: String): Context {
        prefManager.addValueCommit("LANGUAGE", language)
        return updateResources(c, prefManager.getLanguage())
    }
}