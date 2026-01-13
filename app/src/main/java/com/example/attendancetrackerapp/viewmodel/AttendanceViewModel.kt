package com.example.attendancetrackerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.attendancetrackerapp.data.Attendee
import com.example.attendancetrackerapp.data.AttendanceStatus
import com.example.attendancetrackerapp.data.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class AttendanceViewModel(application: Application) : AndroidViewModel(application) {

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events.asStateFlow()

    private val _attendees = MutableStateFlow<Map<Int, List<Attendee>>>(emptyMap())

    private var nextEventId = 1
    private var nextAttendeeId = 1

    fun getAttendeesForEvent(eventId: Int): StateFlow<List<Attendee>> {
        val attendeeList = _attendees.value[eventId] ?: emptyList()
        return MutableStateFlow(attendeeList).asStateFlow()
    }

    private fun generateAuthToken(): String {
        return UUID.randomUUID().toString().substring(0, 8)
    }

    fun addEvent(name: String, date: String) {
        val authToken = generateAuthToken()
        val newEvent = Event(id = nextEventId++, name = name, date = date, authToken = authToken)
        _events.value = _events.value + newEvent
    }

    fun addAttendee(eventId: Int, name: String, status: AttendanceStatus) {
        val newAttendee = Attendee(
            id = nextAttendeeId++,
            eventId = eventId,
            name = name,
            status = status
        )
        val currentAttendees = _attendees.value[eventId] ?: emptyList()
        _attendees.value = _attendees.value + (eventId to (currentAttendees + newAttendee))
    }

    fun updateAttendee(attendee: Attendee) {
        val currentAttendees = _attendees.value[attendee.eventId] ?: emptyList()
        val updatedAttendees = currentAttendees.map {
            if (it.id == attendee.id) attendee else it
        }
        _attendees.value = _attendees.value + (attendee.eventId to updatedAttendees)
    }
}