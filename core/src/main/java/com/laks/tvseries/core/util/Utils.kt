package com.laks.tvseries.core.util

import android.app.ActivityManager
import android.content.Context
import com.laks.tvseries.core.R
import com.laks.tvseries.core.cache.MemoryCache
import com.laks.tvseries.core.data.model.Genre
import com.laks.tvseries.core.global.GlobalConstants
import java.text.DecimalFormat


fun getModList(context: Context): ArrayList<Genre> {
    val modList: ArrayList<Genre> = ArrayList()

    modList.add(Genre(1, context.resources.getString(R.string.mod_drama)))
    modList.add(Genre(2, context.resources.getString(R.string.mod_friend)))
    modList.add(Genre(3, context.resources.getString(R.string.mod_science_fiction)))
    modList.add(Genre(4, context.resources.getString(R.string.mod_funny)))
    modList.add(Genre(5, context.resources.getString(R.string.mod_impressive)))
    modList.add(Genre(6, context.resources.getString(R.string.mod_thriller)))
    modList.add(Genre(7, context.resources.getString(R.string.mod_love)))
    modList.add(Genre(8, context.resources.getString(R.string.mod_fantastic)))
    modList.add(Genre(9, context.resources.getString(R.string.mod_adventure)))
    modList.add(Genre(10, context.resources.getString(R.string.mod_war)))
    modList.add(Genre(11, context.resources.getString(R.string.mod_real)))
    modList.add(Genre(12, context.resources.getString(R.string.mod_musical)))
    modList.add(Genre(13, context.resources.getString(R.string.mod_classic)))
    modList.add(Genre(14, context.resources.getString(R.string.mod_random)))

    return modList
}

fun currencyFormat(amount: String): String? {
    if (amount == "0") {
        return "0.00"
    }
    val formatter = DecimalFormat("###,###,###.00")
    return formatter.format(amount.toDouble())
}

fun Double.format(digits: Int) = "%.${digits}f".format(this)


@Synchronized
fun isActivityCreated(context: Context): Boolean {
    try {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val procInfos = activityManager.runningAppProcesses
        for (i in procInfos.indices) {
            if (procInfos[i].importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && procInfos[i].processName == GlobalConstants.APP_PACKAGE_NAME) {
                return true
            }
        }
    } catch (e: Exception) {
        // Exception
    }
    return false
}