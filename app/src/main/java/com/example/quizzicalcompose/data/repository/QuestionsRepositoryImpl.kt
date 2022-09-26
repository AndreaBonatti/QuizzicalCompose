package com.example.quizzicalcompose.data.repository

import com.example.quizzicalcompose.data.remote.QuizzicalApi
import com.example.quizzicalcompose.data.remote.responses.JsonData
import com.example.quizzicalcompose.domain.repository.QuestionsRepository
import com.example.quizzicalcompose.util.Resource
import javax.inject.Inject

class QuestionsRepositoryImpl @Inject constructor(
    private val api: QuizzicalApi
) : QuestionsRepository {

    override suspend fun getQuestions(amount: Int): Resource<JsonData> {
        val response = try {
            api.getQuestions(amount)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }
        return Resource.Success(response)
    }
}