package homework.homework4and5

import kotlin.random.Random

const val LOWER_RANDOM_BOUND = -100
const val UPPER_RANDOM_BOUND = 100

fun generateRandomMutableList(size: Int, lowerBound: Int = LOWER_RANDOM_BOUND, upperBound: Int = UPPER_RANDOM_BOUND):
    MutableList<Int> = MutableList(size) { Random.nextInt(lowerBound, upperBound) }
