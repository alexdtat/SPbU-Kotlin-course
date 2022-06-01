package libraries.mergeSort.plot

import homework.homework4and5.Model
import libraries.mergeSort.SortingMode
import java.awt.Desktop

class ViewModel {
    fun showTimeOnSizes(fixedParallelingResourcePercentage: Int, capSize: Int, sortingMode: SortingMode) {
        val picture = Model()
            .generatePlotTimeOnSize(fixedParallelingResourcePercentage, capSize, sortingMode)
        Desktop.getDesktop().browse(picture.toURI())
    }

    fun showTimeOnParalleling(capParallelingResourcePercentage: Int, fixedSize: Int, sortingMode: SortingMode) {
        val picture = Model()
            .generatePlotTimeOnParallelingResource(capParallelingResourcePercentage, fixedSize, sortingMode)
        Desktop.getDesktop().browse(picture.toURI())
    }
}
