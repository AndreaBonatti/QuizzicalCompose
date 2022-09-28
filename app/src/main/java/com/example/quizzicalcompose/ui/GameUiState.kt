package com.example.quizzicalcompose.ui

import com.example.quizzicalcompose.data.remote.models.QuestionsListEntry

data class GameUiState(
    val score: Int = 0,
    val isGameOver: Boolean = false,
    val currentQuestionCount: Int = 1,
    val questionsList: List<QuestionsListEntry> = emptyList()
)