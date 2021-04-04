package com.laks.tvseries.featureactor.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.laks.tvseries.core.base.viewmodel.BaseViewModel
import com.laks.tvseries.core.data.actor.ActorRepository
import com.laks.tvseries.core.data.model.CastObject
import com.laks.tvseries.core.data.model.MovieCreditsModel
import com.laks.tvseries.core.data.model.MovieRequestModel
import com.laks.tvseries.core.data.model.actor.ActorDetailModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActorDetailViewModel(var actorRepository: ActorRepository?) : BaseViewModel(actorRepository) {

    var actorDetailModel = MutableLiveData<ActorDetailModel>()
    var actorBackDropImages = MutableLiveData<ArrayList<String>>()
    var movieCreditsModel = MutableLiveData<ArrayList<CastObject>>()
    var tvCreditsModel = MutableLiveData<ArrayList<CastObject>>()
    var movieCreditsShimmer = MutableLiveData<Boolean>(true)
    var tvCreditsShimmer = MutableLiveData<Boolean>(true)

    private val _moreClickEvent = MutableLiveData<Unit>()
    val moreButtonClickEvent: LiveData<Unit> = _moreClickEvent

    fun moreButtonOnClickListener() {
        _moreClickEvent.value = Unit
    }

    fun getActorDetail(actorID: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var requestModel = MovieRequestModel()
                requestModel.actorID = actorID
                actorRepository?.getActorDetail(requestModel)?.collect {
                    actorDetailModel.postValue(it)
                    getActorBackDropImages(requestModel)
                    getActorMovies(requestModel)
                    getActorTv(requestModel)
                }
            }
        }
    }

    private fun getActorBackDropImages(requestModel: MovieRequestModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                actorRepository?.getActorBackDropImage(requestModel)?.collect {
                    actorBackDropImages.postValue(getImageList(it?.cast!!))
                }
            }
        }
    }

    private fun getActorMovies(requestModel: MovieRequestModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                actorRepository?.getActorMovies(requestModel)?.collect {
                    movieCreditsModel.postValue(it?.cast!!)
                    movieCreditsShimmer.postValue(false)
                }
            }
        }
    }

    private fun getActorTv(requestModel: MovieRequestModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                actorRepository?.getActorTv(requestModel)?.collect {
                    it?.cast?.forEach { cast ->
                        cast.isMovie = false
                    }
                    tvCreditsModel.postValue(it?.cast!!)
                    tvCreditsShimmer.postValue(false)
                }
            }
        }
    }

    private fun getImageList(list: ArrayList<CastObject>): ArrayList<String> {
        var imageList = ArrayList<String>()

        list.forEachIndexed { index, cast ->
            cast.backdropPath?.let {imageList.add(it)}
            if (index > 5) {
                return imageList
            }
        }
        return imageList
    }
}