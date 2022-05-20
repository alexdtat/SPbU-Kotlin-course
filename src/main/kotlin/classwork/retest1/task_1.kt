package classwork.retest1

import libraries.vector.IntArithmetic
import libraries.vector.Vector

@Suppress("MagicNumber")
fun main() {
    val vector1 = Vector(listOf(IntArithmetic(1), IntArithmetic(2), IntArithmetic(3)))
    val vector2 = Vector(listOf(IntArithmetic(-1), IntArithmetic(-2), IntArithmetic(-3)))
    val additionVector = vector1 + vector2
    println("${vector1.coordinates} + ${vector2.coordinates} = ${additionVector.coordinates}")
    if (additionVector.isNullVector()) {
        println("${additionVector.coordinates} is null vector!")
    }
    println("${vector1.coordinates} - ${vector2.coordinates} = ${(vector1 - vector2).coordinates}")
    println("${vector1.coordinates} * ${vector2.coordinates} = ${(vector1 * vector2).coordinates}")
}
