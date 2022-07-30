package uno.rebellious.accessibilitydemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import uno.rebellious.accessibilitydemo.ui.theme.AccessibilityDemoTheme
import java.text.NumberFormat

val accountPositive = AccountDetails("40-40-41", "12345678", 23.50, "Positive Account")
val accountNegative = AccountDetails("43-52-11", "87654321", -23.50, "Negative Account")
val accountList = arrayOf(accountPositive, accountNegative)
val items = listOf(Screen.Good, Screen.Bad)

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Good : Screen("good", R.string.good)
    object Bad : Screen("bad", R.string.bad)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AccessibilityDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    Page()
                }
            }
        }
    }
}



@Composable
fun BadPage() {
    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            BadAccounts(accountList)
            Spacer(modifier = Modifier.height(4.dp))
            BadTextEntry()
            Spacer(modifier = Modifier.height(4.dp))
            BadButtonRow()
        }
        Text(
            fontSize = 5.em,
            modifier = Modifier.padding(8.dp),
            text = "This is an amount of text, probably some sort of disclaimer about how your accounts values are saved at the time of loading.  Your actual balance maybe different to what it actually is here."
        )
    }
}

@Composable
fun GoodPage() {
    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Accounts(accountList)
            Spacer(modifier = Modifier.height(4.dp))
            TextEntry()
            Spacer(modifier = Modifier.height(4.dp))
            ButtonRow()
        }
        Text(
            modifier = Modifier.padding(8.dp),
            text = "This is an amount of text, probably some sort of disclaimer about how your accounts values are saved at the time of loading.  Your actual balance maybe different to what it actually is here."
        )
    }
}

@Composable
fun Page() {
    val navController = rememberNavController()
    Scaffold(topBar = { MyTopAppBar() }, bottomBar = { MyAppBottomBar(navController) }) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Good.route,
            Modifier.padding(padding)
        ) {
            composable(Screen.Good.route) { GoodPage() }
            composable(Screen.Bad.route) { BadPage() }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AccessibilityDemoTheme {
        BadAccountCard(accountPositive)
    }
}