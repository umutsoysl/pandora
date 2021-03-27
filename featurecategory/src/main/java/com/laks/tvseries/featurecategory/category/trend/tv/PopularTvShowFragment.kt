package com.laks.tvseries.featurecategory.category.trend.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.laks.tvseries.core.base.fragment.CategoryBaseFragment
import com.laks.tvseries.core.common.media.MediaListItemAdapter
import com.laks.tvseries.core.common.media.MediaListItemOnClickListener
import com.laks.tvseries.core.data.model.MovieModel
import com.laks.tvseries.featurecategory.R
import com.laks.tvseries.featurecategory.category.trend.TrendCategoryViewModel
import com.laks.tvseries.featurecategory.databinding.FragmentTrendMovieBinding
import com.laks.tvseries.featurecategory.di.trendCategoryDIModule
import org.koin.core.module.Module

class PopularTvShowFragment: CategoryBaseFragment<TrendCategoryViewModel>(TrendCategoryViewModel::class),
    MediaListItemOnClickListener {

    override val modules: List<Module>
        get() = arrayListOf(trendCategoryDIModule)

    private lateinit var binding: FragmentTrendMovieBinding
    private lateinit var adapter: MediaListItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trend_movie, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = baseViewModel

        binding.labelTitle.text = requireActivity().resources.getString(R.string.popular)
        binding.labelSubTitle.text = requireActivity().resources.getString(R.string.tv_shows)
        setAdapter()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        baseViewModel.getPopularTvShowList()

        bindingViewModel()
    }

    private fun bindingViewModel() {
        baseViewModel.popularTvShowList.observe(requireActivity(), Observer {
            adapter.submitList(it.results)
            adapter.notifyDataSetChanged()
            binding.rootRelativeView.requestLayout()
            binding.invalidateAll()
            binding.executePendingBindings()
        })
    }

    private fun setAdapter() {
        adapter = MediaListItemAdapter(context = requireActivity(), clickListener = this)
        var layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerMediaList.layoutManager = layoutManager
        binding.recyclerMediaList.adapter = adapter
    }

    companion object {
        fun newInstance() = PopularTvShowFragment()
    }

    override fun mediaListItemOnClickListener(scheduleInfo: MovieModel) {
        TODO("Not yet implemented")
    }
}