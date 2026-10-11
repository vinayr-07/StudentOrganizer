package com.pes.studentorganizer.navigation

/**
 * Sealed class defining all navigation routes in the Student Organizer app.
 * Each object represents a distinct screen destination.
 */
sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Assignments : Screen("assignments")
    object Profile : Screen("profile")
    object Settings : Screen("settings")
    object AddAssignment : Screen("add_assignment")

}
