package com.example.quizzicalcompose.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizzicalcompose.data.remote.models.QuestionsListEntry
import com.example.quizzicalcompose.domain.repository.QuestionsRepository
import com.example.quizzicalcompose.util.Constants.QUESTIONS_NUMBER
import com.example.quizzicalcompose.util.Resource
import com.example.quizzicalcompose.util.htmlToString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val repository: QuestionsRepository
) : ViewModel() {
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var questionsList = listOf<QuestionsListEntry>()

    init {
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
                        questionsList = randomQuestions
                        Log.d("RETROFIT", questionsList.toString())
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
}