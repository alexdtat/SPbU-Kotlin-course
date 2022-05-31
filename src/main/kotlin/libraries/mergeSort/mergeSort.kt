package libraries.mergeSort

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private fun <T : Comparable<T>> MutableList<T>.multithreadedMergeSort(
    threadsResource: Int = 1,
    bufferList: MutableList<T>,
    sortingMode: SortingMode,
): MutableList<T> {
    val middle = (this.size + 1) / 2
    val leftThreadsResource = (threadsResource + 1) / 2
    val rightThreadsResource = threadsResource - leftThreadsResource
    var leftHalfSorted = mutableListOf<T>()
    var rightHalfSorted = mutableListOf<T>()
    val leftThread = Thread {
        leftHalfSorted = bufferList.subList(0, middle).mergeSort(leftThreadsResource, sortingMode)
    }
    val rightThread = Thread {
        rightHalfSorted = bufferList.subList(middle, bufferList.size).mergeSort(rightThreadsResource, sortingMode)
    }

    leftThread.start()
    rightThread.start()
    leftThread.join()
    rightThread.join()

    return this.merge(leftHalfSorted, rightHalfSorted)
}

fun <T : Comparable<T>> MutableList<T>.mergeSort(
    parallelingResource: Int = 1,
    sortingMode: SortingMode,
): MutableList<T> {
    require(parallelingResource >= 1) { "There should be > 0 threads/coroutines for sorting." }
    if (this.size <= 1) {
        return this
    }

    val bufferList = this.toMutableList()
    when (parallelingResource) {
        1 -> singleThreadedMergeSort(bufferList)
        else -> when (sortingMode) {
            SortingMode.COROUTINES -> coroutineMergeSort(parallelingResource, bufferList, sortingMode)
            SortingMode.THREADS -> multithreadedMergeSort(parallelingResource, bufferList, sortingMode)
        }
    }

    return this
}

private fun <T : Comparable<T>> MutableList<T>.singleThreadedMergeSort(bufferList: MutableList<T>) {
    val middle = (this.size + 1) / 2
    val leftHalfSorted = bufferList.subList(0, middle).mergeSort(sortingMode = SortingMode.THREADS)
    val rightHalfSorted = bufferList.subList(middle, bufferList.size).mergeSort(sortingMode = SortingMode.THREADS)

    this.merge(leftHalfSorted, rightHalfSorted)
}

private fun <T : Comparable<T>> MutableList<T>.merge(
    leftList: MutableList<T>,
    rightList: MutableList<T>
): MutableList<T> {
    var indexLeft = 0
    var indexRight = 0

    while (indexLeft < leftList.count() && indexRight < rightList.count()) {
        if (leftList[indexLeft] <= rightList[indexRight]) {
            this[indexLeft + indexRight] = leftList[indexLeft]
            indexLeft++
        } else {
            this[indexLeft + indexRight] = rightList[indexRight]
            indexRight++
        }
    }

    while (indexLeft < leftList.size) {
        this[indexLeft + indexRight] = leftList[indexLeft]
        indexLeft++
    }

    while (indexRight < rightList.size) {
        this[indexLeft + indexRight] = rightList[indexRight]
        indexRight++
    }

    return this
}

private fun <T : Comparable<T>> MutableList<T>.coroutineMergeSort(
    coroutinesResource: Int,
    bufferList: MutableList<T>,
    sortingMode: SortingMode
): MutableList<T> {
    val middle = (this.size + 1) / 2
    val leftCoroutinesResource = (coroutinesResource + 1) / 2
    val rightCoroutinesResource = coroutinesResource - leftCoroutinesResource
    var leftHalfSorted = mutableListOf<T>()
    var rightHalfSorted = mutableListOf<T>()
    runBlocking {
        val leftHalfSorting = launch {
            leftHalfSorted = bufferList.subList(0, middle).mergeSort(leftCoroutinesResource, sortingMode)
        }
        leftHalfSorting.join()
        val rightHalfSorting = launch {
            rightHalfSorted =
                bufferList.subList(middle, bufferList.size).mergeSort(rightCoroutinesResource, sortingMode)
        }
        rightHalfSorting.join()
    }

    return this.merge(leftHalfSorted, rightHalfSorted)
}
