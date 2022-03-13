package test.libraries.PerformedCommandStorage

import main.libraries.PerformedCommandStorage.PerformedCommandStorage
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

internal class PerformedCommandStorageTest {

    @Test
    fun `test correct undo in empty storage`() {
        val testStorage = PerformedCommandStorage()
        val exception = assertThrows<IllegalArgumentException> { testStorage.undo() }
        assertEquals("You cannot undo a command in the empty storage.", exception.message)
    }

    @Test
    fun `test correct addToEnd with argument 5`() {
        val testStorage = PerformedCommandStorage()
        testStorage.addToEnd(5)
        assertEquals(arrayListOf(5), testStorage.getListOfInts())
    }

    @Test
    fun `test correct addToBeginning with argument 5`() {
        val testStorage = PerformedCommandStorage()
        testStorage.addToBeginning(5)
        assertEquals(arrayListOf(5), testStorage.getListOfInts())
    }

    @Test
    fun `test correct undo after 1 command`() {
        val testStorage = PerformedCommandStorage()
        testStorage.addToBeginning(5)
        testStorage.undo()
        assertEquals(arrayListOf(), testStorage.getListOfInts())
    }

    @Test
    fun `test exception swap with negative argument`() {
        val testStorage = PerformedCommandStorage()
        testStorage.addToEnd(5)
        val exception = assertThrows<IllegalArgumentException> { testStorage.swap(-1, 0) }
        assertEquals(
            "Positions must be integers in range 0..${testStorage.getListOfInts().size - 1}.",
            exception.message
        )
    }

    @Test
    fun `test exception swap with out of upper bound argument`() {
        val testStorage = PerformedCommandStorage()
        testStorage.addToEnd(5)
        val exception = assertThrows<IllegalArgumentException> { testStorage.swap(0, 1) }
    }

    @Test
    fun `test correct few commands in row`() {
        val testStorage = PerformedCommandStorage()
        testStorage.addToEnd(5)
        testStorage.addToEnd(-10)
        testStorage.addToBeginning(0)
        testStorage.swap(0, 1)
        testStorage.undo()
        testStorage.swap(0, 2)
        testStorage.addToEnd(3)
        testStorage.undo()
        testStorage.addToBeginning(4)
        testStorage.swap(1, 3)
        testStorage.undo()
        testStorage.undo()
        assertEquals(arrayListOf(-10, 5, 0), testStorage.getListOfInts())
    }
}