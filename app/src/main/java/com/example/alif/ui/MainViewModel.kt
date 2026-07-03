package com.example.alif.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alif.data.AppRepository
import com.example.alif.data.User
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.first

class MainViewModel(private val repository: AppRepository) : ViewModel() {

    val user: StateFlow<User?> = repository.currentUser
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val lessons = repository.allLessons
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
        
    val progress = repository.userProgress
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            if (repository.allLessons.first().isEmpty()) {
                repository.populateInitialData()
            }
        }
    }

    fun completeOnboarding(reason: String, level: String, dailyGoal: Int, lang: String) {
        viewModelScope.launch {
            repository.saveUser(
                User(
                    level = level,
                    dailyGoalMins = dailyGoal,
                    preferredLanguage = lang
                )
            )
            repository.populateInitialData(level)
        }
    }

    fun completeLesson(lessonId: String) {
        viewModelScope.launch {
            repository.markLessonComplete(lessonId)
            // Grant XP and Streak
            val currentUser = user.value ?: User()
            repository.saveUser(
                currentUser.copy(
                    xp = currentUser.xp + 50,
                    streak = currentUser.streak + 1
                )
            )
        }
    }
}
