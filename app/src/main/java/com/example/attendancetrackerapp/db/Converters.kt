package com.example.attendancetrackerapp.db

import androidx.room.TypeConverter
import com.example.attendancetrackerapp.data.AttendanceStatus

class Converters {
    @TypeConverter
    fun fromAttendanceStatus(value: AttendanceStatus): String {
        return value.name
    }

    @TypeConverter
    fun toAttendanceStatus(value: String): AttendanceStatus {
        return AttendanceStatus.valueOf(value)
    }
}
