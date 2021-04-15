package com.laks.tvseries.core.base.fragment

import android.os.Bundle
import com.laks.tvseries.core.base.viewmodel.BaseViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import kotlin.reflect.KClass

abstract class CategoryBaseFragment<QVIEWMODEL : BaseViewModel>(clazz: KClass<QVIEWMODEL>) : BaseFragment<QVIEWMODEL>(clazz) {

    abstract val modules: List<Module>

    private val classTag = this.javaClass.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            unloadKoinModules(modules)
            loadKoinModules(modules)
        }catch (ex: Exception) { }
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(modules)
    }
}