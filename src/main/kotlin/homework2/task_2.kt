package homework2

import libraries.sieveOfEratosthenes.SieveOfEratosthenes
import java.lang.NumberFormatException

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
        } catch (exception: NullPointerException) {
            println("Null input. Try again:")
        } catch (exception: NumberFormatException) {
            println("Cannot convert into Int. Try again:")
        } catch (exception: IllegalArgumentException) {
            println("Invalid argument. Try again:")
        }

    }
}
