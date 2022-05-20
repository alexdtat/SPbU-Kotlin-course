package libraries.vector

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class vectorTest {
    @Test
    fun `isNull with null vector`() {
        val testVector = Vector(listOf(IntArithmetic(0), IntArithmetic(0), IntArithmetic(0)))
        assertEquals(true, testVector.isNullVector())
    }

    @Test
    fun `isNull with NOT null vector`() {
        val testVector = Vector(listOf(IntArithmetic(0), IntArithmetic(1), IntArithmetic(0)))
        assertEquals(false, testVector.isNullVector())
    }

    @Test
    fun `correct addition`() {
        val leftTerm = Vector(listOf(IntArithmetic(1), IntArithmetic(2), IntArithmetic(3)))
        val rightTerm = Vector(listOf(IntArithmetic(-1), IntArithmetic(-2), IntArithmetic(-3)))
        val expectedVector = Vector(listOf(IntArithmetic(0), IntArithmetic(0), IntArithmetic(0)))
        assertEquals(expectedVector, leftTerm + rightTerm)
    }

    @Test
    fun `incorrect addition (different sizes)`() {
        val leftTerm = Vector(listOf(IntArithmetic(1), IntArithmetic(2), IntArithmetic(3), IntArithmetic(0)))
        val rightTerm = Vector(listOf(IntArithmetic(-1), IntArithmetic(-2), IntArithmetic(-3)))
        val exception = assertThrows<IllegalArgumentException> { leftTerm + rightTerm }
        assertEquals("For addition vectors should have same sizes.", exception.message)
    }

    @Test
    fun `correct subtraction`() {
        val minuend = Vector(listOf(IntArithmetic(1), IntArithmetic(2), IntArithmetic(3)))
        val subtrahend = Vector(listOf(IntArithmetic(-1), IntArithmetic(-2), IntArithmetic(-3)))
        val expectedVector = Vector(listOf(IntArithmetic(2), IntArithmetic(4), IntArithmetic(6)))
        assertEquals(expectedVector, minuend - subtrahend)
    }

    @Test
    fun `incorrect subtraction (different sizes)`() {
        val minuend = Vector(listOf(IntArithmetic(1), IntArithmetic(2), IntArithmetic(3), IntArithmetic(0)))
        val subtrahend = Vector(listOf(IntArithmetic(-1), IntArithmetic(-2), IntArithmetic(-3)))
        val exception = assertThrows<IllegalArgumentException> { minuend - subtrahend }
        assertEquals("For subtraction vectors should have same sizes.", exception.message)
    }

    @Test
    fun `correct multiplication`() {
        val leftFactor = Vector(listOf(IntArithmetic(1), IntArithmetic(2), IntArithmetic(3)))
        val rightFactor = Vector(listOf(IntArithmetic(-1), IntArithmetic(-2), IntArithmetic(-3)))
        val expectedVector = Vector(listOf(IntArithmetic(-1), IntArithmetic(-4), IntArithmetic(-9)))
        assertEquals(expectedVector, leftFactor * rightFactor)
    }

    @Test
    fun `incorrect multiplication (different sizes)`() {
        val leftFactor = Vector(listOf(IntArithmetic(1), IntArithmetic(2), IntArithmetic(3), IntArithmetic((0))))
        val rightFactor = Vector(listOf(IntArithmetic(-1), IntArithmetic(-2), IntArithmetic(-3)))
        val exception = assertThrows<IllegalArgumentException> { leftFactor * rightFactor }
        assertEquals("For dot multiplication vectors should have same sizes.", exception.message)
    }
}
