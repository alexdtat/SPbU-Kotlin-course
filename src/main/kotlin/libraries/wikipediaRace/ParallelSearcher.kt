package libraries.wikipediaRace

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

const val BAD_MAN = "Adolf_Hitler"

fun convertParsedLinkIntoName(parsedLink: String): String {
    val tokenList = parsedLink.split('/')
    require(tokenList[1] == "wiki") { "Only wikipedia links are acceptable!" }
    return when {
        tokenList[2].startsWith("Wikipedia:") -> ""
        tokenList[2].startsWith("File:") -> ""
        tokenList[2].startsWith("Help:") -> ""
        tokenList[2].startsWith("Special:") -> ""
        tokenList[2].startsWith("Category:") -> ""
        tokenList[2].startsWith("Template:") -> ""
        tokenList[2].startsWith("Portal") -> ""
        tokenList[2].startsWith("Talk:") -> ""
        else -> tokenList[2]
    }
}

fun getPagesNames(wikiPageDocument: Document): List<String> {
    val links = wikiPageDocument.select("a[href^=/wiki]").map { element -> element.attr("href") }
    return links.filter { it.startsWith("/wiki/") }
        .map { element -> convertParsedLinkIntoName(element) }.filter { it != "" }
}

fun getParsedPage(pageName: String): Document {
    val urlAddress = "https://en.wikipedia.org/wiki/$pageName"
    return Jsoup.connect(urlAddress).get()
}

suspend fun findBadMan(
    visitedPages: Map<String, List<String>>,
    path: MutableList<String>,
    remainingDepth: Int,
): MutableList<String> = coroutineScope {
    if (remainingDepth == 0) {
        return@coroutineScope path
    }
    println("Remaining depth: $remainingDepth\tCurrent path: $path")

    val pagesWithBadMan = visitedPages.filter { BAD_MAN in it.value }
    val parentOfBadMan = if (pagesWithBadMan.isNotEmpty()) pagesWithBadMan.keys.elementAt(0) else null

    if (parentOfBadMan != null) {
        path.add(parentOfBadMan)
        return@coroutineScope path
    }

    val nextLevelPagesNames = mutableMapOf<String, List<String>>()
    val job = launch {
        visitedPages.values.forEach {
            it.forEach { pageName ->
                launch {
                    val wikiPageDocument = getParsedPage(pageName)
                    nextLevelPagesNames[pageName] = getPagesNames(wikiPageDocument)
                }
            }
        }
    }
    job.join()

    val newPath = findBadMan(nextLevelPagesNames, path, remainingDepth - 1)
    if (newPath != emptyList<String>()) {
        visitedPages.forEach {
            if (it.value.contains(newPath.last())) {
                newPath.add(it.key)
            }
        }
    }
    return@coroutineScope newPath
}
