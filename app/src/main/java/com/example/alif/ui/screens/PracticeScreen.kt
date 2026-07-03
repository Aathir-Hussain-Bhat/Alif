package com.example.alif.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.alif.ui.AiTutor

@Composable
fun PracticeScreen(navController: NavController) {
    var mode by remember { mutableStateOf("Listen") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("Listen", "Speak", "Write", "Quiz").forEach { m ->
                FilterChip(
                    selected = mode == m,
                    onClick = { mode = m },
                    label = { Text(m) }
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Box(
            modifier = Modifier
                .size(200.dp)
                .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(32.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("ا", fontSize = 120.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Alif", fontSize = 32.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.weight(1f))

        when (mode) {
            "Listen" -> {
                Button(
                    onClick = { /* Simulate audio */ },
                    modifier = Modifier.size(80.dp),
                    shape = RoundedCornerShape(40.dp)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Play Audio", modifier = Modifier.size(40.dp))
                }
            }
            "Speak" -> {
                Button(
                    onClick = { /* Simulate recording */ },
                    modifier = Modifier.size(80.dp),
                    shape = RoundedCornerShape(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Icon(Icons.Default.Mic, contentDescription = "Record", modifier = Modifier.size(40.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { navController.navigate(AiTutor) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text("Ask AI about this lesson", modifier = Modifier.padding(8.dp), color = MaterialTheme.colorScheme.onSecondary)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Complete Lesson", modifier = Modifier.padding(8.dp))
        }
    }
}
