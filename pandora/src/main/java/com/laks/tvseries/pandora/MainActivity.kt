package com.laks.tvseries.pandora

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.laks.tvseries.core.view.active
import com.laks.tvseries.core.view.attach
import com.laks.tvseries.core.view.detach
import com.laks.tvseries.pandora.bottomnavigation.BottomNavigationHelper
import com.laks.tvseries.pandora.bottomnavigation.createFragment
import com.laks.tvseries.pandora.bottomnavigation.findNavigationPositionById
import com.laks.tvseries.pandora.bottomnavigation.getTag

class MainActivity : AppCompatActivity() {

    private val KEY_POSITION = "keyPosition"

    private var navPosition: BottomNavigationHelper = BottomNavigationHelper.HOME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<BottomNavigationView>(R.id.bottom_navigation).apply {
            active(navPosition.position)
            setOnNavigationItemSelectedListener { item ->
                navPosition = findNavigationPositionById(item.itemId)
                switchFragment(navPosition)
            }
        }

        initFragment(savedInstanceState)
    }

    private fun restoreSaveInstanceState(savedInstanceState: Bundle?) {
        // Restore the current navigation position.
        savedInstanceState?.getInt(KEY_POSITION, BottomNavigationHelper.HOME.id)?.also {
            navPosition = findNavigationPositionById(it)
        }
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        savedInstanceState ?: switchFragment(BottomNavigationHelper.HOME)
    }


    private fun switchFragment(navPosition: BottomNavigationHelper): Boolean {
        return findFragment(navPosition).let {
            if (it.isAdded) return false
            supportFragmentManager.detach()
            supportFragmentManager.attach(it, navPosition.getTag())
            supportFragmentManager.executePendingTransactions()
        }
    }

    private fun findFragment(position: BottomNavigationHelper): Fragment {
        return supportFragmentManager.findFragmentByTag(position.getTag()) ?: position.createFragment()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
        finish()
        finishAffinity()
    }
}