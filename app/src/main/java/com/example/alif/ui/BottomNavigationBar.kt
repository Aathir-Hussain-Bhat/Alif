package com.example.alif.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

sealed class BottomNavItem(val route: Any, val icon: ImageVector, val label: String) {
    object HomeRoute : BottomNavItem(Main, Icons.Default.Home, "Home")
    object LearnRoute : BottomNavItem(Learn, Icons.AutoMirrored.Filled.List, "Learn")
    object AiRoute : BottomNavItem(AiTutor, Icons.Default.Star, "AI Tutor")
    object ProgressRoute : BottomNavItem(Progress, Icons.Default.PlayArrow, "Progress")
    object ProfileRoute : BottomNavItem(Profile, Icons.Default.Person, "Profile")
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.HomeRoute,
        BottomNavItem.LearnRoute,
        BottomNavItem.AiRoute,
        BottomNavItem.ProgressRoute,
        BottomNavItem.ProfileRoute
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { item ->
            val routeString = item.route::class.qualifiedName ?: ""
            val isSelected = currentDestination?.hierarchy?.any { it.route?.contains(routeString) == true } == true

            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
