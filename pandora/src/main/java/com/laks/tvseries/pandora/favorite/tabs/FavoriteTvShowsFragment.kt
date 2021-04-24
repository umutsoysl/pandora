package com.laks.tvseries.pandora.favorite.tabs

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
import com.laks.tvseries.core.common.media.DBFavoriteListItemClickListener
import com.laks.tvseries.core.data.PandoraActivities
import com.laks.tvseries.core.data.db.DBMediaEntity
import com.laks.tvseries.core.data.model.MediaType
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.pandora.MainViewModel
import com.laks.tvseries.pandora.R
import com.laks.tvseries.pandora.databinding.FragmentTabsFavoriteTvBinding
import com.laks.tvseries.pandora.favorite.FavoriteMediaListAdapter

class FavoriteTvShowsFragment: BaseFragment<MainViewModel>(MainViewModel::class), DBFavoriteListItemClickListener {

    private lateinit var binding: FragmentTabsFavoriteTvBinding
    private lateinit var adapter: FavoriteMediaListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tabs_favorite_tv, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = baseViewModel

        baseViewModel.getDBWatchList(requireActivity(), false)
        setAdapter()
        bindingViewModel()

        return binding.root
    }

    private fun bindingViewModel() {
        baseViewModel.tvWatchList.observe(requireActivity(), Observer {
            if (!it.isNullOrEmpty()) {
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
                binding.rootRelativeView.requestLayout()
                binding.rootRelativeView.invalidate()
            }
        })
    }

    private fun setAdapter() {
        adapter = FavoriteMediaListAdapter(context = requireActivity(), clickListener = this)
        var layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerMovieList.layoutManager = layoutManager
        binding.recyclerMovieList.adapter = adapter
    }

    override fun mediaListItemOnClickListener(cast: DBMediaEntity) {
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_TYPE, MediaType.tv)
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_ID, cast.id)
        var intent = Intent(Intent.ACTION_VIEW).setClassName(requireActivity(), PandoraActivities.movieDetailActivityClassName)
        startActivity(intent)
    }

}