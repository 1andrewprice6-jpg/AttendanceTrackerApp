package com.example.attendancetrackerapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val AttendanceDarkColorScheme = darkColorScheme(
    primary = TrackBlue80,
    secondary = PresentGreen80,
    tertiary = LateAmber80,
    background = DarkTrackBackground,
    surface = DarkTrackSurface,
    surfaceVariant = DarkTrackSurfaceVariant,
    onPrimary = TrackBlue20,
    onBackground = TrackSurface,
    onSurface = TrackSurface,
)

private val AttendanceLightColorScheme = lightColorScheme(
    primary = TrackBlue40,
    secondary = PresentGreen40,
    tertiary = LateAmber40,
    background = TrackBackground,
    surface = TrackSurface,
    surfaceVariant = TrackSurfaceVariant,
    onPrimary = TrackSurface,
    onBackground = TrackBlue20,
    onSurface = TrackBlue20,
)

@Composable
fun AttendanceTrackerAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> AttendanceDarkColorScheme
        else -> AttendanceLightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                colorScheme.primary.luminance() > 0.5f
        }
    }
    MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
