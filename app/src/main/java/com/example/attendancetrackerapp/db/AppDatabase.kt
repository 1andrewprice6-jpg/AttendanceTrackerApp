package com.example.attendancetrackerapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.attendancetrackerapp.data.Attendee
import com.example.attendancetrackerapp.data.Event

@Database(entities = [Event::class, Attendee::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
    abstract fun attendeeDao(): AttendeeDao
}