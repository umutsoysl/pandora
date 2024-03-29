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
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.featurecategory.R
import com.laks.tvseries.featurecategory.category.CategoryViewModel
import com.laks.tvseries.featurecategory.list.adapter.AllMovieListAdapter
import com.laks.tvseries.featurecategory.databinding.ActivityAllMovieListBinding
import com.laks.tvseries.featurecategory.detail.MovieDetailActivity
import com.laks.tvseries.featurecategory.di.categoryDIModule
import org.koin.core.module.Module

class AllMovieListActivity: BaseActivity<CategoryViewModel>(CategoryViewModel::class), MediaListItemOnClickListener {

    override val modules: List<Module>
        get() = listOf(categoryDIModule)
    private lateinit var binding: ActivityAllMovieListBinding
    private lateinit var adapter: AllMovieListAdapter
    private var page = 1
    private var movieList = ArrayList<MovieModel>()
    private var movieType = MovieType.trend
    private var mediaType = MediaType.movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(R.layout.activity_all_movie_list)
        binding.viewModel = baseViewModel
        binding.lifecycleOwner = this
        val title = MemoryCache.cache.findMemoryCacheValueAny(GlobalConstants.ALL_MOVIE_TITLE)
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
            MovieType.topRated -> getTopRatedMovieList(page)
            MovieType.netflixTvShow -> getNetflixTvShowList(page)
            MovieType.appleTvShow -> getAppleTvShowList(page)
            MovieType.amazonTvShow -> getAmazonTvShowList(page)
            MovieType.disneyTvShow -> getDisneyTvShowList(page)
            else -> getPopularMovieList(page)
        }
    }

    private fun getTopRatedMovieList(page: Int) {
        baseViewModel.getTopRatedMovieList(page = page)
        mediaType = MediaType.movie
    }

    private fun getUpPopularTVList(page: Int) {
        baseViewModel.getPopularTvShowList(page = page)
        mediaType = MediaType.tv
    }

    private fun getUpComingMovieList(page: Int) {
        baseViewModel.getUpComingMovieList(page = page)
        mediaType = MediaType.movie
    }

    private fun getPopularMovieList(page: Int) {
        baseViewModel.getPopularMovieList(page = page)
        mediaType = MediaType.movie
    }

    private fun getNowPlayingMovieList(page: Int) {
        baseViewModel.getNowPlayingMovieList(page = page)
        mediaType = MediaType.movie
    }

    private fun getNetflixTvShowList(page: Int) {
        baseViewModel.getNetflixTvShows(page = page)
        mediaType = MediaType.tv
    }

    private fun getAppleTvShowList(page: Int) {
        baseViewModel.getAppleTvShows(page = page)
        mediaType = MediaType.tv
    }

    private fun getAmazonTvShowList(page: Int) {
        baseViewModel.getAmazonTvShows(page = page)
        mediaType = MediaType.tv
    }

    private fun getDisneyTvShowList(page: Int) {
        baseViewModel.getDisneyTvShows(page = page)
        mediaType = MediaType.tv
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
            page += 1
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
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_TYPE, mediaType)
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_ID, movie.id!!)
        var intent = Intent(this@AllMovieListActivity, MovieDetailActivity::class.java)
        startActivity(intent)
    }
}