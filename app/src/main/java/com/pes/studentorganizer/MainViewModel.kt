package com.pes.studentorganizer

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pes.studentorganizer.data.Assignment
import com.pes.studentorganizer.data.Course
import com.pes.studentorganizer.data.SampleData
import com.pes.studentorganizer.data.Student
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("student_organizer_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    private val _student = MutableStateFlow(loadStudent())
    val student: StateFlow<Student> = _student.asStateFlow()

    private val _courses = MutableStateFlow(loadCourses())
    val courses: StateFlow<List<Course>> = _courses.asStateFlow()

    private val _assignments = MutableStateFlow(loadAssignments())
    val assignments: StateFlow<List<Assignment>> = _assignments.asStateFlow()

    private val _isDarkMode = MutableStateFlow(prefs.getBoolean("isDarkMode", false))
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    fun toggleDarkMode(isDark: Boolean) {
        _isDarkMode.value = isDark
        prefs.edit { putBoolean("isDarkMode", isDark) }
    }

    fun addAssignment(assignment: Assignment) {
        _assignments.update { current ->
            val updated = current + assignment.copy(id = UUID.randomUUID().hashCode())
            saveAssignments(updated)
            updated
        }
    }

    fun toggleAssignmentComplete(id: Int) {
        _assignments.update { current ->
            val updated = current.map {
                if (it.id == id) it.copy(isCompleted = !it.isCompleted) else it
            }
            saveAssignments(updated)
            updated
        }
    }

    // --- SharedPreferences Helpers ---

    private fun saveStudent(student: Student) {
        prefs.edit { putString("student", gson.toJson(student)) }
    }

    private fun loadStudent(): Student {
        val json = prefs.getString("student", null)
        return if (json != null) {
            gson.fromJson(json, Student::class.java)
        } else {
            SampleData.defaultStudent
        }
    }

    private fun saveCourses(courses: List<Course>) {
        prefs.edit { putString("courses", gson.toJson(courses)) }
    }

    private fun loadCourses(): List<Course> {
        val json = prefs.getString("courses", null)
        val loaded = if (json != null) {
            val type = object : TypeToken<List<Course>>() {}.type
            gson.fromJson<List<Course>>(json, type)
        } else null
        return if (loaded.isNullOrEmpty()) SampleData.defaultCourses else loaded
    }

    private fun saveAssignments(assignments: List<Assignment>) {
        prefs.edit { putString("assignments", gson.toJson(assignments)) }
    }

    private fun loadAssignments(): List<Assignment> {
        val json = prefs.getString("assignments", null)
        val loaded = if (json != null) {
            val type = object : TypeToken<List<Assignment>>() {}.type
            gson.fromJson<List<Assignment>>(json, type)
        } else null
        return if (loaded.isNullOrEmpty()) SampleData.defaultAssignments else loaded
    }
}
