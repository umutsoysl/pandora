package com.laks.tvseries.featurecategory.detail

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.laks.tvseries.core.base.activity.BaseActivity
import com.laks.tvseries.core.cache.MemoryCache
import com.laks.tvseries.core.data.model.MediaType
import com.laks.tvseries.core.di.scheduleDIModule
import com.laks.tvseries.core.di.stateDIModule
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.featurecategory.R
import com.laks.tvseries.featurecategory.databinding.ActivityMovieDetailBinding
import com.laks.tvseries.featurecategory.di.detailDIModule
import com.squareup.picasso.Picasso
import org.koin.core.module.Module


class MovieDetailActivity : BaseActivity<MovieDetailViewModel>(MovieDetailViewModel::class) {

    override val modules: List<Module>
        get() = listOf(detailDIModule, stateDIModule, scheduleDIModule)

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var adapter: GenreListItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        binding.viewModel = baseViewModel
        binding.lifecycleOwner = this

        getDetail()
        setAdapter()
        bindingViewModel()
    }

    private fun getDetail() {
        var movieID = MemoryCache.cache.findMemoryCacheValueAny(GlobalConstants.MEDIA_DETAIL_ID).toString()
        var type = MemoryCache.cache.findMemoryCacheValueAny(GlobalConstants.MEDIA_DETAIL_TYPE).toString()

        if(type == MediaType.movie) {
            baseViewModel.getMovieDetail(movieID = movieID)
        } else {
            baseViewModel.getTVDetail(movieID = movieID)
        }
    }

    private fun bindingViewModel() {

        baseViewModel.movieModel.observe(this, Observer { movie ->
            movie.backdropPath.let { Picasso.with(this).load("${GlobalConstants.SERVER_IMAGE_URL}${it}").fit().into(binding.imageBackdrop) }
            movie.posterPath.let { Picasso.with(this).load("${GlobalConstants.SERVER_IMAGE_URL}${it}").fit().into(binding.imageSchedule) }
            adapter.submitList(movie.genres)
            adapter.notifyDataSetChanged()
            binding.rootRelativeView.requestLayout()
            binding.invalidateAll()
            binding.executePendingBindings()
        })
    }

    private fun setAdapter() {
        adapter = GenreListItemAdapter(this@MovieDetailActivity)
        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerGenreList.layoutManager = layoutManager
        binding.recyclerGenreList.adapter = adapter
    }
}