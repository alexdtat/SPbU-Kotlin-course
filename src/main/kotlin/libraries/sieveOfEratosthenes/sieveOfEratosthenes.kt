package libraries.sieveOfEratosthenes

import kotlin.math.floor
import kotlin.math.sqrt

fun sieveOfEratosthenes(upperBound: Int): List<Int> {
    require(upperBound > 0) { "Argument must be a positive integer." }
    val isPrime = Array(upperBound + 1) { true }

    isPrime[0] = false
    isPrime[1] = false
    val optimisedUpperBound = floor(sqrt(upperBound.toDouble())).toInt()
    (2..optimisedUpperBound).forEach { index ->
        if (isPrime[index])
            ((2 * index)..upperBound step index).forEach { isPrime[it] = false }
    }
    val primes = isPrime.mapIndexedNotNull { index, _ ->
        if (isPrime[index])
            index
        else
            null
    }
    return primes
}
