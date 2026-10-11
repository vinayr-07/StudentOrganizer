package com.pes.studentorganizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pes.studentorganizer.components.AppBar
import com.pes.studentorganizer.components.BottomBar
import com.pes.studentorganizer.data.Assignment
import com.pes.studentorganizer.data.Course
import com.pes.studentorganizer.data.Student
import com.pes.studentorganizer.navigation.Screen
import com.pes.studentorganizer.screens.AddAssignmentScreen
import com.pes.studentorganizer.screens.AssignmentsScreen
import com.pes.studentorganizer.screens.DashboardScreen
import com.pes.studentorganizer.screens.ProfileScreen
import com.pes.studentorganizer.screens.SettingsScreen
import com.pes.studentorganizer.ui.theme.StudentOrganizerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initial setup
        enableEdgeToEdge()
        setContent {
            val viewModel: MainViewModel = viewModel()
            val isDarkMode by viewModel.isDarkMode.collectAsState()

            StudentOrganizerTheme(darkTheme = isDarkMode) {
                StudentOrganizerApp(viewModel = viewModel)
            }
        }
    }
}

/**
 * Root composable that sets up the Scaffold with NavHost, BottomBar, AppBar, and FAB.
 */
@Composable
fun StudentOrganizerApp(
    viewModel: MainViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val assignments by viewModel.assignments.collectAsState()
    val courses by viewModel.courses.collectAsState()
    val student by viewModel.student.collectAsState()

    // Determine which screens show the bottom bar
    val showBottomBar = currentRoute in listOf(
        Screen.Dashboard.route,
        Screen.Assignments.route,
        Screen.Profile.route,
        Screen.Settings.route
    )

    // Determine which screens show the FAB
    val showFab = currentRoute in listOf(
        Screen.Dashboard.route,
        Screen.Assignments.route
    )

    // Determine the AppBar title based on the current route
    val appBarTitle = when (currentRoute) {
        Screen.Dashboard.route -> "Student Organizer"
        Screen.Assignments.route -> "My Assignments"
        Screen.Profile.route -> "Student Profile"
        Screen.Settings.route -> "Settings"
        Screen.AddAssignment.route -> "Add New Assignment"
        else -> "Student Organizer"
    }

    // Determine if the current screen supports back navigation
    val canNavigateBack = currentRoute == Screen.AddAssignment.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBar(
                title = appBarTitle,
                canNavigateBack = canNavigateBack,
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            if (showBottomBar) {
                BottomBar(
                    navController = navController,
                    currentRoute = currentRoute
                )
            }
        },
        floatingActionButton = {
            if (showFab) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.AddAssignment.route) {
                            launchSingleTop = true
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add new assignment"
                    )
                }
            }
        }
    ) { innerPadding ->
        StudentOrganizerNavHost(
            navController = navController,
            viewModel = viewModel,
            assignments = assignments,
            courses = courses,
            student = student,
            isDarkMode = isDarkMode,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

/**
 * Navigation host containing all screen destinations.
 */
@Composable
fun StudentOrganizerNavHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    assignments: List<Assignment>,
    courses: List<Course>,
    student: Student,
    isDarkMode: Boolean,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route,
        modifier = modifier
    ) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                studentName = student.studentName,
                courses = courses,
                assignments = assignments,
                onToggleComplete = { viewModel.toggleAssignmentComplete(it) }
            )
        }
        composable(Screen.Assignments.route) {
            AssignmentsScreen(
                assignments = assignments,
                onToggleComplete = { viewModel.toggleAssignmentComplete(it) }
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                student = student
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                isDarkMode = isDarkMode,
                onDarkModeToggle = { viewModel.toggleDarkMode(it) }
            )
        }
        composable(Screen.AddAssignment.route) {
            AddAssignmentScreen(
                onSaveAssignment = { newAssignment ->
                    viewModel.addAssignment(newAssignment)
                    navController.popBackStack()
                },
                onCancel = {
                    navController.popBackStack()
                }
            )
        }
    }
}