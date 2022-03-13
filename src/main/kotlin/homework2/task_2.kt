package main.kotlin.homework2

fun main() {
    var stringNumber: String?
    println("Please, input a positive integer to print all not greater primes:")
    while (true) {
        try {
            stringNumber = readLine() ?: throw NullPointerException("Null input.")
            val number = stringNumber.toInt()
            val newSieveOfEratosthenes = main.libraries.SieveOfEratosthenes.SieveOfEratosthenes(number)
            newSieveOfEratosthenes.printLesserPrimes()
            break
        } catch (exception: Exception) {
            when(exception){
                is NullPointerException -> println("Null input. Try again:")
                is java.lang.NumberFormatException -> println("Cannot convert into Int. Try again:")
                is IllegalArgumentException -> println("Invalid argument. Try again:")
                else -> throw exception
            }
        }

    }
}

