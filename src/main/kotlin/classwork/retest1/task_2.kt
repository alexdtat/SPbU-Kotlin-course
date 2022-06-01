package classwork.retest1

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import libraries.bashOrgQuotes.mainView.MainView

const val WIDTH = 1280
const val HEIGHT = 720

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "bash.org quotes",
        state = rememberWindowState(width = WIDTH.dp, height = HEIGHT.dp)
    ) {
        MainView()
    }
}
