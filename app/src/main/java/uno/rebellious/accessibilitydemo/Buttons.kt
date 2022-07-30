package uno.rebellious.accessibilitydemo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em

@Composable
fun BadButtonRow() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Button(modifier = Modifier.padding(8.dp), onClick = {}) { Text("One", fontSize = 5.em) }
        Button(modifier = Modifier.padding(8.dp), onClick = {}) { Text("Two", fontSize = 5.em) }
        Button(modifier = Modifier.padding(8.dp), onClick = {}) { Text("Three", fontSize = 5.em) }
    }
}

@Composable
fun ButtonRow() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Button(modifier = Modifier.padding(8.dp), onClick = {}) { Text("One") }
        Button(modifier = Modifier.padding(8.dp), onClick = {}) { Text("Two") }
        Button(modifier = Modifier.padding(8.dp), onClick = {}) { Text("Three") }
    }
}