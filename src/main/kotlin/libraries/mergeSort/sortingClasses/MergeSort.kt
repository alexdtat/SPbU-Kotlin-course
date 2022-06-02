package libraries.mergeSort.sortingClasses

internal abstract class MergeSort<T : Comparable<T>>(val list: MutableList<T>) {
    abstract fun sort(parallelingResource: Int = 1, bufferList: MutableList<T>): MutableList<T>

    protected fun merge(
        leftList: MutableList<T>,
        rightList: MutableList<T>
    ): MutableList<T> {
        var indexLeft = 0
        var indexRight = 0

        while (indexLeft < leftList.count() && indexRight < rightList.count()) {
            if (leftList[indexLeft] <= rightList[indexRight]) {
                list[indexLeft + indexRight] = leftList[indexLeft]
                indexLeft++
            } else {
                list[indexLeft + indexRight] = rightList[indexRight]
                indexRight++
            }
        }

        while (indexLeft < leftList.size) {
            list[indexLeft + indexRight] = leftList[indexLeft]
            indexLeft++
        }

        while (indexRight < rightList.size) {
            list[indexLeft + indexRight] = rightList[indexRight]
            indexRight++
        }

        return list
    }
}
