package homework.homework4and5

import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import libraries.mergeSort.MainView
import libraries.mergeSort.plot.ViewModel

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Multithreaded & [soon] coroutines merge sort",
        state = rememberWindowState(width = 1000.dp, height = 500.dp)
    ) {
        val viewModel = remember { ViewModel() }
        MainView(
            viewModel::showTimeOnThreads,
            viewModel::showTimeOnSizes
        )
    }
}
