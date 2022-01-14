package com.laks.tvseries.pandora.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.laks.tvseries.core.base.fragment.BaseFragment
import com.laks.tvseries.core.base.fragment.CategoryBaseFragment
import com.laks.tvseries.core.data.PandoraActivities
import com.laks.tvseries.core.view.AccessManagement
import com.laks.tvseries.pandora.MainActivity
import com.laks.tvseries.pandora.MainViewModel
import com.laks.tvseries.pandora.R
import com.laks.tvseries.pandora.databinding.FragmentHomeBinding


class HomeFragment: BaseFragment<MainViewModel>(MainViewModel::class) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: RecyclerFragmentAdapter
    private var fragmentList: ArrayList<CategoryBaseFragment<*>> = ArrayList()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        baseViewModel.repository?.classTag = (activity as MainActivity).javaClass.canonicalName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = baseViewModel

        createCategoryList()
        initializeAdapter()
        return binding.root
    }

    private fun initializeAdapter() {
        adapter = RecyclerFragmentAdapter(fragmentList, requireActivity().supportFragmentManager)
        binding.recyclerViewFragmentList.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun createCategoryList() {
        fragmentList.clear()
        fragmentList.add(AccessManagement.instantiateFragment(PandoraActivities.trendingMovieFragmentClassName)!!)
        fragmentList.add(AccessManagement.instantiateFragment(PandoraActivities.trendingTVFragmentClassName)!!)
        fragmentList.add(AccessManagement.instantiateFragment(PandoraActivities.recommendedMovieFragmentClassName)!!)
        fragmentList.add(AccessManagement.instantiateFragment(PandoraActivities.topRatedMovieFragmentClassName)!!)
        fragmentList.add(AccessManagement.instantiateFragment(PandoraActivities.nowPlayingMovieListFragmentClassName)!!)
        fragmentList.add(AccessManagement.instantiateFragment(PandoraActivities.popularTvShowsFragmentClassName)!!)
        fragmentList.add(AccessManagement.instantiateFragment(PandoraActivities.upComingMovieFragmentClassName)!!)
        fragmentList.add(AccessManagement.instantiateFragment(PandoraActivities.collectionMediaListFragmentClassName)!!)
        fragmentList.add(AccessManagement.instantiateFragment(PandoraActivities.netflixTopTvShowsClassName)!!)
        fragmentList.add(AccessManagement.instantiateFragment(PandoraActivities.appleTopTvShowsClassName)!!)
        fragmentList.add(AccessManagement.instantiateFragment(PandoraActivities.amazonTopTvShowsClassName)!!)
        fragmentList.add(AccessManagement.instantiateFragment(PandoraActivities.disneyTvShowsClassName)!!)
        fragmentList.add(AccessManagement.instantiateFragment(PandoraActivities.popularPeopleFragmentClassName)!!)
    }

    companion object {
        val TAG: String = HomeFragment::class.java.simpleName
        fun newInstance() = HomeFragment()
    }
}