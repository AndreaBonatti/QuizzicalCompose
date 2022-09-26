package com.example.quizzicalcompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizzicalcompose.data.remote.models.QuestionsListEntry
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
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    QuestionEntry(entry = viewModel.questionsList[0], viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun QuestionEntry(
    entry: QuestionsListEntry,
    modifier: Modifier = Modifier,
    viewModel: QuestionsViewModel
) {
    Scaffold(
        topBar = { QuizzicalAppTopBar() }
    ) {
        Column(
            Modifier
                .padding(8.dp)
        ) {
            Text(
                text = "Question 1 of 5".uppercase(java.util.Locale.ROOT),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                elevation = 4.dp,
                modifier = modifier.weight(1f)
            ) {
                Text(
                    text = entry.question,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .wrapContentHeight(Alignment.CenterVertically)
                )
            }

            Column(
                modifier.weight(3f)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                val lastAnswer = entry.answers.last()
                for (answer in entry.answers) {
                    Button(onClick = { /*TODO*/ }) {
                        Text(
                            text = answer,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            textAlign = TextAlign.Center,
                        )
                    }
                    if (answer != lastAnswer) Spacer(modifier = Modifier.height(16.dp))
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