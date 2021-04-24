package com.laks.tvseries.pandora.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import com.laks.tvseries.core.base.fragment.BaseFragment
import com.laks.tvseries.core.component.TabViewPagerAdapter
import com.laks.tvseries.pandora.R
import com.laks.tvseries.pandora.databinding.FragmentFavoriteBinding
import com.laks.tvseries.pandora.MainViewModel
import com.laks.tvseries.pandora.favorite.tabs.FavoriteMovieFragment
import com.laks.tvseries.pandora.favorite.tabs.FavoriteTvShowsFragment

class FavoriteFragment: BaseFragment<MainViewModel>(MainViewModel::class) {

    private lateinit var binding: FragmentFavoriteBinding
    private var adapter: TabViewPagerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = baseViewModel

        setupTabLayout()
        prepareTabItems()

        return binding.root
    }

    private fun setupTabLayout() {
        adapter = TabViewPagerAdapter(childFragmentManager)
        adapter?.addFragment(FavoriteMovieFragment(), resources.getString(com.laks.tvseries.core.R.string.movie))
        adapter?.addFragment(FavoriteTvShowsFragment(), resources.getString(com.laks.tvseries.core.R.string.tv_series))
        binding.viewPager.adapter = adapter
        binding.viewPager.setSwipe(false)
        binding.tabs.setupWithViewPager(binding.viewPager)
    }

    private fun prepareTabItems() {
        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) = Unit
            override fun onTabUnselected(tab: TabLayout.Tab) = Unit
            override fun onTabSelected(tab: TabLayout.Tab) {}
        })
    }
    companion object {
        val TAG: String = FavoriteFragment::class.java.simpleName
        fun newInstance() = FavoriteFragment()
    }
}