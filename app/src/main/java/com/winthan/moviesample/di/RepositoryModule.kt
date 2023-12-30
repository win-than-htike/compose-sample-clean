package com.winthan.moviesample.di

import com.winthan.data.repositories.MovieRepositoryImpl
import com.winthan.domain.repositories.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMovieRepository(
        movieRepository: MovieRepositoryImpl
    ): MovieRepository

}