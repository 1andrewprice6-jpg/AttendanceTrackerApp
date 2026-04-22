package com.example.attendancetrackerapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.attendancetrackerapp.data.AttendanceStatus
import com.example.attendancetrackerapp.ui.animations.CountUpAnimation
import com.example.attendancetrackerapp.ui.animations.StaggeredItemReveal
import com.example.attendancetrackerapp.ui.animations.StatusBadgeAnimation
import com.example.attendancetrackerapp.ui.components.AttendanceStatCard
import com.example.attendancetrackerapp.ui.components.GradientProgressBar
import com.example.attendancetrackerapp.ui.theme.AbsentRed40
import com.example.attendancetrackerapp.ui.theme.LateAmber40
import com.example.attendancetrackerapp.ui.theme.PresentGreen40
import com.example.attendancetrackerapp.viewmodel.AttendanceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceScreen(navController: NavController, eventId: Int, viewModel: AttendanceViewModel = viewModel()) {
    val attendees by viewModel.getAttendeesForEvent(eventId).collectAsState(initial = emptyList())
    var attendeeName by remember { mutableStateOf("") }

    val presentCount = attendees.count { it.status == AttendanceStatus.PRESENT }
    val lateCount = attendees.count { it.status == AttendanceStatus.LATE }
    val absentCount = attendees.count { it.status == AttendanceStatus.ABSENT }
    val total = attendees.size
    val attendanceRate = if (total > 0) presentCount.toFloat() / total else 0f

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Attendance for Event $eventId")
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CountUpAnimation(targetCount = presentCount) { count ->
                AttendanceStatCard(
                    title = "Present",
                    value = count.toString(),
                    color = PresentGreen40,
                    modifier = Modifier.weight(1f)
                )
            }
            CountUpAnimation(targetCount = lateCount) { count ->
                AttendanceStatCard(
                    title = "Late",
                    value = count.toString(),
                    color = LateAmber40,
                    modifier = Modifier.weight(1f)
                )
            }
            CountUpAnimation(targetCount = absentCount) { count ->
                AttendanceStatCard(
                    title = "Absent",
                    value = count.toString(),
                    color = AbsentRed40,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        GradientProgressBar(progress = attendanceRate, color = PresentGreen40)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(attendees) { index, attendee ->
                StaggeredItemReveal(index = index) {
                    var expanded by remember { mutableStateOf(false) }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(text = attendee.name)
                        Spacer(modifier = Modifier.weight(1f))
                        key(attendee.status) {
                            var badgeVisible by remember { mutableStateOf(false) }
                            LaunchedEffect(Unit) { badgeVisible = true }
                            StatusBadgeAnimation(visible = badgeVisible) {
                                Text(
                                    text = attendee.status.name,
                                    modifier = Modifier.clickable { expanded = true }
                                )
                            }
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            AttendanceStatus.entries.forEach { status ->
                                DropdownMenuItem(
                                    text = { Text(status.name) },
                                    onClick = {
                                        viewModel.updateAttendee(attendee.copy(status = status))
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = attendeeName,
            onValueChange = { attendeeName = it },
            label = { Text("Attendee Name") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            viewModel.addAttendee(eventId, attendeeName, AttendanceStatus.ABSENT)
            attendeeName = ""
        }) {
            Text("Add Attendee")
        }
    }
}