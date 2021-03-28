package com.laks.tvseries.featurecategory.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.laks.tvseries.core.base.viewmodel.BaseViewModel
import com.laks.tvseries.core.data.category.CategoryRepository
import com.laks.tvseries.core.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrendCategoryViewModel(var categoryRepo: CategoryRepository?) : BaseViewModel(categoryRepo) {

    val trendingMovieList = MutableLiveData<DiscoverMovieListModel>()
    val nowPlayingMovieList = MutableLiveData<DiscoverMovieListModel>()
    val upComingMovieList = MutableLiveData<DiscoverMovieListModel>()

    val trendingTvList = MutableLiveData<DiscoverMovieListModel>()
    val popularTvShowList = MutableLiveData<DiscoverMovieListModel>()

    val popularPeopleList = MutableLiveData<PersonModel>()

    val shimmerVisible = MutableLiveData<Boolean>(true)

    fun getTrendingList(type: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var requestModel = MovieRequestModel()
                requestModel.type = type
                requestModel.time = Time.day
               categoryRepo?.getTrending(requestModel)?.collect {
                   when(type) {
                       MediaType.movie -> trendingMovieList.postValue(it)
                       MediaType.tv -> trendingTvList.postValue(it)
                   }
                   shimmerVisible.postValue(false)
               }
            }
        }
    }


    fun getNowPlayingMovieList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var requestModel = MovieRequestModel()
                categoryRepo?.getNowPlaying(requestModel)?.collect {
                    nowPlayingMovieList.postValue(it)
                    shimmerVisible.postValue(false)
                }
            }
        }
    }

    fun getPopularTvShowList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var requestModel = MovieRequestModel()
                categoryRepo?.getTvPopularShows(requestModel)?.collect {
                    popularTvShowList.postValue(it)
                    shimmerVisible.postValue(false)
                }
            }
        }
    }

    fun getPopularPeopleList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                categoryRepo?.getPopularPeople()?.collect {
                    popularPeopleList.postValue(it)
                    shimmerVisible.postValue(false)
                }
            }
        }
    }

    fun getUpComingMovieList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                categoryRepo?.getUpComingMovie()?.collect {
                    upComingMovieList.postValue(it)
                    shimmerVisible.postValue(false)
                }
            }
        }
    }
}