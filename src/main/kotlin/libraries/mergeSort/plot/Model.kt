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
import java.io.File
import kotlin.system.measureTimeMillis

const val LINE_SIZE = 1.0
const val HEIGHT = 720
const val WIDTH = 1280
const val PARALLELING_PICTURE_FILE_NAME = "sortingPlotParalleling.png"
const val SIZES_PICTURE_FILE_NAME = "sortingPlotSizes.png"
const val FILE_PATH = "src/main/resources/homework4and5"
const val PLOT_STEP_SHARE = 20
const val FULL_PERCENTAGE = 100

fun generatePlotTimeOnSize(
    parallelingResourcePercentage: Int,
    capSize: Int,
    sortingMode: SortingMode
): File {
    val sizesToTimeMap = linkedMapOf<Int, Long>()
    val plotStep = (capSize / PLOT_STEP_SHARE) + 1
    val parallelingResource = (capSize * parallelingResourcePercentage / FULL_PERCENTAGE) + 1
    for (size in (1..capSize) step plotStep) {
        val randomList = generateRandomMutableList(size)
        sizesToTimeMap[size] = measureTimeMillis {
            randomList.mergeSort(parallelingResource, sortingMode = sortingMode)
        }
    }

    val data = mapOf(
        "List size" to sizesToTimeMap.keys.toList(),
        "Sorting time" to sizesToTimeMap.values.toList()
    )

    val style = scaleYContinuous(format = "{} ms") + scaleXContinuous(breaks = sizesToTimeMap.keys.toList()) +
        ggsize(WIDTH, HEIGHT) + labs(
        title = "Multithreaded merge sort",
        subtitle = "Time dependence on list size. " +
            "There are $parallelingResourcePercentage% (+1) of list's size threads for each list."
    )

    val plot = letsPlot(data) { x = "List size"; y = "Sorting time" } + geomLine(
        color = "blue",
        size = LINE_SIZE,
    ) + style
    val path = ggsave(plot, filename = SIZES_PICTURE_FILE_NAME, path = FILE_PATH)
    return File(path)
}

fun generateTimeOnParallelingResource(
    parallelingResourcePercentageCap: Int,
    size: Int,
    sortingMode: SortingMode
): File {
    val parallelBranchesToTimeMap = linkedMapOf<Int, Long>()
    val parallelingResourceCap = (size * parallelingResourcePercentageCap / FULL_PERCENTAGE) + 1
    val plotStep = (parallelingResourceCap / PLOT_STEP_SHARE) + 1
    for (parallelBranches in (1..parallelingResourceCap) step plotStep) {
        val randomList = generateRandomMutableList(size)
        parallelBranchesToTimeMap[parallelBranches] = measureTimeMillis {
            randomList.mergeSort(parallelBranches, sortingMode = sortingMode)
        }
    }

    val data = mapOf(
        "Threads count" to parallelBranchesToTimeMap.keys.toList(),
        "Sorting time" to parallelBranchesToTimeMap.values.toList()
    )

    val style = scaleYContinuous(format = "{} ms") +
        scaleXContinuous(breaks = parallelBranchesToTimeMap.keys.toList()) + ggsize(WIDTH, HEIGHT) + labs(
        title = "Multithreaded merge sort",
        subtitle = "Time dependence on threads count. List size is $size."
    )

    val plot = letsPlot(data) { x = "Threads count"; y = "Sorting time" } + geomLine(
        color = "orange",
        size = LINE_SIZE,
    ) + style
    val path = ggsave(plot, filename = PARALLELING_PICTURE_FILE_NAME, path = FILE_PATH)
    return File(path)
}
