package com.laks.tvseries.featuresearch.tabs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.laks.tvseries.core.base.fragment.BaseFragment
import com.laks.tvseries.core.cache.MemoryCache
import com.laks.tvseries.core.common.media.MediaListItemOnClickListener
import com.laks.tvseries.core.data.PandoraActivities
import com.laks.tvseries.core.data.model.MediaType
import com.laks.tvseries.core.data.model.MovieModel
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.featuresearch.R
import com.laks.tvseries.featuresearch.SearchViewModel
import com.laks.tvseries.featuresearch.databinding.FragmentTvSeriesListBinding
import com.laks.tvseries.featuresearch.tabs.adapter.SearchTvListAdapter

class SearchTvListFragment: BaseFragment<SearchViewModel>(SearchViewModel::class), MediaListItemOnClickListener {

    private lateinit var binding: FragmentTvSeriesListBinding
    private lateinit var tvAdapter: SearchTvListAdapter
    private var tvSeriesList: ArrayList<MovieModel> = arrayListOf()
    private var page = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tv_series_list, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = baseViewModel

        setAdapter()
        bindingViewModel()

        return binding.root
    }

    private fun bindingViewModel() {
        baseViewModel.responseTvList.observe(requireActivity(), Observer {
            it.results?.let { it1 -> tvSeriesList.addAll(it1) }
            tvAdapter.submitList(tvSeriesList)
            tvAdapter.notifyDataSetChanged()
            requestLayout()
            binding.buttonMore.visibility =  if (it.results?.size!!>0 && it.results?.size!!%20 == 0) View.VISIBLE else View.GONE
        })

        binding.buttonMore.setOnClickListener {
            page = page + 1
            baseViewModel.searchTvSeries(page)
        }
    }

    private fun requestLayout() {
        binding.rootRelativeView.requestLayout()
        binding.executePendingBindings()
        binding.invalidateAll()
    }

    private fun setAdapter() {
        tvAdapter = SearchTvListAdapter(context = requireActivity(), clickListener = this)
        var layoutManager1 = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerMediaList.layoutManager = layoutManager1
        binding.recyclerMediaList.adapter = tvAdapter

    }

    override fun mediaListItemOnClickListener(scheduleInfo: MovieModel) {
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_TYPE, MediaType.tv)
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_ID, scheduleInfo.id!!)
        var intent = Intent(Intent.ACTION_VIEW).setClassName(requireActivity(), PandoraActivities.movieDetailActivityClassName)
        startActivity(intent)
    }
}