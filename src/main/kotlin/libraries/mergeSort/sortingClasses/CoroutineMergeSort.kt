package libraries.mergeSort.sortingClasses

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import libraries.mergeSort.SortingMode
import libraries.mergeSort.mergeSort

internal class CoroutineMergeSort<T : Comparable<T>>(list: MutableList<T>) : MergeSort<T>(list) {
    override fun sort(
        parallelingResource: Int,
        bufferList: MutableList<T>,
    ): MutableList<T> {
        val middle = (list.size + 1) / 2
        val leftCoroutinesResource = (parallelingResource + 1) / 2
        val rightCoroutinesResource = parallelingResource - leftCoroutinesResource
        var leftHalfSorted = mutableListOf<T>()
        var rightHalfSorted = mutableListOf<T>()
        runBlocking {
            val leftHalfSorting = launch {
                leftHalfSorted = bufferList.subList(0, middle)
                    .mergeSort(leftCoroutinesResource, SortingMode.COROUTINES)
            }
            val rightHalfSorting = launch {
                rightHalfSorted = bufferList.subList(middle, bufferList.size)
                    .mergeSort(rightCoroutinesResource, SortingMode.COROUTINES)
            }
            leftHalfSorting.join()
            rightHalfSorting.join()
        }

        return merge(leftHalfSorted, rightHalfSorted)
    }
}
