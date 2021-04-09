package com.laks.tvseries.featurecategory.category.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.laks.tvseries.core.base.fragment.CategoryBaseFragment
import com.laks.tvseries.core.cache.MemoryCache
import com.laks.tvseries.core.common.media.MediaListItemAdapter
import com.laks.tvseries.core.common.media.MediaListItemOnClickListener
import com.laks.tvseries.core.data.model.MediaType
import com.laks.tvseries.core.data.model.MovieModel
import com.laks.tvseries.core.data.model.MovieType
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.featurecategory.R
import com.laks.tvseries.featurecategory.category.TrendCategoryViewModel
import com.laks.tvseries.featurecategory.databinding.FragmentTrendMovieBinding
import com.laks.tvseries.featurecategory.detail.MovieDetailActivity
import com.laks.tvseries.featurecategory.di.trendCategoryDIModule
import org.koin.core.module.Module

class UpComingMovieFragment: CategoryBaseFragment<TrendCategoryViewModel>(TrendCategoryViewModel::class),
        MediaListItemOnClickListener {

    override val modules: List<Module>
        get() = arrayListOf(trendCategoryDIModule)

    private lateinit var binding: FragmentTrendMovieBinding
    private lateinit var adapter: MediaListItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trend_movie, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = baseViewModel
        binding.labelTitle.text = requireActivity().resources.getString(R.string.upcoming)

        setAdapter()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        baseViewModel.getUpComingMovieList()

        bindingViewModel()
    }

    private fun bindingViewModel() {
        baseViewModel.allMovieList.observe(requireActivity(), Observer {
            adapter.submitList(it.results)
            adapter.notifyDataSetChanged()
            binding.rootRelativeView.requestLayout()
            binding.invalidateAll()
            binding.executePendingBindings()
        })

        binding.buttonMore.setOnClickListener {
            MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MOVIE_TYPE, MovieType.upComing)
            MemoryCache.cache.setMemoryCacheValue(GlobalConstants.ALL_MOVIE_TITLE, binding.labelTitle.text)
            var intent = Intent(requireActivity(), AllMovieListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setAdapter() {
        adapter = MediaListItemAdapter(context = requireActivity(), clickListener = this)
        var layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerMediaList.layoutManager = layoutManager
        binding.recyclerMediaList.adapter = adapter
    }

    companion object {
        fun newInstance() = UpComingMovieFragment()
    }

    override fun mediaListItemOnClickListener(scheduleInfo: MovieModel) {
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_TYPE, MediaType.movie)
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_ID, scheduleInfo.id!!)
        var intent = Intent(requireActivity(), MovieDetailActivity::class.java)
        startActivity(intent)
    }
}