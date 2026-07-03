package com.example.alif.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.alif.data.Lesson
import com.example.alif.ui.MainViewModel
import com.example.alif.ui.Practice

@Composable
fun LearnScreen(viewModel: MainViewModel, navController: NavController) {
    val lessons by viewModel.lessons.collectAsState()
    val progress by viewModel.progress.collectAsState()

    val user by viewModel.user.collectAsState()
    val unitTitle = when (user?.level) {
        "Intermediate" -> "Vowels & Connecting"
        "Advanced" -> "Roots & Grammar"
        else -> "Arabic Alphabet"
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Unit 1", color = MaterialTheme.colorScheme.primary)
        Text(unitTitle, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(lessons) { lesson ->
                val isCompleted = progress.any { it.lessonId == lesson.id && it.completed }
                LessonCard(lesson, isCompleted) {
                    navController.navigate(Practice(lesson.id))
                }
            }
        }
    }
}

@Composable
fun LessonCard(lesson: Lesson, isCompleted: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCompleted) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(50.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(lesson.arabicChar, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(lesson.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(lesson.transliteration, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            if (isCompleted) {
                Icon(Icons.Default.CheckCircle, contentDescription = "Completed", tint = MaterialTheme.colorScheme.primary)
            } else {
                Icon(Icons.Default.PlayArrow, contentDescription = "Start", tint = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}
