package com.pes.studentorganizer.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.pes.studentorganizer.navigation.Screen

/**
 * Data class representing a bottom navigation tab item.
 */
private data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

/**
 * Bottom navigation bar hosting tabs for Dashboard, Assignments, Profile, and Settings.
 *
 * @param navController The NavHostController used for navigation.
 * @param currentRoute The currently active route string for highlighting the selected tab.
 * @param modifier Optional modifier for the composable.
 */
@Composable
fun BottomBar(
    navController: NavHostController,
    currentRoute: String?,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem("Dashboard", Icons.Filled.Home, Screen.Dashboard.route),
        BottomNavItem("Assignments", Icons.AutoMirrored.Filled.List, Screen.Assignments.route),
        BottomNavItem("Profile", Icons.Filled.Person, Screen.Profile.route),
        BottomNavItem("Settings", Icons.Filled.Settings, Screen.Settings.route)
    )

    NavigationBar(modifier = modifier) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(text = item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            // Pop up to the start destination to avoid building up a large stack
                            popUpTo(Screen.Dashboard.route) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected tab
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
