package com.example.alif.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String = "default_user",
    val name: String = "",
    val level: String = "Beginner",
    val xp: Int = 0,
    val streak: Int = 0,
    val dailyGoalMins: Int = 10,
    val preferredLanguage: String = "English"
)

@Entity(tableName = "lessons")
data class Lesson(
    @PrimaryKey val id: String,
    val title: String,
    val unit: String,
    val arabicChar: String,
    val transliteration: String
)

@Entity(tableName = "progress")
data class Progress(
    @PrimaryKey val lessonId: String,
    val userId: String = "default_user",
    val completed: Boolean = false,
    val score: Int = 0,
    val lastSeen: Long = 0L
)
