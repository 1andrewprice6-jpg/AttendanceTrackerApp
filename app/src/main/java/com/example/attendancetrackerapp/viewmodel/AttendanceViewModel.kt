package com.example.attendancetrackerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.attendancetrackerapp.data.Attendee
import com.example.attendancetrackerapp.data.AttendanceStatus
import com.example.attendancetrackerapp.data.Event
import com.example.attendancetrackerapp.db.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AttendanceViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java, "attendance-db"
    ).build()

    val events: Flow<List<Event>> = db.eventDao().getAllEvents()

    fun getAttendeesForEvent(eventId: Int): Flow<List<Attendee>> {
        return db.attendeeDao().getAttendeesForEvent(eventId)
    }

    fun addEvent(name: String, date: String) {
        viewModelScope.launch {
            db.eventDao().insertEvent(Event(name = name, date = date))
        }
    }

    fun addAttendee(eventId: Int, name: String, status: AttendanceStatus) {
        viewModelScope.launch {
            db.attendeeDao().insertAttendee(Attendee(eventId = eventId, name = name, status = status))
        }
    }

    fun updateAttendee(attendee: Attendee) {
        viewModelScope.launch {
            db.attendeeDao().updateAttendee(attendee)
        }
    }
}