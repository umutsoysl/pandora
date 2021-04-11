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
import com.laks.tvseries.core.common.people.PeopleItemClickListener
import com.laks.tvseries.core.data.PandoraActivities
import com.laks.tvseries.core.data.model.PersonInfo
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.featuresearch.R
import com.laks.tvseries.featuresearch.SearchViewModel
import com.laks.tvseries.featuresearch.databinding.FragmentActorListBinding
import com.laks.tvseries.featuresearch.tabs.adapter.SearchPeopleListAdapter

class SearchActorListFragment: BaseFragment<SearchViewModel>(SearchViewModel::class), PeopleItemClickListener {

    private lateinit var binding: FragmentActorListBinding
    private lateinit var actorAdapter: SearchPeopleListAdapter
    private var actorList: ArrayList<PersonInfo> = arrayListOf()
    private var page = 1
    private var isAdd = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_actor_list, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = baseViewModel

        setAdapter()
        bindingViewModel()

        return binding.root
    }

    private fun bindingViewModel() {
        baseViewModel.responseActorList.observe(requireActivity(), Observer {
            if(isAdd) {
                it.results?.let { it1 -> actorList.addAll(it1) }
                actorAdapter.submitList(actorList)
            } else {
                actorList = it.results!!
                actorAdapter.submitList(it.results)
            }
            actorAdapter.notifyDataSetChanged()
            binding.buttonMore.visibility =  if (it.results?.size!!>0 && it.results?.size!!%20 == 0) View.VISIBLE else View.GONE
            requestLayout()
        })

        binding.buttonMore.setOnClickListener {
            page = page + 1
            baseViewModel.searchActor(page)
            isAdd = true
        }

        baseViewModel.clearSearch.observe(requireActivity(), Observer {
            actorList.clear()
            page = 1
            isAdd = false
        })
    }

    private fun requestLayout() {
        binding.rootRelativeView.requestLayout()
        binding.executePendingBindings()
        binding.invalidateAll()
    }

    private fun setAdapter() {
        actorAdapter = SearchPeopleListAdapter(context = requireActivity(), clickListener = this)
        var layoutManager2 = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerMediaList.layoutManager = layoutManager2
        binding.recyclerMediaList.adapter = actorAdapter
    }

    override fun personClickListener(person: PersonInfo) {
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.ACTOR_DETAIL_ID, person.id!!)
        var intent = Intent(Intent.ACTION_VIEW).setClassName(requireActivity(), PandoraActivities.actorDetailActivityClassName)
        startActivity(intent)
    }
}