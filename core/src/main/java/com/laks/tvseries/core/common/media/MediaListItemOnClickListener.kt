package com.laks.tvseries.core.common.media

import com.laks.tvseries.core.data.db.DBMediaEntity
import com.laks.tvseries.core.data.model.CastObject
import com.laks.tvseries.core.data.model.Genre
import com.laks.tvseries.core.data.model.MovieModel

interface MediaListItemOnClickListener {
    fun mediaListItemOnClickListener(scheduleInfo: MovieModel)
}

interface CastListItemOnClickListener {
    fun castListItemOnClickListener(cast: CastObject)
}

interface DBFavoriteListItemClickListener {
    fun mediaListItemOnClickListener(cast: DBMediaEntity)
}

interface GenreListItemOnClickListener {
    fun genreListItemOnClickListener(genre: Genre)
}