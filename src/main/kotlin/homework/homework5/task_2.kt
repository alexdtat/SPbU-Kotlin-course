package homework.homework5

import libraries.matrix.Matrix

@Suppress("MagicNumber")
fun main() {
    val firstMatrix = Matrix(
        arrayOf(
            intArrayOf(1, 2, 3),
            intArrayOf(4, 5, 6),
            intArrayOf(7, 8, 9)
        )
    )
    val secondMatrix = Matrix(
        arrayOf(
            intArrayOf(-1, -2, -3),
            intArrayOf(-4, -5, -6),
            intArrayOf(-7, -8, -9)
        )
    )
    val result = firstMatrix * secondMatrix
    println("$firstMatrix\t*\t$secondMatrix\t=\t$result")
}
