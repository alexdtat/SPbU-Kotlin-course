package libraries.matrix

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun Matrix.parallelMultiply(multiplier: Matrix): Matrix {
    require(columnsCount == multiplier.rowsCount) {
        "For multiplication left matrix should have the same number of columns as the right one of lines!"
    }
    val result =
        Matrix(Array(rowsCount) { IntArray(multiplier.columnsCount) { 0 } })

    runBlocking {
        for (i in 0 until rowsCount) {
            for (j in 0 until multiplier.columnsCount) {
                launch {
                    result.entries[i][j] = (0 until columnsCount).sumOf {
                        entries[i][it] * multiplier.entries[it][j]
                    }
                }
            }
        }
    }

    return result
}
