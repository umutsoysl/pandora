package com.laks.tvseries.pandora.bottomnavigation

import androidx.fragment.app.Fragment
import com.laks.tvseries.pandora.*
import com.laks.tvseries.pandora.home.HomeFragment

enum class BottomNavigationHelper(val position: Int, val id: Int) {
    HOME(0, R.id.home),
    SEARCH(1, R.id.search),
    FAVORITE(1, R.id.favorite),
    SETTINGS(2, R.id.settings);
}

fun findNavigationPositionById(id: Int): BottomNavigationHelper = when (id) {
    BottomNavigationHelper.HOME.id -> BottomNavigationHelper.HOME
    BottomNavigationHelper.SEARCH.id -> BottomNavigationHelper.SEARCH
    BottomNavigationHelper.FAVORITE.id -> BottomNavigationHelper.FAVORITE
    BottomNavigationHelper.SETTINGS.id -> BottomNavigationHelper.SETTINGS
    else -> BottomNavigationHelper.HOME
}

fun BottomNavigationHelper.createFragment(): Fragment = when (this) {
    BottomNavigationHelper.HOME -> HomeFragment.newInstance()
    BottomNavigationHelper.SEARCH -> DiscoveryFragment.newInstance()
    BottomNavigationHelper.FAVORITE -> FavoriteFragment.newInstance()
    BottomNavigationHelper.SETTINGS -> SettingsFragment.newInstance()
}

fun BottomNavigationHelper.getTag(): String = when (this) {
    BottomNavigationHelper.HOME -> HomeFragment.TAG
    BottomNavigationHelper.SEARCH -> DiscoveryFragment.TAG
    BottomNavigationHelper.FAVORITE -> FavoriteFragment.TAG
    BottomNavigationHelper.SETTINGS -> SettingsFragment.TAG
}