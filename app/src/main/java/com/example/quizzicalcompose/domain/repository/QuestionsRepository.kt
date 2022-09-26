package com.example.quizzicalcompose.domain.repository

import com.example.quizzicalcompose.data.remote.responses.JsonData
import com.example.quizzicalcompose.util.Resource

interface QuestionsRepository {
    suspend fun getQuestions(amount: Int): Resource<JsonData>
}