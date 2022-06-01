package libraries.mergeSort

import libraries.mergeSort.sortingClasses.CoroutineMergeSort
import libraries.mergeSort.sortingClasses.MultithreadedMergeSort
import libraries.mergeSort.sortingClasses.SingleThreadedMergeSort

fun <T : Comparable<T>> MutableList<T>.mergeSort(
    parallelingResource: Int = 1,
    sortingMode: SortingMode,
): MutableList<T> {
    require(parallelingResource >= 1) { "There should be at least 1 thread/coroutine for sorting." }
    if (this.size <= 1) {
        return this
    }

    val bufferList = this.toMutableList()
    when (parallelingResource) {
        1 -> SingleThreadedMergeSort(this).sort(parallelingResource, bufferList)
        else -> when (sortingMode) {
            SortingMode.COROUTINES -> CoroutineMergeSort(this).sort(parallelingResource, bufferList)
            SortingMode.THREADS -> MultithreadedMergeSort(this).sort(parallelingResource, bufferList)
        }
    }

    return this
}
