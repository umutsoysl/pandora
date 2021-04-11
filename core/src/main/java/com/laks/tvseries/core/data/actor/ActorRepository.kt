package com.laks.tvseries.core.data.actor

import com.laks.tvseries.core.base.service.BaseRepository
import com.laks.tvseries.core.data.model.MovieCreditsModel
import com.laks.tvseries.core.data.model.GlobalRequestModel
import com.laks.tvseries.core.data.model.actor.ActorDetailModel
import kotlinx.coroutines.flow.Flow

class ActorRepository: BaseRepository<ActorApi>(ActorApi::class.java) {

    fun getActorDetail(requestModel: GlobalRequestModel): Flow<ActorDetailModel?> {
        return fetchData {
            api.getActorDetail(authToken = requestModel.apiKey!!, personID = requestModel.actorID!!, language = requestModel.language!!)
        }
    }

    fun getActorBackDropImage(requestModel: GlobalRequestModel): Flow<MovieCreditsModel?> {
        return fetchData(isLoadingShown = true) {
            api.getActorBackDropImage(authToken = requestModel.apiKey!!, personID = requestModel.actorID!!, language = requestModel.language!!)
        }
    }

    fun getActorMovies(requestModel: GlobalRequestModel): Flow<MovieCreditsModel?> {
        return fetchData(isLoadingShown = true) {
            api.getActorMovies(authToken = requestModel.apiKey!!, personID = requestModel.actorID!!, language = requestModel.language!!)
        }
    }

    fun getActorTv(requestModel: GlobalRequestModel): Flow<MovieCreditsModel?> {
        return fetchData(isLoadingShown = true) {
            api.getActorTv(authToken = requestModel.apiKey!!, personID = requestModel.actorID!!, language = requestModel.language!!)
        }
    }
}