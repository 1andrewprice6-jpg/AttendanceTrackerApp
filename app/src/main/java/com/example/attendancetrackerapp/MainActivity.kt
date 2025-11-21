package com.example.attendancetrackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.attendancetrackerapp.ui.theme.AttendanceTrackerAppTheme
import com.example.attendancetrackerapp.ui.EventListScreen
import com.example.attendancetrackerapp.ui.AttendanceScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AttendanceTrackerAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "event_list") {
                        composable("event_list") {
                            EventListScreen(navController = navController)
                        }
                        composable("attendance/{eventId}") { backStackEntry ->
                            val eventId = backStackEntry.arguments?.getString("eventId")?.toInt() ?: 0
                            AttendanceScreen(navController = navController, eventId = eventId)
                        }
                    }
                }
            }
        }
    }
}