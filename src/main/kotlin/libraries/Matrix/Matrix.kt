package libraries.matrix

data class Matrix(val entries: Array<IntArray>) {
    init {
        require(
            entries.isNotEmpty() && entries[0].isNotEmpty() && !entries.any { it.size != entries[0].size }
        ) { "Matrix can have only natural number of lines having the same between each other natural size." }
    }
    val rowsCount = entries.size
    val columnsCount = entries[0].size

    override fun hashCode(): Int {
        var result = entries.hashCode()
        result = 31 * result + rowsCount
        result = 31 * result + columnsCount
        return result
    }

    operator fun times(multiplier: Matrix) = parallelMultiply(multiplier)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Matrix

        if (rowsCount != other.rowsCount) return false
        if (columnsCount != other.columnsCount) return false
        return entries.contentDeepEquals(other.entries)
    }

    override fun toString() = entries.contentDeepToString()
}
