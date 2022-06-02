package libraries.mergeSort

import homework.homework4and5.generateRandomMutableList
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

const val CAP_NUMBER_OF_LISTS = 16
const val TEST_MIN_SIZE = 1
const val TEST_MAX_SIZE = 100
const val TEST_SHARE_THREADS_RESOURCE = 4

internal class MergeSortTest {
    @Test
    fun `threads mergeSort with 0 parallelingResource exception`() {
        val listForSorting = generateRandomMutableList(10)
        val exception = assertThrows<IllegalArgumentException> {
            listForSorting.mergeSort(0, sortingMode = SortingMode.THREADS)
        }
        assertEquals("There should be at least 1 thread/coroutine for sorting.", exception.message)
    }

    @Test
    fun `coroutines mergeSort with 0 parallelingResource exception`() {
        val listForSorting = generateRandomMutableList(10)
        val exception = assertThrows<IllegalArgumentException> {
            listForSorting.mergeSort(0, sortingMode = SortingMode.COROUTINES)
        }
        assertEquals("There should be at least 1 thread/coroutine for sorting.", exception.message)
    }
    @ParameterizedTest(name = "singleThreadedMergeSort {0}")
    @MethodSource("getListForTestSort")
    fun <T : Comparable<T>> `singleThreadedMergeSort parametrized`(expected: MutableList<T>, list: MutableList<T>) {
        assertEquals(expected, list.mergeSort(1, sortingMode = SortingMode.THREADS))
    }

    @ParameterizedTest(name = "multiThreadedMergeSort {0}")
    @MethodSource("getListForTestSort")
    fun <T : Comparable<T>> `multiThreadedMergeSort parametrized`(expected: MutableList<T>, list: MutableList<T>) {
        assertEquals(
            expected,
            list.mergeSort(list.size / TEST_SHARE_THREADS_RESOURCE + 1, sortingMode = SortingMode.THREADS)
        )
    }

    @ParameterizedTest(name = "multiThreadedMergeSort {0}")
    @MethodSource("getListForTestSort")
    fun <T : Comparable<T>> `coroutinesMergeSort parametrized`(expected: MutableList<T>, list: MutableList<T>) {
        assertEquals(
            expected,
            list.mergeSort(list.size / TEST_SHARE_THREADS_RESOURCE + 1, sortingMode = SortingMode.COROUTINES)
        )
    }

    companion object {
        private val listToExpected = mutableMapOf<MutableList<Int>, MutableList<Int>>().apply {
            repeat(CAP_NUMBER_OF_LISTS) {
                val newList = generateRandomMutableList((TEST_MIN_SIZE..TEST_MAX_SIZE).random())
                this[newList] = newList.apply { this.sort() }
            }
        }
        @JvmStatic
        fun getListForTestSort() = listToExpected.map { Arguments.of(it.value, it.key) }
    }
}
