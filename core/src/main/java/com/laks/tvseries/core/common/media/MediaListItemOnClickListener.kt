package com.laks.tvseries.core.common.media

import com.laks.tvseries.core.data.model.MovieModel

interface MediaListItemOnClickListener {
    fun mediaListItemOnClickListener(scheduleInfo: MovieModel)
}