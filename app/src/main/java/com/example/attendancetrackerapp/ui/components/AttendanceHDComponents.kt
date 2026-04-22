package com.example.attendancetrackerapp.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AttendanceStatCard(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        if (pressed) 0.96f else 1f,
        spring(Spring.DampingRatioMediumBouncy),
        label = "stat_scale"
    )
    Card(
        modifier = modifier.graphicsLayer { scaleX = scale; scaleY = scale }
            .shadow(6.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.15f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold, color = color, fontSize = 32.sp
            ))
            Spacer(Modifier.height(4.dp))
            Text(title, style = MaterialTheme.typography.bodyMedium.copy(color = color.copy(0.8f)))
        }
    }
}

@Composable
fun ShimmerAttendanceRow(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val alpha by transition.animateFloat(
        0.25f, 0.75f, infiniteRepeatable(tween(900), RepeatMode.Reverse), "shimmer_alpha"
    )
    Row(modifier = modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(48.dp).clip(RoundedCornerShape(50)).background(MaterialTheme.colorScheme.onSurface.copy(alpha)))
        Spacer(Modifier.width(12.dp))
        Column {
            Box(Modifier.fillMaxWidth(0.6f).height(14.dp).clip(RoundedCornerShape(7.dp)).background(MaterialTheme.colorScheme.onSurface.copy(alpha)))
            Spacer(Modifier.height(8.dp))
            Box(Modifier.fillMaxWidth(0.4f).height(10.dp).clip(RoundedCornerShape(5.dp)).background(MaterialTheme.colorScheme.onSurface.copy(alpha * 0.7f)))
        }
    }
}

@Composable
fun GradientProgressBar(progress: Float, color: Color, modifier: Modifier = Modifier) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(1000, easing = FastOutSlowInEasing),
        label = "progress"
    )
    Box(modifier = modifier.fillMaxWidth().height(12.dp).clip(RoundedCornerShape(6.dp))
        .background(color.copy(alpha = 0.2f))) {
        Box(modifier = Modifier.fillMaxWidth(animatedProgress).fillMaxHeight()
            .clip(RoundedCornerShape(6.dp))
            .background(Brush.horizontalGradient(listOf(color, color.copy(alpha = 0.7f)))))
    }
}
