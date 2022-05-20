package libraries.bashOrgQuotes

import classwork.retest1.model.getBestQuotes
import classwork.retest1.model.getLastQuotes
import classwork.retest1.model.getRandomQuote
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class BashOrgQuotesTest {
    @Test
    fun `getBestQuotes is not empty`() {
        assertTrue { getBestQuotes() != "" }
    }

    @Test
    fun `getLastQuotes is not empty`() {
        assertTrue { getLastQuotes() != "" }
    }

    @Test
    fun `getRandomQuote is not empty`() {
        assertTrue { getRandomQuote() != "" }
    }
}
