package libraries.mergeSort.sortingClasses

import libraries.mergeSort.SortingMode
import libraries.mergeSort.mergeSort

internal class SingleThreadedMergeSort<T : Comparable<T>>(list: MutableList<T>) : MergeSort<T>(list) {
    override fun sort(
        parallelingResource: Int,
        bufferList: MutableList<T>
    ): MutableList<T> {
        val middle = (list.size + 1) / 2
        val leftHalfSorted = bufferList.subList(0, middle).mergeSort(sortingMode = SortingMode.THREADS)
        val rightHalfSorted = bufferList.subList(middle, bufferList.size).mergeSort(sortingMode = SortingMode.THREADS)

        return merge(leftHalfSorted, rightHalfSorted)
    }
}
