package com.winthan.domain.usecases

import com.winthan.domain.repositories.MovieRepository
import javax.inject.Inject

class NowPlayingMovieUseCases @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke() = movieRepository.getNowPlayingMovies()

}