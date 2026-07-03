package com.example.alif.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateNext: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000)
        onNavigateNext()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "ا",
                fontSize = 120.sp,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "ALIF",
                fontSize = 40.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                letterSpacing = 8.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Learn Arabic with AI",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
fun OnboardingScreen(onComplete: (String, String, Int, String) -> Unit) {
    var reason by remember { mutableStateOf("") }
    var level by remember { mutableStateOf("Beginner") }
    var dailyGoal by remember { mutableStateOf(10) }
    var lang by remember { mutableStateOf("English") }
    var step by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (step) {
            1 -> {
                Text("Why are you learning Arabic?", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(24.dp))
                val reasons = listOf("Travel", "Religion", "Work", "Family", "Other")
                reasons.forEach { r ->
                    Button(
                        onClick = {
                            reason = r
                            step++
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(r, modifier = Modifier.padding(8.dp))
                    }
                }
            }
            2 -> {
                Text("What is your level?", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(24.dp))
                val levels = listOf("Beginner", "Intermediate", "Advanced")
                levels.forEach { l ->
                    Button(
                        onClick = {
                            level = l
                            step++
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(l, modifier = Modifier.padding(8.dp))
                    }
                }
            }
            3 -> {
                Text("Daily Goal?", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(24.dp))
                val goals = listOf(5, 10, 20, 30)
                goals.forEach { g ->
                    Button(
                        onClick = {
                            dailyGoal = g
                            onComplete(reason, level, dailyGoal, lang)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("$g minutes/day", modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}
