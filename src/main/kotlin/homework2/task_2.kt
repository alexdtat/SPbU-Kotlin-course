package main.kotlin.homework2

fun main() {
    var number: Int = 0
    println("Please, input a positive integer to print all not greater primes:")
    while(true) {
        try {
            number = readln().toInt()
            break
        } catch (e: java.lang.NumberFormatException) {
            println("Cannot convert into Int. Try again:")
        }
    }

    while(true) {
        try {
            val argument = number
            val newSieveOfEratosthenes = main.libraries.SieveOfEratosthenes.SieveOfEratosthenes(number)
            newSieveOfEratosthenes.printLesserPrimes()
            break
        } catch (exception: IllegalArgumentException) {
            println("Invalid argument. Try again:")
            number = readln().toInt()
        }
    }
}

