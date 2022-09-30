package com.example.quizzicalcompose.ui

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.quizzicalcompose.R
import com.example.quizzicalcompose.ui.components.HomeScreen
import com.example.quizzicalcompose.ui.components.QuestionEntry
import com.example.quizzicalcompose.ui.components.ResultScreen
import com.example.quizzicalcompose.util.Constants

// Navigation destinations
enum class QuizzicalApp {
    Start,
    Game,
    Result
}


@Composable
fun QuizzicalApp(
    gameViewModel: GameViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    // Game UI
    val isLoading = gameViewModel.isLoading.value
    val gameUiState by gameViewModel.uiState.collectAsState()

    // Navigation parameters
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: QuizzicalApp.Start.name

    Scaffold(
        topBar = { QuizzicalAppTopBar() }
    ) {
        NavHost(
            navController = navController,
            startDestination = QuizzicalApp.Start.name,
            modifier = modifier.padding(8.dp)
        ) {
            composable(QuizzicalApp.Start.name) {
                HomeScreen(
                    onStartButtonClicked = {
                        gameViewModel.resetGame()
                        navController.navigate(QuizzicalApp.Game.name){
                            popUpTo(QuizzicalApp.Start.name) { inclusive = true }
                        }
                    }
                )
            }
            composable(QuizzicalApp.Game.name) {
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
                            questionsNumber = Constants.QUESTIONS_NUMBER,
                            score = gameUiState.score
                        )
                        QuestionEntry(
                            entry = gameUiState.questionsList[gameUiState.currentQuestionCount - 1],
                            questionNumber = gameUiState.currentQuestionCount,
                            nextQuestion = { gameViewModel.nextQuestion() },
                            checkUserAnswer = { gameViewModel.checkUserAnswer(it) },
                            goToResultScreen = { navController.navigate(QuizzicalApp.Result.name){
                                popUpTo(QuizzicalApp.Game.name) { inclusive = true }
                            } }
                        )
                    }
                }
            }
            composable(QuizzicalApp.Result.name) {
                ResultScreen(
                    finalScore = gameUiState.score,
                    goToStartScreen = {
                        navController.navigate(QuizzicalApp.Start.name) {
                            popUpTo(QuizzicalApp.Result.name) { inclusive = true }
                        }
                    }
                )
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
            .size(24.dp),
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