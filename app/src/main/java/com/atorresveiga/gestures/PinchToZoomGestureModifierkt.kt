package com.atorresveiga.gestures

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
internal fun PinchToZoomAndRotateScreen(modifier: Modifier = Modifier) {
    // In this part of the code we are holding the current scale rotation and offset values
    // We also should pass logical initial values for these
    var offset by remember { mutableStateOf(Offset.Zero) }
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }

    // From our gesture recognizer, we update these values whenever the gesture receives a new change.
    val transformableState =
        rememberTransformableState { zoomChange, offsetChange, rotationChange ->
            scale *= zoomChange
            rotation += rotationChange
            offset += offsetChange
        }
    Box(
        // Here we are recognizing the gestures
        modifier = modifier.transformable(state = transformableState)
    ) {
        Image(
            painter = painterResource(id = R.drawable.eagle),
            contentDescription = "Eagle",
            modifier =
            Modifier
                // And finally, we apply the transformation values to our composable.
                .graphicsLayer {
                    translationX = offset.x
                    translationY = offset.y
                    scaleX = scale
                    scaleY = scale
                    rotationZ = rotation
                }
                .size(300.dp)
                .align(Alignment.Center)
        )
    }
}