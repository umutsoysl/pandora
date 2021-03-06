package com.laks.tvseries.pandora.discover

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.laks.tvseries.core.base.fragment.BaseFragment
import com.laks.tvseries.core.cache.MemoryCache
import com.laks.tvseries.core.common.media.MediaListItemAdapter
import com.laks.tvseries.core.common.media.MediaListItemOnClickListener
import com.laks.tvseries.core.data.PandoraActivities
import com.laks.tvseries.core.data.model.MediaType
import com.laks.tvseries.core.data.model.MovieModel
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.pandora.MainViewModel
import com.laks.tvseries.pandora.R
import com.laks.tvseries.pandora.databinding.FragmentDiscoverBinding

class DiscoverFragment: BaseFragment<MainViewModel>(MainViewModel::class), MediaListItemOnClickListener {

    private lateinit var binding: FragmentDiscoverBinding
    private lateinit var adapterMovie: MediaListItemAdapter
    private lateinit var adapterTV: MediaListItemAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createBannerAds()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_discover, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = baseViewModel

        setAdapterMovie()
        setAdapterTV()
        goSearchScreen()

        bindingViewModel()

        return binding.root
    }

    private fun createBannerAds() {
        val fragMan: FragmentManager? = childFragmentManager
        val fragTransaction: FragmentTransaction = fragMan!!.beginTransaction()

        val myFrag: Fragment = (Class.forName(PandoraActivities.pandoraBannerAdsFragmentClassName).newInstance() as Fragment)
        fragTransaction.replace(binding.layoutAds.id, myFrag, "pandoraFragmentAdsDiscoverMovie")
        fragTransaction.commit()
    }

    private fun bindingViewModel() {
        baseViewModel.discoverMovieList.observe(requireActivity(), Observer {
            adapterMovie.submitList(it.results)
            adapterMovie.notifyDataSetChanged()
            binding.rootRelativeView.requestLayout()
            binding.invalidateAll()
        })

        baseViewModel.discoverTVList.observe(requireActivity(), Observer {
            adapterTV.submitList(it.results)
            adapterTV.notifyDataSetChanged()
            binding.rootRelativeView.requestLayout()
            binding.invalidateAll()
        })

        binding.buttonMoreMovie.setOnClickListener {
            MemoryCache.cache.setMemoryCacheValue(GlobalConstants.DISCOVER_MEDIA_TITLE, resources.getString(R.string.movies))
            MemoryCache.cache.setMemoryCacheValue(GlobalConstants.DISCOVER_MEDIA_TYPE, MediaType.movie)
            startActivity(Intent(requireActivity(), DiscoverMediaListActivity::class.java))
        }

        binding.buttonMoreTV.setOnClickListener {
            MemoryCache.cache.setMemoryCacheValue(GlobalConstants.DISCOVER_MEDIA_TITLE, resources.getString(R.string.tv_shows))
            MemoryCache.cache.setMemoryCacheValue(GlobalConstants.DISCOVER_MEDIA_TYPE, MediaType.tv)
            startActivity(Intent(requireActivity(), DiscoverMediaListActivity::class.java))
        }
    }

    private fun goSearchScreen() {
        binding.searchBox.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW).setClassName(requireActivity(), PandoraActivities.searchActivityClassName)
            startActivity(intent)
        }
    }

    private fun setAdapterMovie() {
        adapterMovie = MediaListItemAdapter(context = requireActivity(), clickListener = this)
        var layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerDiscoverMovieList.layoutManager = layoutManager
        binding.recyclerDiscoverMovieList.adapter = adapterMovie
    }

    private fun setAdapterTV() {
        adapterTV = MediaListItemAdapter(context = requireActivity(), clickListener = this)
        var layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerDiscoverTvList.layoutManager = layoutManager
        binding.recyclerDiscoverTvList.adapter = adapterTV
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseViewModel.getDiscoverMovieList(1)
        baseViewModel.getDiscoverTVList(1)
    }

    companion object {
        val TAG: String = DiscoverFragment::class.java.simpleName
        fun newInstance() = DiscoverFragment()
    }

    override fun mediaListItemOnClickListener(scheduleInfo: MovieModel) {
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_TYPE, if(scheduleInfo.isMovie) MediaType.movie else MediaType.tv)
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_ID, scheduleInfo.id!!)
        var intent = Intent(Intent.ACTION_VIEW).setClassName(requireActivity(), PandoraActivities.movieDetailActivityClassName)
        startActivity(intent)
    }
}