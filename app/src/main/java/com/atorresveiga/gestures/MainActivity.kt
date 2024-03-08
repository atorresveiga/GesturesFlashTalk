package com.atorresveiga.gestures

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.atorresveiga.gestures.ui.theme.GesturesFlashTalkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GesturesFlashTalkTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GesturesExamplesScreen(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}


@Composable
fun GesturesExamplesScreen(modifier: Modifier = Modifier) {
    var currentScreen by remember { mutableStateOf(Screens.ExamplesListScreen) }
    BackHandler {
        currentScreen = Screens.ExamplesListScreen
    }
    val onExampleChange: (screen: Screens) -> Unit = { screen ->
        currentScreen = screen
    }

    Box(modifier = modifier) {
        when (currentScreen) {
            Screens.ExamplesListScreen -> ExamplesList(
                onExampleChange = onExampleChange
            )

            Screens.DraggableTextScreen -> DraggableText()
            Screens.DraggableBoxScreen -> DraggableBoxLowLevel(modifier = Modifier.fillMaxSize())
            Screens.SwitchScreen -> SwitchScreen(modifier = Modifier.fillMaxSize())
            Screens.PinchToZoom -> PinchToZoomScreen(modifier = Modifier.fillMaxSize())
        }
    }

}

@Composable
fun ExamplesList(onExampleChange: (screen: Screens) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(modifier = Modifier) {
        itemsIndexed(items = Screens.entries.filter { it != Screens.ExamplesListScreen }) { i, item ->
            if (i != 0) Divider()
            Text(
                text = item.title,
                modifier = Modifier
                    .clickable { onExampleChange(item) }
                    .padding(16.dp)
                    .fillMaxWidth()

            )
        }
    }
}

enum class Screens(val title: String) {
    ExamplesListScreen("List of examples"),
    DraggableTextScreen("Draggable text with horizontal orientation"),
    DraggableBoxScreen("Draggable box low level"),
    PinchToZoom("Pinch to zoom image"),
    SwitchScreen("Switch control with anchored draggable")
}