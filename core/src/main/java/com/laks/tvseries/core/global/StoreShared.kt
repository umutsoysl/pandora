package com.laks.tvseries.core.global

import android.content.Context


class StoreShared(context: Context) {
    val context = context

    @Synchronized
    fun setStringKeyValue(key: String, value: String) {
        val sharedPref = context.getSharedPreferences(GlobalConstants.SHARED_NAME, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            if(sharedPref.contains(key)){
                remove(key)
            }
            putString(key, value)
            apply()
        }
    }

    @Synchronized
    fun setIntKeyValue(key: String, value: Int) {
        val sharedPref = context.getSharedPreferences(GlobalConstants.SHARED_NAME, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            if(sharedPref.contains(key)){
                remove(key)
            }
            putInt(key, value)
            apply()
        }
    }

    @Synchronized
    fun setBooleanKeyValue(key: String, value: Boolean) {
        val sharedPref = context.getSharedPreferences(GlobalConstants.SHARED_NAME, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            if(sharedPref.contains(key)){
                remove(key)
            }
            putBoolean(key, value)
            apply()
        }
    }

    @Synchronized
    fun getStringValue(key: String): String? {
        val sharedPref = context.getSharedPreferences(GlobalConstants.SHARED_NAME, Context.MODE_PRIVATE)
                ?: return null
        return sharedPref.getString(key, "")
    }

    @Synchronized
    fun getIntValue(key: String): Int? {
        val sharedPref = context.getSharedPreferences(GlobalConstants.SHARED_NAME, Context.MODE_PRIVATE)
                ?: return null
        return sharedPref.getInt(key, 0)
    }

    @Synchronized
    fun getBooleanValue(key: String): Boolean {
        val sharedPref = context.getSharedPreferences(GlobalConstants.SHARED_NAME, Context.MODE_PRIVATE)
                ?: return false
        return sharedPref.getBoolean(key, false)
    }
}
