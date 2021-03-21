package com.laks.tvseries.core.base.model


open class BaseModel {
    private val classTag = this.javaClass.canonicalName
    open fun getClassTag(): String {
        return classTag ?: this.javaClass.canonicalName ?: ""
    }
}