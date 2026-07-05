package com.example.alif.ui

import kotlinx.serialization.Serializable

@Serializable
object Splash

@Serializable
object Welcome

@Serializable
object Auth

@Serializable
object Onboarding

@Serializable
object Plan

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
