package com.example.alif.ui

import kotlinx.serialization.Serializable

@Serializable
object Splash

@Serializable
object Onboarding

@Serializable
object Main

@Serializable
object Learn

@Serializable
data class Practice(val lessonId: String)

@Serializable
data class AiTutor(val topic: String = "General")

@Serializable
object Progress

@Serializable
object Profile
