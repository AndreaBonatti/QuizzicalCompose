package com.example.quizzicalcompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizzicalcompose.ui.theme.QuizzicalComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizzicalComposeTheme {
                val viewModel = hiltViewModel<QuestionsViewModel>()
                val isLoading = viewModel.isLoading.value
                Scaffold(
                    topBar = { QuizzicalAppTopBar() }
                ) {
//                    if (isLoading) {
//                        Column(
//                            modifier = Modifier.fillMaxSize(),
//                            verticalArrangement = Arrangement.Center,
//                            horizontalAlignment = Alignment.CenterHorizontally
//                        ) {
//                            CircularProgressIndicator()
//                        }
//                    } else {
//                        QuestionEntry(
//                            entry = viewModel.questionsList[0],
//                            questionNumber = 1,
//                            viewModel = viewModel
//                        )
//                    }
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun QuizzicalAppTopBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.primary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(64.dp)
                .padding(8.dp),
            painter = painterResource(id = com.example.quizzicalcompose.R.drawable.ic_app_logo),
            contentDescription = "Quiz icons created by Freepik"
        )
        Text(
            text = stringResource(com.example.quizzicalcompose.R.string.app_name),
            style = MaterialTheme.typography.h1
        )
    }
}