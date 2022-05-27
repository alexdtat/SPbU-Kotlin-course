package homework.homework4and5

import jetbrains.letsPlot.export.ggsave
import jetbrains.letsPlot.geom.geomLine
import jetbrains.letsPlot.ggsize
import jetbrains.letsPlot.label.labs
import jetbrains.letsPlot.letsPlot
import jetbrains.letsPlot.scale.scaleXContinuous
import jetbrains.letsPlot.scale.scaleYContinuous
import libraries.mergeSort.SortingMode
import libraries.mergeSort.mergeSort
import java.awt.Desktop
import java.io.File
import kotlin.system.measureTimeMillis

const val LIST_SIZE = 50000
const val THREADS_COUNT_LOWER = 64
const val THREADS_COUNT_UPPER = 1024
const val THREADS_STEP = 16
const val SIZES_LOWER = 512
const val SIZES_UPPER = 25000
const val SIZES_STEP = 128
const val SHARE_THREADS_RESOURCE = 32
const val LINE_SIZE = 1.0
const val HEIGHT = 720
const val WIDTH = 1280
const val THREADS_PICTURE_FILE_NAME = "sortingPlotThreads.png"
const val SIZES_PICTURE_FILE_NAME = "sortingPlotSizes.png"
const val FILE_PATH = "src/main/resources/homework4"

fun timeOnThreadsDependence(sortingMode: SortingMode) {
    val sortingModeText = if (sortingMode == SortingMode.THREADS) "Threads" else "Coroutines"
    val randomList = generateRandomMutableList(LIST_SIZE)
    val threadsToTimeMap = linkedMapOf<Int, Long>()
    for (threadsCount in THREADS_COUNT_LOWER..THREADS_COUNT_UPPER step THREADS_STEP) {
        threadsToTimeMap[threadsCount] = measureTimeMillis {
            randomList.mergeSort(threadsCount, sortingMode = sortingMode)
        }
    }

    val data = mapOf(
        "$sortingModeText count" to threadsToTimeMap.keys.toList(),
        "Sorting time" to threadsToTimeMap.values.toList()
    )

    val style = scaleYContinuous(format = "{} ms") + scaleXContinuous(breaks = threadsToTimeMap.keys.toList()) +
        ggsize(WIDTH, HEIGHT) + labs(
        title = "$sortingModeText merge sort",
        subtitle = "Time dependence on ${sortingModeText.lowercase()} count. List size is $LIST_SIZE."
    )

    val plot = letsPlot(data) { x = "$sortingModeText count"; y = "Sorting time" } + geomLine(
        color = "orange",
        size = LINE_SIZE,
    ) + style
    val path = ggsave(plot, filename = THREADS_PICTURE_FILE_NAME, path = FILE_PATH)
    val file = File(path)
    Desktop.getDesktop().browse(file.toURI())
}

fun timeOnSizesDependence(sortingMode: SortingMode) {
    val sortingModeText = if (sortingMode == SortingMode.THREADS) "Threads" else "Coroutines"
    val sizesToTimeMap = linkedMapOf<Int, Long>()
    for (size in SIZES_LOWER..SIZES_UPPER step SIZES_STEP) {
        val randomList = generateRandomMutableList(size)
        sizesToTimeMap[size] = measureTimeMillis {
            randomList.mergeSort(size / SHARE_THREADS_RESOURCE + 1, sortingMode = sortingMode)
        }
    }

    val data = mapOf(
        "List size" to sizesToTimeMap.keys.toList(),
        "Sorting time" to sizesToTimeMap.values.toList()
    )

    val style = scaleYContinuous(format = "{} ms") + scaleXContinuous(breaks = sizesToTimeMap.keys.toList()) +
        ggsize(WIDTH, HEIGHT) + labs(
        title = "$sortingModeText merge sort",
        subtitle = "Time dependence on list size. " +
            "There is 1/$SHARE_THREADS_RESOURCE of list's size paralleling resource for each list."
    )

    val plot = letsPlot(data) { x = "List size"; y = "Sorting time" } + geomLine(
        color = "blue",
        size = LINE_SIZE,
    ) + style
    val path = ggsave(plot, filename = SIZES_PICTURE_FILE_NAME, path = FILE_PATH)
    val file = File(path)
    Desktop.getDesktop().browse(file.toURI())
}
