package main.libraries.sieveOfEratosthenes

class SieveOfEratosthenes(private val upperBound: Int) {
    private var isPrime = Array(1) { _ -> true }
    private val primes = ArrayList<Int>()

    private fun sievePrimes() {
        isPrime = Array(upperBound + 1) { _ -> true }
        isPrime[0] = false
        isPrime[1] = false
        val optimisedUpperBound = kotlin.math.floor(kotlin.math.sqrt(upperBound.toDouble())).toInt()
        (2..optimisedUpperBound).forEach { index ->
            if (isPrime[index]) {
                (2 * index..upperBound step index).forEach { isPrime[it] = false }
                primes.add(index)
            }
        }

        ((optimisedUpperBound + 1)..upperBound).forEach { index ->
            if (isPrime[index])
                primes.add(index)
        }
    }

    fun printLesserPrimes() {
        println("Primes not greater than $upperBound:\n")
        for (index in primes.indices) {
            print("${primes[index]}\t")
        }
    }

    fun getPrimes(): ArrayList<Int> {
        return primes
    }

    init {
        require(upperBound > 0) { "Argument must be a positive integer." }
        sievePrimes()
    }
}
