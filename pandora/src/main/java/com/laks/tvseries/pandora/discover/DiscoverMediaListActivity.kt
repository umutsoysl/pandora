package com.laks.tvseries.pandora.discover

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.laks.tvseries.core.base.activity.BaseActivity
import com.laks.tvseries.core.base.activity.HeaderIconItemClickListener
import com.laks.tvseries.core.cache.MemoryCache
import com.laks.tvseries.core.common.media.MediaListItemOnClickListener
import com.laks.tvseries.core.data.PandoraActivities
import com.laks.tvseries.core.data.model.HeaderIconType
import com.laks.tvseries.core.data.model.MediaType
import com.laks.tvseries.core.data.model.MovieModel
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.pandora.MainViewModel
import com.laks.tvseries.pandora.R
import com.laks.tvseries.pandora.databinding.ActivityDiscoverMediaListBinding
import com.laks.tvseries.pandora.di.homeDIModule
import org.koin.core.module.Module

class DiscoverMediaListActivity: BaseActivity<MainViewModel>(MainViewModel::class), MediaListItemOnClickListener, HeaderIconItemClickListener {
    override val modules: List<Module>
        get() = listOf(homeDIModule)
    private lateinit var binding: ActivityDiscoverMediaListBinding
    private lateinit var adapter: DiscoverMediaListAdapter
    private var page = 1
    private var movieList = ArrayList<MovieModel>()
    private var mediaType = MediaType.movie
    private var genre = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(R.layout.activity_discover_media_list)
        binding.viewModel = baseViewModel
        binding.lifecycleOwner = this
        showFilterButton(this)
        val title = MemoryCache.cache.findMemoryCacheValueAny(GlobalConstants.DISCOVER_MEDIA_TITLE)
        setToolbarTitle(title.let { title.toString() })

        mediaType = MemoryCache.cache.findMemoryCacheValueAny(GlobalConstants.DISCOVER_MEDIA_TYPE) as String
        genre = MemoryCache.cache.findMemoryCacheValueAny(GlobalConstants.GENRE_ID) as String

        if (genre.isNullOrEmpty()) {
            genre = ""
        }

        setAdapter()
        bindingViewModel()
        getDiscoverData()
    }

    private fun bindingViewModel() {
        baseViewModel.discoverMovieList.observe(this, Observer {
            it.results?.let { it1 -> movieList.addAll(it1) }
            adapter.submitList(movieList)
            adapter.notifyDataSetChanged()
            baseViewModel.shimmerVisible.postValue(false)
            binding.rootRelativeView.requestLayout()
            binding.invalidateAll()
        })

        baseViewModel.discoverTVList.observe(this, Observer {
            it.results?.let { it1 -> movieList.addAll(it1) }
            adapter.submitList(movieList)
            adapter.notifyDataSetChanged()
            baseViewModel.shimmerVisible.postValue(false)
            binding.rootRelativeView.requestLayout()
            binding.invalidateAll()
        })

        binding.buttonMore.setOnClickListener {
            baseViewModel.shimmerVisible.postValue(true)
            page += 1
            getDiscoverData()
        }
    }

    private fun getDiscoverData() {
        when(mediaType) {
            MediaType.movie -> baseViewModel.getDiscoverMovieList(page, genre)
            MediaType.tv -> baseViewModel.getDiscoverTVList(page, genre)
        }
    }

    private fun setAdapter() {
        adapter = DiscoverMediaListAdapter(context = this, clickListener = this)
        var layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerMovieList.layoutManager = layoutManager
        binding.recyclerMovieList.adapter = adapter
    }

    override fun mediaListItemOnClickListener(media: MovieModel) {
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_TYPE, mediaType)
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_ID, media.id!!)
        var intent = Intent(Intent.ACTION_VIEW).setClassName(this, PandoraActivities.movieDetailActivityClassName)
        startActivity(intent)
    }

    override fun headerIconClickListener(headerIconType: String) {
        when(headerIconType) {
            HeaderIconType.filter -> ""
        }
    }
}