package com.laks.tvseries.featurecategory.category.people

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
import com.laks.tvseries.core.common.people.PeopleItemClickListener
import com.laks.tvseries.core.common.people.PeopleListItemAdapter
import com.laks.tvseries.core.data.PandoraActivities
import com.laks.tvseries.core.data.model.PersonInfo
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.featurecategory.R
import com.laks.tvseries.featurecategory.category.TrendCategoryViewModel
import com.laks.tvseries.featurecategory.category.tv.PopularTvShowFragment
import com.laks.tvseries.featurecategory.databinding.FragmentTrendMovieBinding
import com.laks.tvseries.featurecategory.di.trendCategoryDIModule
import com.laks.tvseries.featurecategory.list.AllPeopleListActivity
import org.koin.core.module.Module

class PopularPeopleFragment: CategoryBaseFragment<TrendCategoryViewModel>(TrendCategoryViewModel::class), PeopleItemClickListener {

    override val modules: List<Module>
        get() = arrayListOf(trendCategoryDIModule)

    private lateinit var binding: FragmentTrendMovieBinding
    private lateinit var adapter: PeopleListItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trend_movie, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = baseViewModel

        binding.labelTitle.text = requireActivity().resources.getString(R.string.popular_people)
        binding.labelSubTitle.visibility = View.GONE
        setAdapter()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        baseViewModel.getPopularPeopleList()

        bindingViewModel()
    }

    private fun bindingViewModel() {
        baseViewModel.popularPeopleList.observe(requireActivity(), Observer {
            adapter.submitList(it.results)
            adapter.notifyDataSetChanged()
            binding.rootRelativeView.requestLayout()
            binding.invalidateAll()
            binding.executePendingBindings()
        })

        binding.buttonMore.setOnClickListener {
            MemoryCache.cache.setMemoryCacheValue(GlobalConstants.ALL_MOVIE_TITLE, "${binding.labelTitle.text} ${binding.labelSubTitle.text}")
            var intent = Intent(requireActivity(), AllPeopleListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setAdapter() {
        adapter = PeopleListItemAdapter(context = requireActivity(), clickListener = this)
        var layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerMediaList.layoutManager = layoutManager
        binding.recyclerMediaList.adapter = adapter
    }

    companion object {
        fun newInstance() = PopularTvShowFragment()
    }

    override fun personClickListener(person: PersonInfo) {
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.ACTOR_DETAIL_ID, person.id!!)
        var intent = Intent(Intent.ACTION_VIEW).setClassName(requireActivity(), PandoraActivities.actorDetailActivityClassName)
        startActivity(intent)
    }
}