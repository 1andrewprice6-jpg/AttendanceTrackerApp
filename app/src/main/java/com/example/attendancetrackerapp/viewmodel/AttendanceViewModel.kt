package com.example.attendancetrackerapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.attendancetrackerapp.data.Attendee
import com.example.attendancetrackerapp.data.AttendanceStatus
import com.example.attendancetrackerapp.data.Event
import com.example.attendancetrackerapp.db.AppDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

class AttendanceViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "attendance_db"
    ).build()

    private val eventDao = db.eventDao()
    private val attendeeDao = db.attendeeDao()

    val events: StateFlow<List<Event>> = eventDao.getAllEvents()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val attendeeFlows = ConcurrentHashMap<Int, StateFlow<List<Attendee>>>()

    fun getAttendeesForEvent(eventId: Int): StateFlow<List<Attendee>> =
        attendeeFlows.getOrPut(eventId) {
            attendeeDao.getAttendeesForEvent(eventId)
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
        }

    fun addEvent(name: String, date: String) {
        viewModelScope.launch {
            try {
                eventDao.insertEvent(Event(name = name, date = date))
            } catch (e: Exception) {
                Log.e(TAG, "Failed to insert event", e)
            }
        }
    }

    fun addAttendee(eventId: Int, name: String, status: AttendanceStatus) {
        viewModelScope.launch {
            try {
                attendeeDao.insertAttendee(Attendee(eventId = eventId, name = name, status = status))
            } catch (e: Exception) {
                Log.e(TAG, "Failed to insert attendee", e)
            }
        }
    }

    fun updateAttendee(attendee: Attendee) {
        viewModelScope.launch {
            try {
                attendeeDao.updateAttendee(attendee)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to update attendee", e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        db.close()
    }

    companion object {
        private const val TAG = "AttendanceViewModel"
    }
}