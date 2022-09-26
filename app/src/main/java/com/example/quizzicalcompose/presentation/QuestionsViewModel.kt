package com.example.quizzicalcompose.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizzicalcompose.domain.repository.QuestionsRepository
import com.example.quizzicalcompose.util.Constants.QUESTIONS_NUMBER
import com.example.quizzicalcompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val repository: QuestionsRepository
) : ViewModel() {
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getQuestions(QUESTIONS_NUMBER)
            when (result) {
                is Resource.Success -> {
                    Log.d("RETROFIT", result.data?.results.toString())
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