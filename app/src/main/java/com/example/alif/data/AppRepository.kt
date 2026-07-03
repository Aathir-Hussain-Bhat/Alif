package com.example.alif.data

import kotlinx.coroutines.flow.Flow

class AppRepository(private val dao: AppDao) {
    val currentUser: Flow<User?> = dao.getUser()
    val allLessons: Flow<List<Lesson>> = dao.getAllLessons()
    val userProgress: Flow<List<Progress>> = dao.getUserProgress()

    suspend fun saveUser(user: User) {
        dao.insertUser(user)
    }

    suspend fun populateInitialData(level: String = "Beginner") {
        dao.clearLessons()
        val lessons = when (level) {
            "Intermediate" -> listOf(
                Lesson("l1", "Tashkeel", "Unit 1", "َ ِ ُ", "Vowels"),
                Lesson("l2", "Sun Letters", "Unit 1", "ش", "Shamsiyyah"),
                Lesson("l3", "Moon Letters", "Unit 1", "ق", "Qamariyyah"),
                Lesson("l4", "Connecting", "Unit 1", "ـبـ", "Joined Ba")
            )
            "Advanced" -> listOf(
                Lesson("l1", "Root Words", "Unit 1", "ك ت ب", "K-T-B"),
                Lesson("l2", "Verb Forms", "Unit 1", "يَفْعَلُ", "Form I"),
                Lesson("l3", "Plurals", "Unit 1", "ـون", "Masculine Plural"),
                Lesson("l4", "Case Endings", "Unit 1", "ـاً", "Accusative")
            )
            else -> listOf(
                Lesson("l1", "Alif", "Unit 1", "ا", "Alif"),
                Lesson("l2", "Ba", "Unit 1", "ب", "Ba"),
                Lesson("l3", "Ta", "Unit 1", "ت", "Ta"),
                Lesson("l4", "Tha", "Unit 1", "ث", "Tha")
            )
        }
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
