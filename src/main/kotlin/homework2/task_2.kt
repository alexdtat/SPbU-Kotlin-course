package homework2.task2

import libraries.sieveOfEratosthenes.SieveOfEratosthenes

fun main() {
    var stringNumber: String?
    println("Please, input a positive integer to print all not greater primes:")
    while (true) {
        try {
            stringNumber = readLine() ?: throw NullPointerException("Null input.")
            val number = stringNumber.toInt()
            val newSieveOfEratosthenes = SieveOfEratosthenes(number)
            newSieveOfEratosthenes.printLesserPrimes()
            break
        } catch (exceptionFormat: NumberFormatException) {
            println("Cannot convert into Int. Try again:")
        } catch (exceptionIllegal: IllegalArgumentException) {
            println("Invalid argument. Try again:")
        } catch (exceptionNull: NullPointerException) {
            println("Null input. Try again:")
        }
    }
}
