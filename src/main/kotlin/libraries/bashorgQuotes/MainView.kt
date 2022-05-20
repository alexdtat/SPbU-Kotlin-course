@file:Suppress("FunctionNaming")

package libraries.bashOrgQuotes.mainView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import classwork.retest1.model.getBestQuotes
import classwork.retest1.model.getLastQuotes
import classwork.retest1.model.getRandomQuote
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

const val OFFSET = 60

@Composable
fun MainView() {
    var textField by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("bash.org quotes", fontWeight = Bold, fontSize = 50.sp, modifier = Modifier.padding(OFFSET.dp))
        Text(
            title,
            fontWeight = FontWeight.Medium,
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding((OFFSET / 2).dp)
        )
        Text(textField, fontWeight = FontWeight.Medium, fontSize = 20.sp, modifier = Modifier.padding(OFFSET.dp))
        Button(onClick = {
            runBlocking { launch { textField = getBestQuotes() } }
            title = "Best quotes"
        }) { Text("Show best quotes") }
        Button(onClick = {
            runBlocking { launch { textField = getLastQuotes() } }
            title = "Last quotes"
        }) { Text("Show last quotes") }
        Button(onClick = {
            runBlocking { launch { textField = getRandomQuote() } }
            title = "Random quote"
        }) { Text("Show random quote") }
    }
}
