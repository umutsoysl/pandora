package com.laks.tvseries.core.base.service

abstract class BaseResponse {
    private val classTag = this.javaClass.canonicalName

    fun getClassTag(): String {
        return classTag
    }
}