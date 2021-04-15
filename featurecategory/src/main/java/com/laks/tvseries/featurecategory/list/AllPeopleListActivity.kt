package com.laks.tvseries.featurecategory.list

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.laks.tvseries.core.base.activity.BaseActivity
import com.laks.tvseries.core.cache.MemoryCache
import com.laks.tvseries.core.common.people.PeopleItemClickListener
import com.laks.tvseries.core.data.PandoraActivities
import com.laks.tvseries.core.data.model.PersonInfo
import com.laks.tvseries.core.di.mediaDIModule
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.featurecategory.R
import com.laks.tvseries.featurecategory.category.TrendCategoryViewModel
import com.laks.tvseries.featurecategory.databinding.ActivityAllMovieListBinding
import com.laks.tvseries.featurecategory.di.trendCategoryDIModule
import com.laks.tvseries.featurecategory.list.adapter.AllPeopleListAdapter
import org.koin.core.module.Module

class AllPeopleListActivity: BaseActivity<TrendCategoryViewModel>(TrendCategoryViewModel::class), PeopleItemClickListener {

    override val modules: List<Module>
        get() = listOf(trendCategoryDIModule, mediaDIModule)
    private lateinit var binding: ActivityAllMovieListBinding
    private lateinit var adapter: AllPeopleListAdapter
    private var page = 1
    private var actorList = ArrayList<PersonInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(R.layout.activity_all_movie_list)
        binding.viewModel = baseViewModel
        binding.lifecycleOwner = this
        setToolbarTitle(MemoryCache.cache.findMemoryCacheValueAny(GlobalConstants.ALL_MOVIE_TITLE) as String)

        setAdapter()
        getPopularPeopleList(1)
        bindingViewModel()
    }

    private fun getPopularPeopleList(page: Int) {
        baseViewModel.getPopularPeopleList(page = page)
    }

    private fun bindingViewModel() {
        baseViewModel.popularPeopleList.observe(this, Observer {
            it.results?.let { it1 -> actorList.addAll(it1) }
            adapter.submitList(actorList)
            adapter.notifyDataSetChanged()
            binding.rootRelativeView.requestLayout()
            binding.invalidateAll()
            binding.executePendingBindings()
        })

        binding.buttonMore.setOnClickListener {
            baseViewModel.shimmerVisible.postValue(true)
            page = page + 1
            getPopularPeopleList(page)
        }
    }

    private fun setAdapter() {
        adapter = AllPeopleListAdapter(context = this, clickListener = this)
        var layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerMovieList.layoutManager = layoutManager
        binding.recyclerMovieList.adapter = adapter
    }

    override fun personClickListener(person: PersonInfo) {
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.ACTOR_DETAIL_ID, person.id!!)
        var intent = Intent(Intent.ACTION_VIEW).setClassName(this, PandoraActivities.actorDetailActivityClassName)
        startActivity(intent)
    }
}