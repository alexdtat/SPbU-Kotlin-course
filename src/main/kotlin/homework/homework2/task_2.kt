package homework.homework2

import libraries.sieveOfEratosthenes.SieveOfEratosthenes

fun main() {
    var stringNumber: String? = ""
    println("Please, input a positive integer to print all not greater primes:")
    while (true) {
        try {
            var number = 0
            stringNumber = readLine()
            if (stringNumber == null) {
                println("Null input. Please, try again:")
            } else {
                number = stringNumber.toInt()
            }
            val newSieveOfEratosthenes = SieveOfEratosthenes(number)
            newSieveOfEratosthenes.printLesserPrimes()
            break
        } catch (exceptionFormat: NumberFormatException) {
            println(exceptionFormat.message + " Cannot convert into Int. Try again:")
        } catch (exceptionIllegal: IllegalArgumentException) {
            println(exceptionIllegal.message + " Invalid argument. Try again:")
        }
    }
}
