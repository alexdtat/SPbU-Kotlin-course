package libraries.vector

data class Vector<T : ArithmeticAvailable<T>>(val coordinates: List<T>) {
    private val size = coordinates.size

    operator fun plus(addedVector: Vector<T>) = this.sum(addedVector)
    operator fun minus(subtractedVector: Vector<T>) = this.difference(subtractedVector)
    operator fun times(factorVector: Vector<T>) = this.dotProduct(factorVector)

    fun isNullVector(): Boolean = !coordinates.any { !it.isNeutral() }
    fun sum(addedVector: Vector<T>): Vector<T> {
        require(size == addedVector.size) { "For addition vectors should have same sizes." }
        return Vector(List(size) { coordinates[it] + addedVector.coordinates[it] })
    }

    fun difference(subtrahendVector: Vector<T>): Vector<T> {
        require(size == subtrahendVector.size) { "For subtraction vectors should have same sizes." }
        return Vector(List(size) { coordinates[it] - subtrahendVector.coordinates[it] })
    }

    fun dotProduct(factorVector: Vector<T>): T {
        require(size == factorVector.size) { "For dot multiplication vectors should have same sizes." }
        var result = coordinates[0] * factorVector.coordinates[0]
        for (index in 1 until coordinates.size) {
            result += coordinates[index] * factorVector.coordinates[index]
        }

        return result
    }

    init {
        require(coordinates.isNotEmpty()) { "There should be more than 0 coordinates." }
    }
}
