package com.example.alif.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.alif.ui.screens.*

@Composable
fun AppNavigation(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    
    val showBottomNav = currentDestination?.contains(Main::class.qualifiedName ?: "") == true ||
            currentDestination?.contains(AiTutor::class.qualifiedName ?: "") == true ||
            currentDestination?.contains(Progress::class.qualifiedName ?: "") == true ||
            currentDestination?.contains(Profile::class.qualifiedName ?: "") == true

    Scaffold(
        bottomBar = {
            if (showBottomNav) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Splash,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Splash> {
                val user by viewModel.user.collectAsState()
                SplashScreen(
                    onNavigateNext = {
                        if (user?.name?.isNotEmpty() == true) {
                            navController.navigate(Main) {
                                popUpTo(Splash) { inclusive = true }
                            }
                        } else {
                            navController.navigate(Onboarding) {
                                popUpTo(Splash) { inclusive = true }
                            }
                        }
                    }
                )
            }
            
            composable<Onboarding> {
                OnboardingScreen(
                    onComplete = { reason, level, dailyGoal, lang ->
                        viewModel.completeOnboarding(reason, level, dailyGoal, lang)
                        navController.navigate(Main) {
                            popUpTo(Onboarding) { inclusive = true }
                        }
                    }
                )
            }

            composable<Main> {
                HomeScreen(viewModel, navController)
            }

            composable<Learn> {
                LearnScreen(viewModel, navController)
            }
            
            composable<Practice> {
                PracticeScreen(navController)
            }

            composable<AiTutor> {
                AiTutorScreen()
            }
            
            composable<Progress> {
                ProgressScreen(viewModel)
            }
            
            composable<Profile> {
                ProfileScreen(viewModel)
            }
        }
    }
}
