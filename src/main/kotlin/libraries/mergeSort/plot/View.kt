@file:Suppress("FunctionNaming")
package libraries.mergeSort

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
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
import kotlin.math.pow

private object Constants {
    const val SIZE_RANGE_LIMIT = 25000f
    const val PARALLELING_RESOURCE_POWER_LIMIT = 12f
}

private fun checkedSortingMode(sortingMode: SortingMode?) = sortingMode
    ?: throw java.lang.IllegalStateException("Sorting mode not found.")

@Composable
private fun PlotsButtons(
    onClickShowTimeOnThreadsDependence: (Int, Int, SortingMode) -> Unit,
    onClickShowTimeOnSizesDependence: (Int, Int, SortingMode) -> Unit,
    parallelingResourcePower: Int,
    listSize: Int,
    selectedSortingMode: SortingMode?
) {
    val sortingModeText = if (selectedSortingMode == SortingMode.THREADS) "threads" else "coroutines"
    Button(onClick = {
        onClickShowTimeOnThreadsDependence(
            parallelingResourcePower,
            listSize,
            checkedSortingMode(selectedSortingMode)
        )
    }) {
        Text("Show time on $sortingModeText dependence")
    }
    Button(onClick = {
        onClickShowTimeOnSizesDependence(
            parallelingResourcePower,
            listSize,
            checkedSortingMode(selectedSortingMode)
        )
    }) {
        Text("Show time on sizes dependence")
    }
}

@Composable
fun MainView(
    onClickShowTimeOnParallelingDependence: (Int, Int, SortingMode) -> Unit,
    onClickShowTimeOnSizesDependence: (Int, Int, SortingMode) -> Unit
) {
    val sortingModeMap = mapOf("Multithreaded" to SortingMode.THREADS, "Coroutines" to SortingMode.COROUTINES)
    val radioOptions = sortingModeMap.keys.toList()
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    val (selectedSortingMode, onSortingModeSelected) = remember { mutableStateOf(sortingModeMap[selectedOption]) }
    var listSize by remember { mutableStateOf(1) }
    var parallelingResourcePower by remember { mutableStateOf(0) }
    val sortingModeText = if (selectedSortingMode == SortingMode.THREADS) "Threads" else "Coroutines"
    val parallelingText = "$sortingModeText: 2^$parallelingResourcePower = ${2.0.pow(parallelingResourcePower).toInt()}"
    MaterialTheme {
        Column(
            Modifier.fillMaxSize().padding(5.dp),
            Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "List size: $listSize", fontWeight = FontWeight.Bold)
            Slider(
                value = listSize.toFloat(),
                onValueChange = { listSize = it.toInt() },
                valueRange = 1f..Constants.SIZE_RANGE_LIMIT
            )
            Text(text = parallelingText, fontWeight = FontWeight.Bold)
            Slider(
                value = parallelingResourcePower.toFloat(),
                onValueChange = { parallelingResourcePower = it.toInt() },
                valueRange = 0f..Constants.PARALLELING_RESOURCE_POWER_LIMIT
            )
            radioOptions.forEach { text ->
                Row(
                    Modifier.fillMaxWidth().selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            onSortingModeSelected(sortingModeMap[text])
                        }
                    ).padding(all = Dp(value = 8F)),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == selectedOption), modifier = Modifier.padding(all = Dp(value = 8F)),
                        onClick = {
                            onOptionSelected(text)
                            onSortingModeSelected(sortingModeMap[text])
                        }
                    )
                    Text(text = text, modifier = Modifier.padding(start = 32.dp))
                }
            }
            PlotsButtons(
                onClickShowTimeOnParallelingDependence,
                onClickShowTimeOnSizesDependence,
                parallelingResourcePower,
                listSize,
                selectedSortingMode
            )
        }
    }
}
