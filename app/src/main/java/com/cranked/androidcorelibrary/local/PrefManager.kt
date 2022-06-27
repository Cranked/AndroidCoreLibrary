package com.cranked.androidcorelibrary.local

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.io.Serializable


class PrefManager(context: Context) {
    private val mSharedPref: SharedPreferences
    init {
        val spec = KeyGenParameterSpec.Builder(
            MasterKey.DEFAULT_MASTER_KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .build()

        val masterKey = MasterKey.Builder(context)
            .setKeyGenParameterSpec(spec)
            .build()

        mSharedPref = try {
            EncryptedSharedPreferences.create(
                context,
                "secret_shared_prefs",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (ex: Exception) {
            context.getSharedPreferences("secret_shared_prefs", Context.MODE_PRIVATE)
        }
    }
    fun getSharedPreferences() = mSharedPref

    fun <T : Any> SharedPreferences.Editor.set(pair: Pair<String, T>) {
        when (pair.second) {
            is String -> putString(pair.first, pair.second as String)
            is Int -> putInt(pair.first, pair.second as Int)
            is Long -> putLong(pair.first, pair.second as Long)
            is Float -> putFloat(pair.first, pair.second as Float)
            is Set<*> -> putStringSet(pair.first, pair.second as Set<String>)
        }
    }

    fun <T : Any> get(key: String, defaultVal: T?): Serializable? {
        val preference = getSharedPreferences()
        return when (defaultVal) {
            is String, null -> preference.getString(key, defaultVal.toString())
            is Int -> preference.getInt(key, defaultVal.toInt())
            is Float -> preference.getFloat(key, defaultVal.toFloat())
            is Long -> preference.getLong(key, defaultVal.toLong())
            else -> IllegalArgumentException("Type mismatch")
        }
    }

    fun getLanguage(): String {
        return get("LANGUAGE", "").toString()
    }

    fun addValueApply(keyValue: String, value: Any?) {
        val editor = mSharedPref.edit()
        when (value) {
            is String -> editor.putString(keyValue, value)
            is Int -> editor.putInt(keyValue, value)
            is Long -> editor.putLong(keyValue, value)
            is Float -> editor.putFloat(keyValue, value)
        }
        editor.apply()
    }

    fun addValueCommit(keyValue: String, value: Any?) {
        val editor = mSharedPref.edit()
        when (value) {
            is String -> editor.putString(keyValue, value)
            is Int -> editor.putInt(keyValue, value)
            is Long -> editor.putLong(keyValue, value)
            is Float -> editor.putFloat(keyValue, value)
        }
        editor.commit()
    }
//    inline operator fun <reified T> SharedPreferences.Editor.set(key: String, value: T) {
//        when (T::class.java) {
//            Int::class.java -> putInt(key, value as Int)
//            Long::class.java -> putLong(key, value as Long)
//        }
//    }
//
//    inline operator fun <reified T> SharedPreferences.get(key: String, defValue: T): T = when (T::class.java) {
//        Int::class.java -> getInt(key, defValue as Int) as T
//        Long::class.java -> getLong(key, defValue as Long) as T
//        else -> 0 as T
//    }
}