package libraries.mergeSort.sortingClasses

import libraries.mergeSort.SortingMode
import libraries.mergeSort.mergeSort

internal class MultithreadedMergeSort<T : Comparable<T>>(list: MutableList<T>) : MergeSort<T>(list) {
    override fun sort(
        parallelingResource: Int,
        bufferList: MutableList<T>,
    ): MutableList<T> {
        val middle = (list.size + 1) / 2
        val leftThreadsResource = (parallelingResource + 1) / 2
        val rightThreadsResource = parallelingResource - leftThreadsResource
        var leftHalfSorted = mutableListOf<T>()
        var rightHalfSorted = mutableListOf<T>()
        val leftThread = Thread {
            leftHalfSorted = bufferList.subList(0, middle)
                .mergeSort(leftThreadsResource, SortingMode.THREADS)
        }
        val rightThread = Thread {
            rightHalfSorted = bufferList.subList(middle, bufferList.size)
                .mergeSort(rightThreadsResource, SortingMode.THREADS)
        }

        leftThread.start()
        rightThread.start()
        leftThread.join()
        rightThread.join()

        return merge(leftHalfSorted, rightHalfSorted)
    }
}
