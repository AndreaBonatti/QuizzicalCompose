package com.example.quizzicalcompose.ui

import com.example.quizzicalcompose.data.remote.models.QuestionsListEntry
import com.example.quizzicalcompose.util.Constants.QUESTIONS_NUMBER

data class GameUiState(
    val score: Int = 0,
    val isGameOver: Boolean = false,
    val currentQuestionCount: Int = 1,
    val questionsNumber: Int = QUESTIONS_NUMBER,
    val questionsList: List<QuestionsListEntry> = emptyList()
)