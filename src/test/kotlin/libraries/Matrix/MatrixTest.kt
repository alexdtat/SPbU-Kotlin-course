package libraries.matrix

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class MatrixTest {
    @Test
    fun `empty matrix creation exception`() {
        val exception = assertThrows<IllegalArgumentException> { Matrix(emptyArray()) }
        assertEquals(
            "Matrix can have only natural number of lines having the same between each other natural size.",
            exception.message
        )
    }

    @Test
    fun `empty columns matrix creation exception`() {
        val exception = assertThrows<IllegalArgumentException> { Matrix(arrayOf(intArrayOf(), intArrayOf())) }
        assertEquals(
            "Matrix can have only natural number of lines having the same between each other natural size.",
            exception.message
        )
    }

    @Test
    fun `matrix with different sizes of rows exception`() {
        val exception = assertThrows<IllegalArgumentException> { Matrix(arrayOf(intArrayOf(1), intArrayOf(2, 3))) }
        assertEquals(
            "Matrix can have only natural number of lines having the same between each other natural size.",
            exception.message
        )
    }

    @Test
    fun `matrices with incorrect number of columns and rows multiplication exception`() {
        val leftMultiplier = Matrix(
            arrayOf(
                intArrayOf(1, 2, 3),
                intArrayOf(4, 5, 6),
            )
        )
        val rightMultiplier = Matrix(
            arrayOf(
                intArrayOf(1, 2, 3),
                intArrayOf(4, 5, 6),
            )
        )
        val exception = assertThrows<IllegalArgumentException> { leftMultiplier * rightMultiplier }
        assertEquals(
            "For multiplication left matrix should have the same number of columns as the right one of lines!",
            exception.message
        )
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("matricesForMultiplication")
    fun `parallelMultiplication correctness test`(expected: Matrix, leftMultiplier: Matrix, rightMultiplier: Matrix) {
        assertEquals(expected, leftMultiplier * rightMultiplier)
    }

    companion object {
        @JvmStatic
        fun matricesForMultiplication() = listOf(
            Arguments.of(
                Matrix(
                    arrayOf(
                        intArrayOf(-30, -36, -42),
                        intArrayOf(-66, -81, -96),
                        intArrayOf(-102, -126, -150)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(1, 2, 3),
                        intArrayOf(4, 5, 6),
                        intArrayOf(7, 8, 9)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(-1, -2, -3),
                        intArrayOf(-4, -5, -6),
                        intArrayOf(-7, -8, -9)
                    )
                )
            ),
            Arguments.of(
                Matrix(
                    arrayOf(
                        intArrayOf(-22, -28),
                        intArrayOf(-49, -64)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(1, 2, 3),
                        intArrayOf(4, 5, 6),
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(-1, -2),
                        intArrayOf(-3, -4),
                        intArrayOf(-5, -6)
                    )
                )
            ),
            Arguments.of(
                Matrix(arrayOf(intArrayOf(2))),
                Matrix(arrayOf(intArrayOf(1))),
                Matrix(arrayOf(intArrayOf(2)))
            )
        )
    }
}
