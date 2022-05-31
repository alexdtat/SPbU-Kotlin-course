package libraries.mergeSort.plot

import homework.homework4and5.generatePlotTimeOnSize
import homework.homework4and5.generateTimeOnParallelingResource
import libraries.mergeSort.SortingMode
import java.awt.Desktop

class ViewModel {
    fun showTimeOnSizes(fixedParallelingResourcePercentage: Int, capSize: Int, sortingMode: SortingMode) {
        val picture = generatePlotTimeOnSize(fixedParallelingResourcePercentage, capSize, sortingMode)
        Desktop.getDesktop().browse(picture.toURI())
    }

    fun showTimeOnParalleling(capParallelingResourcePercentage: Int, fixedSize: Int, sortingMode: SortingMode) {
        val picture = generateTimeOnParallelingResource(capParallelingResourcePercentage, fixedSize, sortingMode)
        Desktop.getDesktop().browse(picture.toURI())
    }
}
