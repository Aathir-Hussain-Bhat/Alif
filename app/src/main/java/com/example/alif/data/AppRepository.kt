package com.example.alif.data

import kotlinx.coroutines.flow.Flow

class AppRepository(private val dao: AppDao) {
    val currentUser: Flow<User?> = dao.getUser()
    val allLessons: Flow<List<Lesson>> = dao.getAllLessons()
    val userProgress: Flow<List<Progress>> = dao.getUserProgress()

    suspend fun saveUser(user: User) {
        dao.insertUser(user)
    }

    suspend fun populateInitialData() {
        val lessons = listOf(
            Lesson("l1", "Alif", "Unit 1", "ا", "Alif"),
            Lesson("l2", "Ba", "Unit 1", "ب", "Ba"),
            Lesson("l3", "Ta", "Unit 1", "ت", "Ta"),
            Lesson("l4", "Tha", "Unit 1", "ث", "Tha")
        )
        dao.insertLessons(lessons)
    }

    suspend fun markLessonComplete(lessonId: String, score: Int = 100) {
        val progress = Progress(
            lessonId = lessonId,
            completed = true,
            score = score,
            lastSeen = System.currentTimeMillis()
        )
        dao.insertProgress(progress)
    }
}
