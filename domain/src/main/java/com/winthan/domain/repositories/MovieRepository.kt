package com.winthan.domain.repositories

import com.winthan.domain.Resource
import com.winthan.domain.models.Movie

interface MovieRepository {

    suspend fun getNowPlayingMovies(): Resource<List<Movie>>

}