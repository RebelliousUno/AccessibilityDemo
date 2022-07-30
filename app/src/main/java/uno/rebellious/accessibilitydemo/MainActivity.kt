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
fun MyAppBottomBar(navController: NavHostController) {
    BottomNavigation {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry.value?.destination
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                label = { Text(stringResource(screen.resourceId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun MyTopAppBar() {
    val openDialog = remember { mutableStateOf(false) }
    TopAppBar(title = {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Accessibility Demo")
            IconButton(
                onClick = {
                    openDialog.value = true
                }) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    })
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text("Info Alert") },
            text = { Text("Some Info Value") },
            confirmButton = {
                Button(onClick = {
                    openDialog.value = false
                }) { Text("Confirm Button") }
            }, dismissButton = {
                Button(onClick = {
                    openDialog.value = false
                }) { Text("Cancel Button") }
            })
    }
}

@OptIn(ExperimentalComposeUiApi::class)
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

@Composable
fun BadAccounts(accounts: Array<AccountDetails>) {
    Column {
        accounts.forEach {
            BadAccountCard(it)
        }
    }
}

@Composable
fun Accounts(accounts: Array<AccountDetails>) {
    Column {
        accounts.forEach {
            AccountCard(it)
        }
    }
}

@Composable
fun AccountRow(field: String, value: String, readIndividual: Boolean = false) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(field)
        Spacer(Modifier.width(8.dp))
        Text(
            text = value,
            textAlign = TextAlign.End,
            modifier = Modifier.semantics {
                this.text = AnnotatedString(
                    if (readIndividual) value.map { "$it " }.toString().trim() else value
                )
            })
    }
}

@Composable
fun BadAccountCard(account: AccountDetails) {
    val fontSize = 5.em
    Surface(
        shape = MaterialTheme.shapes.medium,
        elevation = 1.dp,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(Modifier.padding(8.dp)) {
                Text(text = "Sort Code", fontSize = fontSize)
                Text(text = "Account Number", fontSize = fontSize)
                Text(text = "Account Name", fontSize = fontSize)
                Text(text = "Balance", fontSize = fontSize)
            }
            Column(Modifier.padding(8.dp)) {
                Text(
                    modifier = Modifier.align(Alignment.End),
                    fontSize = fontSize,
                    text = account.sortCode
                )
                Text(
                    modifier = Modifier.align(Alignment.End),
                    fontSize = fontSize,
                    text = account.accountNumber
                )
                Text(
                    modifier = Modifier.align(Alignment.End),
                    fontSize = fontSize,
                    text = account.accountName
                )
                Text(
                    modifier = Modifier.align(Alignment.End), fontSize = fontSize,
                    text = NumberFormat.getCurrencyInstance().format(account.balance)
                )
            }
        }
    }
}

@Composable
fun AccountCard(account: AccountDetails) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        elevation = 1.dp,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(Modifier.padding(8.dp)) {
            AccountRow("Sort Code", account.sortCode, true)
            AccountRow("Account Number", account.accountNumber, true)
            AccountRow("Account Name", account.accountName)
            AccountRow("Balance", NumberFormat.getCurrencyInstance().format(account.balance))
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