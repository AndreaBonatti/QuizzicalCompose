package com.example.quizzicalcompose.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizzicalcompose.data.remote.models.QuestionsListEntry
import java.util.*

/**
 * This file represent a single question
 * Every question have from 2 to 4 possible answers
 * If the user selected the correct answer the answer become green
 * and there is a "message" correct answer
 * Else the correct answer become green and the one selected by the user become red
 * */
@Composable
fun QuestionEntry(
    entry: QuestionsListEntry,
    questionNumber: Int,
    viewModel: QuestionsViewModel,
    modifier: Modifier = Modifier
) {
    var answered by rememberSaveable { mutableStateOf(false) }
    var guessed by rememberSaveable { mutableStateOf(false) }
    var selectedAnswer by rememberSaveable { mutableStateOf("") }
    Scaffold(
        topBar = { QuizzicalAppTopBar() }
    ) {
        Column(
            Modifier
                .padding(8.dp)
        ) {
            Text(
                text = "Question $questionNumber of ${viewModel.questionsList.size}".uppercase(
                    Locale.ROOT
                ),
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
                    AnswerEntry(
                        answer = answer,
                        enabled = !answered,
                        isCorrect = entry.correctAnswer == answer,
                        isSelected = selectedAnswer == answer,
                        onClick = {
                            answered = true
                            if (entry.correctAnswer == answer) guessed = true
                            selectedAnswer = answer
                        }
                    )
                    if (answer != lastAnswer) Spacer(modifier = Modifier.height(16.dp))
                }
            }
            if (answered) {
                Text(
                    text = if (guessed) "Correct answer!" else "Wrong answer!",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                Text(
                    text = "",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun AnswerEntry(
    answer: String,
    enabled: Boolean,
    isCorrect: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { onClick() },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            disabledBackgroundColor =
            if (isCorrect)
                Color.Green
            else if (isSelected && !isCorrect)
                Color.Red
            else
                MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
        )
    ) {
        Text(
            text = answer,
            color = MaterialTheme.colors.onPrimary,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center,
        )
    }
}