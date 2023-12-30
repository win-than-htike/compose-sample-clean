package com.winthan.data.repositories

import android.util.Log
import com.winthan.data.datasource.LocalDataSource
import com.winthan.data.network.ApiService
import com.winthan.data.response.dto.toDomain
import com.winthan.domain.Resource
import com.winthan.domain.models.Movie
import com.winthan.domain.repositories.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: ApiService,
) : MovieRepository {

    override suspend fun getNowPlayingMovies(): Resource<List<Movie>> {
        return try {
            val response = remoteDataSource.getNowPlayingMovies().results?.map {
                it.toDomain()
            }.orEmpty()
            Resource.Success(response)
        } catch (e: Exception) {
            Log.e("MovieRepositoryImpl", e.localizedMessage ?: "Unknown error")
            Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }

}