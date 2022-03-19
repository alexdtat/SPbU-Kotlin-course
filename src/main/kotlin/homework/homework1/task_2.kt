package homework.homework1

import libraries.sieveOfEratosthenes.sieveOfEratosthenes

fun main() {
    println("Please, input a positive integer to print all not greater primes:")
    while (true) {
        try {
            val number = readLine()?.toIntOrNull()
            if (number == null)
                println("Cannot convert to Int. Try again:")
            else {
                val sieved = sieveOfEratosthenes(number)
                println("Primes not greater than $number:\n" + sieved.joinToString(", "))
                break
            }
        } catch (exceptionIllegal: IllegalArgumentException) {
            println(exceptionIllegal.message + " Invalid argument. Try again:")
        }
    }
}
