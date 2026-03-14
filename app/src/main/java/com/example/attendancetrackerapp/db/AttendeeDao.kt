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
    suspend fun insertAttendee(attendee: Attendee)

    @Update
    suspend fun updateAttendee(attendee: Attendee)

    @Delete
    suspend fun deleteAttendee(attendee: Attendee)
}