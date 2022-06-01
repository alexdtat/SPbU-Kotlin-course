package libraries.mergeSort.plot

import homework.homework4and5.Model
import libraries.mergeSort.SortingMode
import java.awt.Desktop

class ViewModel {
    fun showTimeOnSizes(fixedParallelingResourcePower: Int, capSize: Int, sortingMode: SortingMode) {
        val picture = Model()
            .generatePlotTimeOnSize(fixedParallelingResourcePower, capSize, sortingMode)
        Desktop.getDesktop().browse(picture.toURI())
    }

    fun showTimeOnParalleling(capParallelingResourcePower: Int, fixedSize: Int, sortingMode: SortingMode) {
        val picture = Model()
            .generatePlotTimeOnParallelingResource(capParallelingResourcePower, fixedSize, sortingMode)
        Desktop.getDesktop().browse(picture.toURI())
    }
}
