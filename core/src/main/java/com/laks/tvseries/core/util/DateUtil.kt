package com.laks.tvseries.core.util

import java.text.SimpleDateFormat
import java.util.*

fun getDate(): String {
    val sdf = SimpleDateFormat("HH:mm dd.MM.yyyy")
    val date: String = sdf.format(Date())
    return date
}

fun getProcessDate(): Long {
    val sdf = SimpleDateFormat("yyyyMMdd")
    val date: String = sdf.format(Date())
    return date.toLong()
}