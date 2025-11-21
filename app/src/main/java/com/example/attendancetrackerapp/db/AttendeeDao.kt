package com.example.attendancetrackerapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.attendancetrackerapp.data.Attendee
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendeeDao {
    @Query("SELECT * FROM attendees WHERE eventId = :eventId")
    fun getAttendeesForEvent(eventId: Int): Flow<List<Attendee>>

    @Insert
    fun insertAttendee(attendee: Attendee)

    @Update
    fun updateAttendee(attendee: Attendee)

    @Delete
    fun deleteAttendee(attendee: Attendee)
}