package com.example.quizzicalcompose.di

import com.example.quizzicalcompose.data.repository.QuestionsRepositoryImpl
import com.example.quizzicalcompose.domain.repository.QuestionsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsQuestionsRepository(
        repository: QuestionsRepositoryImpl
    ): QuestionsRepository
}