package com.flow.hugh.di

import com.flow.hugh.repository.MovieRepository
import com.flow.hugh.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindVolunteerRepository(
        repositoryImpl: MovieRepositoryImpl
    ): MovieRepository
}