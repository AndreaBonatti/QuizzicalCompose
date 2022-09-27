package com.example.quizzicalcompose.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizzicalcompose.data.remote.models.QuestionsListEntry
import com.example.quizzicalcompose.domain.repository.QuestionsRepository
import com.example.quizzicalcompose.util.Constants.QUESTIONS_NUMBER
import com.example.quizzicalcompose.util.Constants.SCORE_INCREASE
import com.example.quizzicalcompose.util.Resource
import com.example.quizzicalcompose.util.htmlToString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: QuestionsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    var currentAnswer by mutableStateOf("")
        private set

    init {
        resetGame()
    }

    fun resetGame() {
        loadQuestions()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getQuestions(QUESTIONS_NUMBER)
            when (result) {
                is Resource.Success -> {
                    if (result.data?.responseCode != 0) {
                        Log.d("RETROFIT", "API error!")
                        when (result.data?.responseCode) {
                            // This error messages came from the api docs:
                            // https://opentdb.com/api_config.php
                            1 -> Log.d("RETROFIT", "Code 1: No results!")
                            2 -> Log.d("RETROFIT", "Code 2: Invalid parameters!")
                            3 -> Log.d("RETROFIT", "Code 3: Token not found!")
                            4 -> Log.d("RETROFIT", "Code 4: Token empty!")
                        }
                    } else {
                        Log.d("RETROFIT", result.data.results.toString())
                        val randomQuestions = result.data.results.mapIndexed { index, entry ->
                            val answers = mutableListOf<String>()
                            answers.addAll(entry.incorrectAnswers)
                            answers.add(entry.correctAnswer)
                            answers.shuffle()
                            QuestionsListEntry(
                                question = htmlToString(entry.question),
                                answers = answers.map { htmlToString(it) },
                                correctAnswer = htmlToString(entry.correctAnswer)
                            )
                        }
                        Log.d("RETROFIT", randomQuestions.toString())
                        _uiState.update { currentState ->
                            currentState.copy(questionsList = randomQuestions)
                        }
                    }
                    loadError.value = ""
                    isLoading.value = false
                }
                is Resource.Error -> {
                    Log.d("RETROFIT", result.message!!.toString())
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }

    fun updateCurrentAnswer(newAnswer: String) {
        currentAnswer = newAnswer
    }

    fun checkUserAnswer() {
        if (currentAnswer == _uiState.value.questionsList[_uiState.value.currentQuestionCount - 1].correctAnswer) {
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            nextQuestion(updatedScore)
        }
    }

    fun skipQuestion() {
        nextQuestion(_uiState.value.score)
        // reset user answer
        updateCurrentAnswer("")
    }

    fun nextQuestion(updatedScore: Int) {
        if (_uiState.value.currentQuestionCount < QUESTIONS_NUMBER) {
            // There are other questions => go to the next one
            _uiState.update { currentState ->
                currentState.copy(
                    score = updatedScore,
                    currentQuestionCount = currentState.currentQuestionCount + 1
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    score = updatedScore,
                    isGameOver = true
                )
                //TODO go to result screen
            }
        }
        // TODO ?updateCurrentAnswer("")
    }
}