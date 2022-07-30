package uno.rebellious.accessibilitydemo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import java.text.NumberFormat

@Composable
fun WeirdAccounts(accounts: Array<AccountDetails>) {
    Column {
        accounts.forEach {
            WeirdAccountCard(account = it)
        }
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
fun WeirdAccountCard(account: AccountDetails) {
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
fun BadAccountCard(account: AccountDetails) {
    val fontSize = 5.em
    val openDialog = remember { mutableStateOf(false) }

    Surface(
        shape = MaterialTheme.shapes.medium,
        elevation = 1.dp,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { openDialog.value = true }), horizontalArrangement = Arrangement.SpaceBetween) {
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

@Composable
fun AccountCard(account: AccountDetails) {
    val openDialog = remember { mutableStateOf(false) }

    Surface(
        shape = MaterialTheme.shapes.medium,
        elevation = 1.dp,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            Modifier
                .padding(8.dp)
                .clickable(
                    onClick = { openDialog.value = true },
                    onClickLabel = "Show Account Details"
                )) {
            AccountRow("Sort Code", account.sortCode, true)
            AccountRow("Account Number", account.accountNumber, true)
            AccountRow("Account Name", account.accountName)
            AccountRow("Balance", NumberFormat.getCurrencyInstance().format(account.balance))
        }
    }
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