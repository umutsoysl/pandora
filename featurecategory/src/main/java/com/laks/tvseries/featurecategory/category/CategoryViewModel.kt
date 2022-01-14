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

class CategoryViewModel(var categoryRepo: CategoryRepository?) : BaseViewModel(categoryRepo) {

    val trendingMovieList = MutableLiveData<DiscoverMovieListModel>()

    val popularPeopleList = MutableLiveData<PersonModel>()

    val shimmerVisible = MutableLiveData<Boolean>(true)

    val allMovieList = MutableLiveData<DiscoverMovieListModel>()

    fun getTrendingList(type: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val requestModel = GlobalRequestModel()
                requestModel.type = type
                requestModel.time = Time.day
               categoryRepo?.getTrending(requestModel)?.collect {
                   when(type) {
                       MediaType.movie -> trendingMovieList.postValue(it)
                       MediaType.tv -> allMovieList.postValue(it)
                   }
                   shimmerVisible.postValue(false)
               }
            }
        }
    }

    fun getPopularMovieList(page: Int? = 1) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val requestModel = GlobalRequestModel()
                requestModel.page = page
                categoryRepo?.getMoviePopular(requestModel)?.collect {
                    allMovieList.postValue(it)
                    shimmerVisible.postValue(false)
                }
            }
        }
    }


    fun getNowPlayingMovieList(page: Int? = 1) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val requestModel = GlobalRequestModel()
                requestModel.page = page
                categoryRepo?.getNowPlaying(requestModel)?.collect {
                    allMovieList.postValue(it)
                    shimmerVisible.postValue(false)
                }
            }
        }
    }

    fun getPopularTvShowList(page: Int? = 1)  {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val requestModel = GlobalRequestModel()
                requestModel.page = page
                categoryRepo?.getTvPopularShows(requestModel)?.collect {
                    allMovieList.postValue(it)
                    shimmerVisible.postValue(false)
                }
            }
        }
    }

    fun getPopularPeopleList(page: Int? = 1) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val requestModel = GlobalRequestModel()
                requestModel.page = page
                categoryRepo?.getPopularPeople(requestModel)?.collect {
                    popularPeopleList.postValue(it)
                    shimmerVisible.postValue(false)
                }
            }
        }
    }

    fun getUpComingMovieList(page: Int? = 1) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val requestModel = GlobalRequestModel()
                requestModel.page = page
                categoryRepo?.getUpComingMovie(requestModel)?.collect {
                    allMovieList.postValue(it)
                    shimmerVisible.postValue(false)
                }
            }
        }
    }

    fun getTopRatedMovieList(page: Int? = 1) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val requestModel = GlobalRequestModel()
                requestModel.page = page
                categoryRepo?.getMovieTopRated(requestModel)?.collect {
                    allMovieList.postValue(it)
                    shimmerVisible.postValue(false)
                }
            }
        }
    }

    fun getNetflixTvShows(page: Int? = 1) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val requestModel = GlobalRequestModel()
                requestModel.page = page
                requestModel.movieID = "213"
                categoryRepo?.getNetworkTvShows(requestModel)?.collect {
                    allMovieList.postValue(it)
                    shimmerVisible.postValue(false)
                }
            }
        }
    }

    fun getAppleTvShows(page: Int? = 1) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val requestModel = GlobalRequestModel()
                requestModel.page = page
                requestModel.movieID = "2552"
                categoryRepo?.getNetworkTvShows(requestModel)?.collect {
                    allMovieList.postValue(it)
                    shimmerVisible.postValue(false)
                }
            }
        }
    }

    fun getAmazonTvShows(page: Int? = 1) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val requestModel = GlobalRequestModel()
                requestModel.page = page
                requestModel.movieID = "1024"
                categoryRepo?.getNetworkTvShows(requestModel)?.collect {
                    allMovieList.postValue(it)
                    shimmerVisible.postValue(false)
                }
            }
        }
    }

    fun getDisneyTvShows(page: Int? = 1) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val requestModel = GlobalRequestModel()
                requestModel.page = page
                requestModel.movieID = "2739"
                categoryRepo?.getNetworkTvShows(requestModel)?.collect {
                    allMovieList.postValue(it)
                    shimmerVisible.postValue(false)
                }
            }
        }
    }

    fun getCollectionMediaList(listID: Int? = 1) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (listID != null) {
                    categoryRepo?.getCollectionList(listID)?.collect {
                        allMovieList.postValue(it)
                        shimmerVisible.postValue(false)
                    }
                }
            }
        }
    }
}