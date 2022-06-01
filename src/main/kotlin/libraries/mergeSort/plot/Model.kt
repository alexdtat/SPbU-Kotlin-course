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
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.pow
import kotlin.system.measureTimeMillis

class Model {
    fun generatePlotTimeOnSize(
        parallelingResourcePower: Int,
        capSize: Int,
        sortingMode: SortingMode
    ): File {
        val sortingModeText = if (sortingMode == SortingMode.THREADS) "Threads" else "Coroutines"
        val sizesToTimeMap = linkedMapOf<Int, Long>()
        val plotStep = (capSize / PLOT_STEP_SHARE) + 1
        val parallelingResource = 2.0.pow(parallelingResourcePower).toInt()
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
            title = "$sortingModeText merge sort",
            subtitle = "Time dependence on list size. There are 2^$parallelingResourcePower = " +
                "${2.0.pow(parallelingResourcePower).toInt()} ${sortingModeText.lowercase()} for each list."
        )

        val plot = letsPlot(data) { x = "List size"; y = "Sorting time" } + geomLine(
            color = "blue",
            size = LINE_SIZE,
        ) + style
        val directory = File("$FILE_PATH/")
        if (!directory.isDirectory) {
            Files.createDirectory(Paths.get("$FILE_PATH/"))
        }
        val path = ggsave(plot, filename = SIZES_PICTURE_FILE_NAME, path = FILE_PATH)
        return File(path)
    }

    fun generatePlotTimeOnParallelingResource(
        parallelingResourcePowerCap: Int,
        size: Int,
        sortingMode: SortingMode
    ): File {
        val sortingModeText = if (sortingMode == SortingMode.THREADS) "Threads" else "Coroutines"
        val parallelBranchesToTimeMap = linkedMapOf<Int, Long>()
        for (parallelingResourcePower in 0..parallelingResourcePowerCap) {
            val randomList = generateRandomMutableList(size)
            val parallelingResource = 2.0.pow(parallelingResourcePower).toInt()
            parallelBranchesToTimeMap[parallelingResourcePower] = measureTimeMillis {
                randomList.mergeSort(parallelingResource, sortingMode = sortingMode)
            }
        }

        val data = mapOf(
            "Log(${sortingModeText.lowercase()} count)" to parallelBranchesToTimeMap.keys.toList(),
            "Sorting time" to parallelBranchesToTimeMap.values.toList()
        )

        val style = scaleYContinuous(format = "{} ms") +
            scaleXContinuous(breaks = parallelBranchesToTimeMap.keys.toList()) + ggsize(WIDTH, HEIGHT) + labs(
            title = "$sortingModeText merge sort",
            subtitle = "Time dependence on ${sortingModeText.lowercase()} count. List size is $size."
        )

        val plot = letsPlot(data) { x = "Log(${sortingModeText.lowercase()} count)"; y = "Sorting time" } + geomLine(
            color = "orange",
            size = LINE_SIZE,
        ) + style
        val directory = File("$FILE_PATH/")
        if (!directory.isDirectory) {
            Files.createDirectory(Paths.get("$FILE_PATH/"))
        }
        val path = ggsave(plot, filename = PARALLELING_PICTURE_FILE_NAME, path = FILE_PATH)
        return File(path)
    }

    companion object {
        private const val LINE_SIZE = 1.0
        private const val HEIGHT = 720
        private const val WIDTH = 1280
        private const val PARALLELING_PICTURE_FILE_NAME = "sortingPlotParalleling.png"
        private const val SIZES_PICTURE_FILE_NAME = "sortingPlotSizes.png"
        private const val FILE_PATH = "src/main/resources/homework4and5"
        private const val PLOT_STEP_SHARE = 20
    }
}
