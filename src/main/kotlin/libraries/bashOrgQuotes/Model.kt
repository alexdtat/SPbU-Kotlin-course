package classwork.retest1.model

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

const val LAST_QUOTES_URL = "http://bashorg.org/"
const val BEST_QUOTES_URL = "http://bashorg.org/best"
const val RANDOM_QUOTE_URL = "http://bashorg.org/casual"

fun getQuotes(quotesDocument: Document): String {
    val data = quotesDocument.select(".quote").mapIndexed { index, element -> "${index + 1}) ${element.text()}" }
    return data.joinToString("\n\n")
}

fun getLastQuotes(): String {
    val quotesDocument = Jsoup.connect(LAST_QUOTES_URL).get()
    return getQuotes(quotesDocument)
}

fun getBestQuotes(): String {
    val quotesDocument = Jsoup.connect(BEST_QUOTES_URL).get()
    return getQuotes(quotesDocument)
}

fun getRandomQuote(): String {
    val quotesDocument = Jsoup.connect(RANDOM_QUOTE_URL).get()
    val data = quotesDocument.select(".q div")
    if (data.size < 2) {
        return data.firstOrNull()?.text() ?: ""
    }
    return data[1].text()
}
