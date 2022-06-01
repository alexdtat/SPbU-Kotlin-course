package libraries.vector

interface ArithmeticAvailable<T : ArithmeticAvailable<T>> {
    fun isNeutral(): Boolean
    operator fun plus(added: T): T
    operator fun minus(deductible: T): T
    operator fun times(multiplier: T): T
}

data class IntArithmetic(val number: Int) : ArithmeticAvailable<IntArithmetic> {
    override fun isNeutral() = (number == 0)
    override fun plus(added: IntArithmetic) = IntArithmetic(number + added.number)
    override fun minus(deductible: IntArithmetic) = IntArithmetic(number - deductible.number)
    override fun times(multiplier: IntArithmetic) = IntArithmetic(number * multiplier.number)
    override fun toString() = "$number"
}
