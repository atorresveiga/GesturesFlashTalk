package com.atorresveiga.gestures

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt


@Composable
internal fun SwitchScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        var isChecked by remember { mutableStateOf(false) }
        Text(
            text = "Current value: $isChecked",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 32.dp)
        )

        SwitchAnchoredDraggableSample(
            checked = isChecked,
            onCheckedChange = { newValue -> isChecked = newValue },
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SwitchAnchoredDraggableSample(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val width = 96.dp
    val squareSize = 48.dp
    val density = LocalDensity.current
    val anchors = with(density) {
        DraggableAnchors {
            false at 0f
            true at (width - squareSize).toPx()
        }
    }

    val anchoredDraggableState = remember {
        AnchoredDraggableState(
            initialValue = checked,
            anchors = anchors,
            positionalThreshold = { totalDistance -> totalDistance * 0.5f },
            velocityThreshold = { with(density) { 125.dp.toPx() } },
            animationSpec = tween(),
            confirmValueChange = { newValue ->
                onCheckedChange(newValue)
                true
            }
        )
    }

    LaunchedEffect(checked) {
        if (checked != anchoredDraggableState.currentValue) {
            anchoredDraggableState.animateTo(checked)
        }
    }

    val switchColor by animateColorAsState(
        targetValue = if (checked) {
            MaterialTheme.colorScheme.primary
        } else {
            Color.DarkGray
        },
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearEasing
        ), label = "color_animation"
    )

    Box(
        modifier = modifier
            .width(width)
            .clickable { onCheckedChange(!checked) }
            .anchoredDraggable(
                state = anchoredDraggableState,
                orientation = Orientation.Horizontal
            )
            .background(Color.LightGray)
    ) {
        Box(
            Modifier
                .offset { IntOffset(anchoredDraggableState.offset.roundToInt(), 0) }
                .size(squareSize)
                .background(switchColor)
        )
    }
}