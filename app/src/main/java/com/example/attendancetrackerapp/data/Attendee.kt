package com.example.attendancetrackerapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attendees")
data class Attendee(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val eventId: Int,
    val name: String,
    val status: AttendanceStatus
)

enum class AttendanceStatus {
    PRESENT,
    ABSENT,
    LATE
}