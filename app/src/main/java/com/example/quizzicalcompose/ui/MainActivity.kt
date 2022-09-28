package com.example.quizzicalcompose.ui

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizzicalcompose.R
import com.example.quizzicalcompose.data.remote.models.QuestionsListEntry
import com.example.quizzicalcompose.ui.components.QuestionEntry
import com.example.quizzicalcompose.ui.theme.QuizzicalComposeTheme
import com.example.quizzicalcompose.util.Constants.QUESTIONS_NUMBER
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizzicalComposeTheme {
                val gameViewModel = hiltViewModel<GameViewModel>()
                val isLoading = gameViewModel.isLoading.value
                val gameUiState by gameViewModel.uiState.collectAsState()
                Scaffold(
                    topBar = { QuizzicalAppTopBar() }
                ) {
                    if (isLoading) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            GameStatus(
                                questionCount = gameUiState.currentQuestionCount,
                                questionsNumber = QUESTIONS_NUMBER,
                                score = gameUiState.score
                            )
                            GameLayout(
                                currentQuestion = gameUiState.questionsList[gameUiState.currentQuestionCount - 1],
                                questionNumber = gameUiState.currentQuestionCount,
                                nextQuestion = { gameViewModel.nextQuestion() },
                                checkUserAnswer = { gameViewModel.checkUserAnswer(it) }
                            )
                        }

                    }
//                    HomeScreen()
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
            painter = painterResource(id = R.drawable.ic_app_logo),
            contentDescription = "Quiz icons created by Freepik"
        )
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.h1
        )
    }
}

@Composable
fun GameStatus(
    questionCount: Int,
    questionsNumber: Int,
    score: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .size(48.dp),
    ) {
        Text(
            text = stringResource(R.string.question_count, questionCount, questionsNumber),
            fontSize = 18.sp,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            text = stringResource(R.string.score, score),
            fontSize = 18.sp,
        )
    }
}

@Composable
fun GameLayout(
    currentQuestion: QuestionsListEntry,
    questionNumber: Int,
    nextQuestion: () -> Unit,
    checkUserAnswer: (String) -> Unit,
) {
    QuestionEntry(
        entry = currentQuestion,
        questionNumber = questionNumber,
        nextQuestion = { nextQuestion() },
        checkUserAnswer = checkUserAnswer
    )
}