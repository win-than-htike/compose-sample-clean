package com.winthan.data.network

import com.winthan.data.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "ADD YOUR API KEY HERE"
const val BASE_URL = "https://api.themoviedb.org/3/"

interface ApiService {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY,
    ): MovieResponse

}