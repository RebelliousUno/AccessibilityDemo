package uno.rebellious.accessibilitydemo

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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