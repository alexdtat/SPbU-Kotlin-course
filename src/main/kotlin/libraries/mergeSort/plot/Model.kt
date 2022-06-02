package homework.homework4and5

import jetbrains.letsPlot.export.ggsave
import jetbrains.letsPlot.geom.geomLine
import jetbrains.letsPlot.ggsize
import jetbrains.letsPlot.intern.Plot
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
    private fun generatePlot(
        mapForData: LinkedHashMap<Int, Long>,
        title: String,
        subtitle: String,
        color: String,
        scaleXText: String,
    ): Plot {
        val data = mapOf(
            scaleXText to mapForData.keys.toList(),
            "Sorting time" to mapForData.values.toList()
        )

        val style = scaleYContinuous(format = "{} ms") + scaleXContinuous(breaks = mapForData.keys.toList()) +
            ggsize(WIDTH, HEIGHT) + labs(
            title = title,
            subtitle = subtitle
        )

        return letsPlot(data) { x = scaleXText; y = "Sorting time" } + geomLine(
            color = color,
            size = LINE_SIZE,
        ) + style
    }
    private fun createFileInDirectory(plot: Plot, filename: String): File {
        val directory = File("$FILE_PATH/")
        if (!directory.isDirectory) {
            Files.createDirectories(Paths.get("$FILE_PATH/"))
        }
        val path = ggsave(plot, filename = filename, path = FILE_PATH)
        return File(path)
    }

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

        val plot = generatePlot(
            sizesToTimeMap,
            "$sortingModeText merge sort",
            "Time dependence on list size. There are 2^$parallelingResourcePower = " +
                "${2.0.pow(parallelingResourcePower).toInt()} ${sortingModeText.lowercase()} for each list.",
            "blue",
            "List size",
        )

        return createFileInDirectory(plot, SIZES_PICTURE_FILE_NAME)
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

        val plot = generatePlot(
            parallelBranchesToTimeMap,
            "$sortingModeText merge sort",
            "Time dependence on ${sortingModeText.lowercase()} count. List size is $size.",
            "orange",
            "Log(${sortingModeText.lowercase()} count)",
        )

        return createFileInDirectory(plot, PARALLELING_PICTURE_FILE_NAME)
    }

    companion object {
        private const val LINE_SIZE = 1.0
        private const val HEIGHT = 720
        private const val WIDTH = 1280
        private const val PARALLELING_PICTURE_FILE_NAME = "sortingPlotParalleling.png"
        private const val SIZES_PICTURE_FILE_NAME = "sortingPlotSizes.png"
        private const val FILE_PATH = "src/main/resources/libraries/mergeSort/plot"
        private const val PLOT_STEP_SHARE = 20
    }
}
