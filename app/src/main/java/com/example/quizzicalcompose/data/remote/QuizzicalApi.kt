package com.example.quizzicalcompose.data.remote

import com.example.quizzicalcompose.data.remote.responses.JsonData
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizzicalApi {

    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: Int
    ): JsonData
}