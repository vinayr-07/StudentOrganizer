package com.pes.studentorganizer.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

/**
 * State-hoisted reusable form component holding input fields for creating new assignments.
 * All state is passed in as parameters and changes are emitted via callbacks.
 *
 * @param title Current title field value.
 * @param course Current course field value.
 * @param dueDate Current due date field value.
 * @param priority Current selected priority ("Low", "Medium", "High").
 * @param description Current description field value.
 * @param reminderEnabled Current reminder checkbox state.
 * @param onTitleChange Callback when title changes.
 * @param onCourseChange Callback when course changes.
 * @param onDueDateChange Callback when due date changes.
 * @param onPriorityChange Callback when priority selection changes.
 * @param onDescriptionChange Callback when description changes.
 * @param onReminderChange Callback when reminder checkbox changes.
 * @param modifier Optional modifier for the composable.
 */
@Composable
fun AssignmentForm(
    title: String,
    course: String,
    dueDate: String,
    priority: String,
    description: String,
    reminderEnabled: Boolean,
    onTitleChange: (String) -> Unit,
    onCourseChange: (String) -> Unit,
    onDueDateChange: (String) -> Unit,
    onPriorityChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onReminderChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Assignment Title (required)
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            label = { Text("Assignment Title *") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // 2. Course Name/Code (required)
        OutlinedTextField(
            value = course,
            onValueChange = onCourseChange,
            label = { Text("Course Name / Code *") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // 3. Due Date (required)
        OutlinedTextField(
            value = dueDate,
            onValueChange = onDueDateChange,
            label = { Text("Due Date *") },
            singleLine = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "Select Due Date"
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        // 4. Priority Level
        Column {
            Text(
                text = "Priority Level",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val priorities = listOf("Low", "Medium", "High")
                priorities.forEach { option ->
                    Row(
                        modifier = Modifier
                            .selectable(
                                selected = priority == option,
                                onClick = { onPriorityChange(option) },
                                role = Role.RadioButton
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = priority == option,
                            onClick = null // handled by Row's selectable
                        )
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
        }

        // 5. Description / Notes
        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text("Description / Notes") },
            minLines = 3,
            maxLines = 5,
            modifier = Modifier.fillMaxWidth()
        )

        // 6. Reminder Setting
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(
                    value = reminderEnabled,
                    onValueChange = onReminderChange,
                    role = Role.Checkbox
                )
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = reminderEnabled,
                onCheckedChange = null // handled by Row's toggleable
            )
            Text(
                text = "Enable Reminder Notification",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}
