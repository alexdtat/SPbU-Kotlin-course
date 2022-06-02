package classwork.final_work

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import libraries.wikipediaRace.BAD_MAN
import libraries.wikipediaRace.findBadMan
import libraries.wikipediaRace.getPagesNames
import libraries.wikipediaRace.getParsedPage
import org.jsoup.Jsoup

const val RANDOM = "https://en.wikipedia.org/wiki/Special:Random"

@OptIn(ExperimentalCoroutinesApi::class)
fun startSearch(maxDepth: Int, coreCount: Int, pageName: String = "") {
    val parsedWikiPage = if (pageName == "") {
        Jsoup.connect(RANDOM).get()
    } else {
        getParsedPage(pageName)
    }
    val links = getPagesNames(parsedWikiPage)
    val map = mutableMapOf(pageName to links)
    val path = runBlocking(Dispatchers.Default.limitedParallelism(coreCount)) {
        findBadMan(map, mutableListOf(), maxDepth)
    }.reversed()
    if (path == emptyList<String>()) {
        println("Cannot find Bad Man with this parameters.")
    } else {
        println("Path to Bad Man: ${path.joinToString(" -> ")} -> $BAD_MAN")
    }
}

fun main() {
    println("Please, input data to find Bad Man:\nsearchDepth coreNumber startingPageName(optional)")
    println("For example:\n10 16 Germany")
    val input = readLine()
    val inputSplitted = input?.split(' ') ?: throw java.lang.IllegalArgumentException("Null input!")
    require(inputSplitted.size >= 2)
    if (inputSplitted.size == 2) {
        startSearch(maxDepth = inputSplitted[0].toInt(), coreCount = inputSplitted[1].toInt())
    } else {
        startSearch(inputSplitted[0].toInt(), inputSplitted[1].toInt(), inputSplitted[2])
    }
}
