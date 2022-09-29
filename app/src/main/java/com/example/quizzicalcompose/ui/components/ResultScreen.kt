package com.example.quizzicalcompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizzicalcompose.R

@Composable
fun ResultScreen(
    finalScore: Int,
    goToStartScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_app_logo),
            contentDescription = "Quiz icons created by Freepik"
        )
        Spacer(modifier = Modifier.height(72.dp))
        Text(
            text = "Final score: $finalScore",
            style = MaterialTheme.typography.h1
        )
        Spacer(modifier = Modifier.height(72.dp))
        Button(
            onClick = { goToStartScreen() }
        ) {
            Text(
                text = "Start screen",
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center,
            )
        }
    }
}