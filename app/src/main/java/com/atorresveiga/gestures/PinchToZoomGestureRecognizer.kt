package com.atorresveiga.gestures

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
internal fun PinchToZoomScreen(modifier: Modifier = Modifier) {
    var offset by remember { mutableStateOf(Offset.Zero) }
    var zoom by remember { mutableStateOf(1f) }
    Box(
        modifier = modifier.pointerInput(Unit) {
            detectTransformGestures { centroid, pan, gestureZoom, _ ->
                offset = offset.calculateNewOffset(centroid, pan, zoom, gestureZoom)
                zoom = maxOf(1f, zoom * gestureZoom)
            }
        }
    ) {
        Image(
            painter = painterResource(id = R.drawable.eagle),
            contentDescription = "Eagle",
            modifier =
            Modifier
                .graphicsLayer {
                    translationX = -offset.x * zoom
                    translationY = -offset.y * zoom
                    scaleX = zoom
                    scaleY = zoom
                    transformOrigin = TransformOrigin(0f, 0f)
                }
                .size(300.dp)
                .align(Alignment.Center)
        )
    }
}

fun Offset.calculateNewOffset(
    centroid: Offset,
    pan: Offset,
    zoom: Float,
    gestureZoom: Float
): Offset {
    val newScale = maxOf(1f, zoom * gestureZoom)
    return (this + centroid / zoom) - (centroid / newScale + pan / zoom)
}