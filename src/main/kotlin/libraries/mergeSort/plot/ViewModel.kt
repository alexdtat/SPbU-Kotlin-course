package libraries.mergeSort.plot

import homework.homework4and5.generateRandomMutableList
import homework.homework4and5.timeOnSizesDependence
import homework.homework4and5.timeOnThreadsDependence
import libraries.mergeSort.SortingMode

class ViewModel {
    fun showTimeFromThreads(sortingMode: SortingMode) {
        timeOnThreadsDependence(sortingMode)
    }

    fun showTimeFromSizes(sortingMode: SortingMode) {
        timeOnSizesDependence(sortingMode)
    }

    fun onClickGenerateList(size: Int) = generateRandomMutableList(size)
}
