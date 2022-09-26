package com.example.quizzicalcompose.di

import com.example.quizzicalcompose.data.remote.QuizzicalApi
import com.example.quizzicalcompose.data.repository.QuestionsRepositoryImpl
import com.example.quizzicalcompose.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Lifecycle
object AppModule {

    @Provides
    @Singleton // Scope
    fun providesQuestionsApi(): QuizzicalApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(QuizzicalApi::class.java)
    }
}