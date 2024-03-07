package com.atorresveiga.gestures

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
internal fun DraggableText(modifier: Modifier = Modifier) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    val state = rememberDraggableState { delta ->
        offsetX += delta
    }
    Text(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), 0) }
            .draggable(
                orientation = Orientation.Horizontal,
                state = state
            )
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        text = "Drag me!",
        color = MaterialTheme.colorScheme.onPrimary
    )
}


@Composable
internal fun DraggableBoxLowLevel(modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier) {
        var offsetX by remember { mutableFloatStateOf(constraints.maxWidth / 2f) }
        var offsetY by remember { mutableFloatStateOf(constraints.maxHeight / 2f) }
        Box(
            Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .background(Color.Blue)
                .size(100.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                }
        )
    }
}
