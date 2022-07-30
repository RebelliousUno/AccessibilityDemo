package uno.rebellious.accessibilitydemo

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em

@Composable
fun BadTextEntry() {
    val username = remember {
        mutableStateOf("")
    }
    TextField(
        modifier = Modifier.padding(8.dp),
        placeholder = { Text("Enter Text Here", fontSize = 5.em) },
        singleLine = true,
        value = username.value,
        onValueChange = { username.value = it })
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextEntry() {
    val username = remember {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        modifier = Modifier.padding(8.dp),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
        singleLine = true,
        value = username.value,
        onValueChange = { username.value = it },
        label = { Text("Enter Text Here") })
}