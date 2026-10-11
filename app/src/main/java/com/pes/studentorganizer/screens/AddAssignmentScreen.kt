package com.pes.studentorganizer.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pes.studentorganizer.components.AssignmentForm
import com.pes.studentorganizer.data.Assignment

/**
 * Add Assignment screen — dedicated form for entering and saving a new assignment.
 * State is managed locally until saved, at which point it's passed back via callback.
 *
 * @param onSaveAssignment Callback invoked when a valid assignment is saved.
 * @param onCancel Callback invoked when the user cancels.
 * @param modifier Optional modifier.
 */
@Composable
fun AddAssignmentScreen(
    onSaveAssignment: (Assignment) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Local form state
    var title by remember { mutableStateOf("") }
    var course by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("Medium") }
    var description by remember { mutableStateOf("") }
    var reminderEnabled by remember { mutableStateOf(false) }

    // Validation logic: Save button is enabled only if required fields are filled
    val isSaveEnabled = title.isNotBlank() && course.isNotBlank() && dueDate.isNotBlank()

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
            AssignmentForm(
                title = title,
                course = course,
                dueDate = dueDate,
                priority = priority,
                description = description,
                reminderEnabled = reminderEnabled,
                onTitleChange = { title = it },
                onCourseChange = { course = it },
                onDueDateChange = { dueDate = it },
                onPriorityChange = { priority = it },
                onDescriptionChange = { description = it },
                onReminderChange = { reminderEnabled = it }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = {
                        if (isSaveEnabled) {
                            val newAssignment = Assignment(
                                id = 0, // ID is handled by the ViewModel
                                title = title,
                                course = course,
                                dueDate = dueDate,
                                priority = priority,
                                isCompleted = false,
                                description = description,
                                reminderEnabled = reminderEnabled
                            )
                            onSaveAssignment(newAssignment)
                        }
                    },
                    enabled = isSaveEnabled,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Save Assignment")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
