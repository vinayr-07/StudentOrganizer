package com.pes.studentorganizer.data

import java.util.UUID

/**
 * Represents an active course the student is enrolled in.
 */
data class Course(
    val id: String = UUID.randomUUID().toString(),
    val courseName: String,
    val courseCode: String,
    val instructor: String,
    val progress: Float = 0f
)

/**
 * Represents a student assignment or task.
 */
data class Assignment(
    val id: Int,
    val title: String,
    val course: String,
    val dueDate: String,
    val priority: String,
    val isCompleted: Boolean = false,
    val description: String = "",
    val reminderEnabled: Boolean = false
)

/**
 * Represents the student user profile.
 */
data class Student(
    val studentName: String,
    val studentId: String,
    val major: String,
    val avatarRes: Int,
    val gpa: Float,
    val completedCredits: Int,
    val currentSemester: String
)

/**
 * Centralized default data for the Student Organizer app.
 */
object SampleData {

    val defaultStudent = Student(
        studentName = "Rahul Sharma",
        studentId = "PES1UG22CS001",
        major = "B.Tech Computer Science",
        avatarRes = 0,
        gpa = 3.82f,
        completedCredits = 84,
        currentSemester = "Fall 2026"
    )

    val defaultCourses = listOf(
        Course(
            id = "c1",
            courseName = "Data Structures",
            courseCode = "CS201",
            instructor = "Dr. Anand Kumar",
            progress = 0.65f
        ),
        Course(
            id = "c2",
            courseName = "Database Systems",
            courseCode = "CS301",
            instructor = "Prof. Meena Iyer",
            progress = 0.40f
        ),
        Course(
            id = "c3",
            courseName = "Operating Systems",
            courseCode = "CS302",
            instructor = "Dr. Rajesh Nair",
            progress = 0.80f
        ),
        Course(
            id = "c4",
            courseName = "Discrete Mathematics",
            courseCode = "MATH201",
            instructor = "Dr. Sunita Rao",
            progress = 0.55f
        )
    )

    val defaultAssignments = listOf(
        Assignment(
            id = 1,
            title = "Binary Tree Traversal Implementation",
            course = "CS201",
            dueDate = "2026-10-08",
            priority = "High",
            isCompleted = false,
            description = "Implement in-order, pre-order, and post-order traversals."
        ),
        Assignment(
            id = 2,
            title = "ER Diagram for Library System",
            course = "CS301",
            dueDate = "2026-10-10",
            priority = "Medium",
            isCompleted = false,
            description = "Design a complete ER diagram with normalization."
        ),
        Assignment(
            id = 3,
            title = "Process Scheduling Simulation",
            course = "CS302",
            dueDate = "2026-10-12",
            priority = "High",
            isCompleted = false,
            description = "Simulate Round Robin and SJF algorithms."
        ),
        Assignment(
            id = 4,
            title = "Graph Theory Problem Set",
            course = "MATH201",
            dueDate = "2026-10-15",
            priority = "Medium",
            isCompleted = false,
            description = "Complete exercises 5.1 through 5.12."
        ),
        Assignment(
            id = 5,
            title = "SQL Query Optimization Lab",
            course = "CS301",
            dueDate = "2026-10-18",
            priority = "Low",
            isCompleted = true,
            description = "Optimize 10 given queries using indexing strategies."
        ),
        Assignment(
            id = 6,
            title = "Linked List Operations Report",
            course = "CS201",
            dueDate = "2026-10-05",
            priority = "Low",
            isCompleted = true,
            description = "Write a report comparing array-based and linked-list implementations."
        ),
        Assignment(
            id = 7,
            title = "Semaphore Implementation Lab",
            course = "CS302",
            dueDate = "2026-10-20",
            priority = "Medium",
            isCompleted = false,
            description = "Implement producer-consumer using semaphores in C."
        )
    )
}
