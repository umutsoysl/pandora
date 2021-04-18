package com.laks.tvseries.featuresearch

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import com.laks.tvseries.core.base.activity.BaseActivity
import com.laks.tvseries.core.component.TabViewPagerAdapter
import com.laks.tvseries.featuresearch.databinding.ActivitySearchBinding
import com.laks.tvseries.featuresearch.di.searchDIModule
import com.laks.tvseries.featuresearch.tabs.SearchActorListFragment
import com.laks.tvseries.featuresearch.tabs.SearchMovieListFragment
import com.laks.tvseries.featuresearch.tabs.SearchTvListFragment
import org.koin.core.module.Module

class SearchActivity : BaseActivity<SearchViewModel>(SearchViewModel::class) {

    override val modules: List<Module>
        get() = listOf(searchDIModule)

    private lateinit var binding: ActivitySearchBinding
    private var adapter: TabViewPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.viewModel = baseViewModel
        binding.lifecycleOwner = this

        setupTabLayout()
        prepareTabItems()
        backButtonClick()
        search()
    }

    private fun search() {
        binding.editTextSearchContent.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty()) {
                    baseViewModel.searchQuery.postValue(s.toString())
                    baseViewModel.progressBarVisible.postValue(true)
                    binding.closeButton.visibility = View.VISIBLE
                    baseViewModel.searchMovie()
                } else {
                    baseViewModel.progressBarVisible.postValue(false)
                    binding.closeButton.visibility = View.GONE
                    baseViewModel.clearSearch.postValue(true)
                }
            }
        })

        binding.closeButton.setOnClickListener {
            binding.editTextSearchContent.text = null
        }

    }

    private fun setupTabLayout() {
        adapter = TabViewPagerAdapter(supportFragmentManager)
        adapter?.addFragment(SearchMovieListFragment(), resources.getString(com.laks.tvseries.core.R.string.movie))
        adapter?.addFragment(SearchTvListFragment(), resources.getString(com.laks.tvseries.core.R.string.tv_series))
        adapter?.addFragment(SearchActorListFragment(), resources.getString(com.laks.tvseries.core.R.string.people))
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

    private fun backButtonClick() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}