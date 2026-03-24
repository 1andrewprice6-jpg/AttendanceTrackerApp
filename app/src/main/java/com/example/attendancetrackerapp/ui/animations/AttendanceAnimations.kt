package com.example.attendancetrackerapp.ui.animations

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

val slideInFromRight: EnterTransition = fadeIn(tween(280)) +
    slideInHorizontally(tween(280)) { it / 3 }
val slideOutToLeft: ExitTransition = fadeOut(tween(200)) +
    slideOutHorizontally(tween(200)) { -it / 3 }
val slideInFromLeft: EnterTransition = fadeIn(tween(280)) +
    slideInHorizontally(tween(280)) { -it / 3 }
val slideOutToRight: ExitTransition = fadeOut(tween(200)) +
    slideOutHorizontally(tween(200)) { it / 3 }

@Composable
fun CountUpAnimation(
    targetCount: Int,
    durationMs: Int = 800,
    content: @Composable (Int) -> Unit
) {
    var displayCount by remember { mutableIntStateOf(0) }
    LaunchedEffect(targetCount) {
        val steps = 20
        val stepDelay = (durationMs / steps).toLong()
        for (i in 1..steps) {
            kotlinx.coroutines.delay(stepDelay)
            displayCount = (targetCount * i / steps)
        }
        displayCount = targetCount
    }
    content(displayCount)
}

@Composable
fun StatusBadgeAnimation(
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMedium)) + fadeIn(tween(200)),
        exit = scaleOut(tween(150)) + fadeOut(tween(150)),
        modifier = modifier,
        content = content
    )
}

@Composable
fun StaggeredItemReveal(index: Int, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(index * 50L)
        visible = true
    }
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(300)) + slideInVertically(tween(300)) { it / 3 },
        modifier = modifier
    ) { content() }
}
