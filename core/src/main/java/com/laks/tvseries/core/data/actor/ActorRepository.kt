package com.laks.tvseries.core.data.actor

import com.laks.tvseries.core.base.service.BaseRepository
import com.laks.tvseries.core.data.model.MovieCreditsModel
import com.laks.tvseries.core.data.model.MovieRequestModel
import com.laks.tvseries.core.data.model.actor.ActorDetailModel
import kotlinx.coroutines.flow.Flow

class ActorRepository: BaseRepository<ActorApi>(ActorApi::class.java) {

    fun getActorDetail(requestModel: MovieRequestModel): Flow<ActorDetailModel?> {
        return fetchData {
            api.getActorDetail(authToken = requestModel.apiKey!!, personID = requestModel.actorID!!, language = requestModel.language!!)
        }
    }

    fun getActorBackDropImage(requestModel: MovieRequestModel): Flow<MovieCreditsModel?> {
        return fetchData(isLoadingShown = true) {
            api.getActorBackDropImage(authToken = requestModel.apiKey!!, personID = requestModel.actorID!!, language = requestModel.language!!)
        }
    }

    fun getActorMovies(requestModel: MovieRequestModel): Flow<MovieCreditsModel?> {
        return fetchData(isLoadingShown = true) {
            api.getActorMovies(authToken = requestModel.apiKey!!, personID = requestModel.actorID!!, language = requestModel.language!!)
        }
    }

    fun getActorTv(requestModel: MovieRequestModel): Flow<MovieCreditsModel?> {
        return fetchData(isLoadingShown = true) {
            api.getActorTv(authToken = requestModel.apiKey!!, personID = requestModel.actorID!!, language = requestModel.language!!)
        }
    }
}