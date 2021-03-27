package com.laks.tvseries.core.view

import com.laks.tvseries.core.base.activity.BaseActivity
import com.laks.tvseries.core.base.fragment.CategoryBaseFragment

class AccessManagement {
    companion object {

        fun instantiateFragment(className: String): CategoryBaseFragment<*>? {
            return try {
                Class.forName(className).newInstance() as CategoryBaseFragment<*>
            } catch (e: Exception) {
                null
            }
        }

        fun instantiateActivity(className: String): BaseActivity<*>? {
            return try {
                Class.forName(className).newInstance() as BaseActivity<*>
            } catch (e: Exception) {
                null
            }
        }
    }
}