package com.laks.tvseries.featurecategory.list

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.laks.tvseries.core.base.activity.BaseActivity
import com.laks.tvseries.core.cache.MemoryCache
import com.laks.tvseries.core.common.media.MediaListItemOnClickListener
import com.laks.tvseries.core.data.model.MediaType
import com.laks.tvseries.core.data.model.MovieModel
import com.laks.tvseries.core.data.model.MovieType
import com.laks.tvseries.core.di.mediaDIModule
import com.laks.tvseries.core.di.stateDIModule
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.featurecategory.R
import com.laks.tvseries.featurecategory.category.TrendCategoryViewModel
import com.laks.tvseries.featurecategory.list.adapter.AllMovieListAdapter
import com.laks.tvseries.featurecategory.databinding.ActivityAllMovieListBinding
import com.laks.tvseries.featurecategory.detail.MovieDetailActivity
import com.laks.tvseries.featurecategory.di.trendCategoryDIModule
import org.koin.core.module.Module

class AllMovieListActivity: BaseActivity<TrendCategoryViewModel>(TrendCategoryViewModel::class), MediaListItemOnClickListener {

    override val modules: List<Module>
        get() = listOf(trendCategoryDIModule, mediaDIModule)
    private lateinit var binding: ActivityAllMovieListBinding
    private lateinit var adapter: AllMovieListAdapter
    private var page = 1
    private var movieList = ArrayList<MovieModel>()
    private var movieType = MovieType.trend

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(R.layout.activity_all_movie_list)
        binding.viewModel = baseViewModel
        binding.lifecycleOwner = this
        var title = MemoryCache.cache.findMemoryCacheValueAny(GlobalConstants.ALL_MOVIE_TITLE)
        setToolbarTitle(title.let { title.toString() })
        movieType = MemoryCache.cache.findMemoryCacheValueAny(GlobalConstants.MOVIE_TYPE) as String

        setAdapter()
        getLoadMovieList(1)
        bindingViewModel()
    }

    private fun getLoadMovieList(page: Int) {
        when(movieType) {
            MovieType.trend -> getPopularMovieList(page)
            MovieType.nowPlaying -> getNowPlayingMovieList(page)
            MovieType.upComing -> getUpComingMovieList(page)
            MovieType.popularTV -> getUpPopularTVList(page)
            else -> getPopularMovieList(page)
        }
    }

    private fun getUpPopularTVList(page: Int) {
        baseViewModel.getPopularTvShowList(page = page)
    }

    private fun getUpComingMovieList(page: Int) {
        baseViewModel.getUpComingMovieList(page = page)
    }

    private fun getPopularMovieList(page: Int) {
        baseViewModel.getPopularMovieList(page = page)
    }

    private fun getNowPlayingMovieList(page: Int) {
        baseViewModel.getNowPlayingMovieList(page = page)
    }

    private fun bindingViewModel() {
        baseViewModel.allMovieList.observe(this, Observer {
            it.results?.let { it1 -> movieList.addAll(it1) }
            adapter.submitList(movieList)
            adapter.notifyDataSetChanged()
            binding.rootRelativeView.requestLayout()
            binding.invalidateAll()
            binding.executePendingBindings()
        })

        binding.buttonMore.setOnClickListener {
            baseViewModel.shimmerVisible.postValue(true)
            page = page + 1
            getLoadMovieList(page)
        }
    }

    private fun setAdapter() {
        adapter = AllMovieListAdapter(context = this, clickListener = this)
        var layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerMovieList.layoutManager = layoutManager
        binding.recyclerMovieList.adapter = adapter
    }

    override fun mediaListItemOnClickListener(movie: MovieModel) {
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_TYPE, if (movieType == MovieType.popularTV) MediaType.tv else MediaType.movie)
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_ID, movie.id!!)
        var intent = Intent(this@AllMovieListActivity, MovieDetailActivity::class.java)
        startActivity(intent)
    }
}