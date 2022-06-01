package homework.homework4and5

import kotlin.random.Random
private object RandomListConstants {
    const val LOWER_RANDOM_BOUND = -10000
    const val UPPER_RANDOM_BOUND = 10000
}

fun generateRandomMutableList(
    size: Int,
    lowerBound: Int = RandomListConstants.LOWER_RANDOM_BOUND,
    upperBound: Int = RandomListConstants.UPPER_RANDOM_BOUND
): MutableList<Int> = MutableList(size) { Random.nextInt(lowerBound, upperBound) }
