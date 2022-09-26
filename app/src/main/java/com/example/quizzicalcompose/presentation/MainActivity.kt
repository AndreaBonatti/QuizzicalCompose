package com.example.quizzicalcompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizzicalcompose.presentation.QuestionsViewModel
import com.example.quizzicalcompose.ui.theme.QuizzicalComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizzicalComposeTheme {
                val viewModel = hiltViewModel<QuestionsViewModel>()
            }
        }
    }
}
