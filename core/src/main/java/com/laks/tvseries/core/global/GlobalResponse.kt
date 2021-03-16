package com.laks.tvseries.core.global

data class GlobalResponse<T>(
        val content: T?,
        var success: Boolean = false,
        var exceptionType: Int?,
)
