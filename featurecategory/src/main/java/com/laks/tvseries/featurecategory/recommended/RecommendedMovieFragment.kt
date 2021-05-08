package com.laks.tvseries.featurecategory.recommended

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.firebase.database.*
import com.laks.tvseries.core.base.fragment.CategoryBaseFragment
import com.laks.tvseries.core.cache.MemoryCache
import com.laks.tvseries.core.common.media.GenreListItemOnClickListener
import com.laks.tvseries.core.data.model.Genre
import com.laks.tvseries.core.data.model.MediaType
import com.laks.tvseries.core.data.model.firebase.FbModListMovieDataModel
import com.laks.tvseries.core.data.model.firebase.FbMovieDataModel
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.core.util.getModList
import com.laks.tvseries.featurecategory.R
import com.laks.tvseries.featurecategory.category.CategoryViewModel
import com.laks.tvseries.featurecategory.databinding.FragmentRecommendedBinding
import com.laks.tvseries.featurecategory.detail.GenreListItemAdapter
import com.laks.tvseries.featurecategory.detail.MovieDetailActivity
import com.laks.tvseries.featurecategory.di.categoryDIModule
import org.koin.core.module.Module

class RecommendedMovieFragment: CategoryBaseFragment<CategoryViewModel>(CategoryViewModel::class), GenreListItemOnClickListener{

    override val modules: List<Module>
        get() = arrayListOf(categoryDIModule)

    private lateinit var binding: FragmentRecommendedBinding
    private lateinit var adapter: GenreListItemAdapter
    private var isOpenModList: Boolean = true
    private lateinit var fbDatabase : DatabaseReference
    private var randomMovie: FbMovieDataModel? = null
    private var modListMovie: ArrayList<FbMovieDataModel> = arrayListOf()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(MemoryCache.cache.findMemoryCacheValue(GlobalConstants.FIREBASE_RANDOM_MOVIE_TABLE) == null) {
            getFirebaseDatabaseValueList(GlobalConstants.FIREBASE_RANDOM_MOVIE_TABLE)
        }
        getFirebaseDatabaseValueList(GlobalConstants.FIREBASE_MOD_LIS_MOVIE_TABLE)
        if (MemoryCache.cache.findMemoryCacheValueAny(GlobalConstants.FIREBASE_ABOUT_TABLE) == null) {
            getFirebaseDatabaseValueList(GlobalConstants.FIREBASE_ABOUT_TABLE)
        }
        if (MemoryCache.cache.findMemoryCacheValueAny(GlobalConstants.FIREBASE_TERM_OF_USE_TABLE) == null) {
            getFirebaseDatabaseValueList(GlobalConstants.FIREBASE_TERM_OF_USE_TABLE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recommended, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = baseViewModel

        setAdapter()

        binding.buttonSelectMod.setOnClickListener {
            binding.recyclerModList.visibility = if (isOpenModList) View.VISIBLE else View.GONE
            isOpenModList = !isOpenModList
        }

        binding.buttonRandom.setOnClickListener {
            randomMovie = MemoryCache.cache.findMemoryCacheValue(GlobalConstants.FIREBASE_RANDOM_MOVIE_TABLE) as FbMovieDataModel
            randomMovie?.id?.let { movieID -> goToMovieDetail(movieID) }
        }

        return binding.root
    }

    private fun setAdapter() {
        adapter = GenreListItemAdapter(requireActivity(), this)
        val layoutManager = FlexboxLayoutManager(requireActivity())
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        binding.recyclerModList.layoutManager = layoutManager
        binding.recyclerModList.adapter = adapter

        adapter.submitList(getModList(requireActivity()))
    }

    private fun getFirebaseDatabaseValueList(path: String) {
        fbDatabase = FirebaseDatabase.getInstance().getReference(path)
       // modListMovie.clear()
        fbDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    when (path) {
                        GlobalConstants.FIREBASE_RANDOM_MOVIE_TABLE -> {
                            ds.getValue(FbMovieDataModel::class.java)?.let { MemoryCache.cache.setMemoryCacheValue(GlobalConstants.FIREBASE_RANDOM_MOVIE_TABLE, it) }
                        }
                        GlobalConstants.FIREBASE_MOD_LIS_MOVIE_TABLE -> {
                            ds.getValue(FbMovieDataModel::class.java)?.let { modListMovie.add(it) }
                        }
                        GlobalConstants.FIREBASE_ABOUT_TABLE -> {
                            ds.value?.let { MemoryCache.cache.setMemoryCacheValue(GlobalConstants.FIREBASE_ABOUT_TABLE, it) }
                        }
                        GlobalConstants.FIREBASE_TERM_OF_USE_TABLE -> {
                            ds.value?.let { MemoryCache.cache.setMemoryCacheValue(GlobalConstants.FIREBASE_TERM_OF_USE_TABLE, it) }
                        }
                        else -> {
                        }
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun goToMovieDetail(movieID: Long) {
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_TYPE, MediaType.movie)
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_ID, movieID)
        var intent = Intent(requireActivity(), MovieDetailActivity::class.java)
        startActivity(intent)
    }

    companion object {
        fun newInstance() = RecommendedMovieFragment()
    }

    override fun genreListItemOnClickListener(genre: Genre) {
        val movieId = genre.id.toInt() - 1
        modListMovie[movieId].id?.let {
            MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_TYPE, MediaType.movie)
            MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_ID, it)
            var intent = Intent(requireActivity(), MovieDetailActivity::class.java)
            startActivity(intent)
        }
    }
}