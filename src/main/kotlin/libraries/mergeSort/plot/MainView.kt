@file:Suppress("FunctionNaming")
package libraries.mergeSort

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import homework.homework4and5.SHARE_THREADS_RESOURCE

const val RANGE_LIMIT = 128f

private fun checkedSortingMode(sortingMode: SortingMode?) = sortingMode
    ?: throw java.lang.IllegalStateException("Sorting mode not found.")

@Composable
private fun PlotsButtons(
    onClickShowTimeFromThreadsDependency: (SortingMode) -> Unit,
    onClickShowTimeFromSizesDependency: (SortingMode) -> Unit,
    selectedSortingMode: SortingMode?
) {
    Button(onClick = { onClickShowTimeFromThreadsDependency(checkedSortingMode(selectedSortingMode)) }) {
        Text("Show time from threads dependency")
    }
    Button(onClick = { onClickShowTimeFromSizesDependency(checkedSortingMode(selectedSortingMode)) }) {
        Text("Show time from sizes dependency")
    }
}

@Composable
fun MainView(
    onClickGenerateRandomList: (Int) -> MutableList<Int>,
    onClickShowTimeFromThreadsDependency: (SortingMode) -> Unit,
    onClickShowTimeFromSizesDependency: (SortingMode) -> Unit
) {
    val sortingModeMap = mapOf("Multithreaded" to SortingMode.THREADS, "Coroutines" to SortingMode.COROUTINES)
    val radioOptions = sortingModeMap.keys.toList()
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    val (selectedSortingMode, onSortingModeSelected) = remember { mutableStateOf(sortingModeMap[selectedOption]) }
    var listSize by remember { mutableStateOf(0) }
    var list by remember { mutableStateOf(mutableListOf<Int>()) }
    var listState by remember { mutableStateOf("Generated List: ") }
    MaterialTheme {
        Column(
            Modifier.fillMaxSize().padding(5.dp).verticalScroll(rememberScrollState()),
            Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "List size: $listSize", fontWeight = FontWeight.Bold)
            Slider(
                value = listSize.toFloat(),
                onValueChange = { listSize = it.toInt() },
                valueRange = 0f..RANGE_LIMIT,
                onValueChangeFinished = {
                    list = onClickGenerateRandomList(listSize)
                    listState = "Generated List: "
                }
            )
            Text(listState, fontWeight = FontWeight.Bold)
            Text(list.toString())
            radioOptions.forEach { text ->
                Row(
                    Modifier.fillMaxWidth().selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            onSortingModeSelected(sortingModeMap[text])
                        }
                    ).padding(all = Dp(value = 8F)),
                    horizontalArrangement = Arrangement.Center
                ) {
                    RadioButton(
                        selected = (text == selectedOption), modifier = Modifier.padding(all = Dp(value = 8F)),
                        onClick = {
                            onOptionSelected(text)
                            onSortingModeSelected(sortingModeMap[text])
                        }
                    )
                    Text(
                        text = text,
                        modifier = Modifier.padding(start = 32.dp)
                    )
                }
            }
            Button(onClick = {
                listState = "Sorted List: "
                list.mergeSort(list.size / SHARE_THREADS_RESOURCE + 1, sortingMode = SortingMode.THREADS)
            }) { Text("SORT LIST") }
            PlotsButtons(onClickShowTimeFromThreadsDependency, onClickShowTimeFromSizesDependency, selectedSortingMode)
        }
    }
}
