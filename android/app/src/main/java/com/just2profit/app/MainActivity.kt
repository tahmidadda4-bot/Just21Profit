package com.just2profit.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Task(
    val title: String,
    val reward: String,
    val description: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Just2ProfitApp()
        }
    }
}

@Composable
fun Just2ProfitApp() {

    var page by remember { mutableStateOf("home") }

    val tasks = listOf(
        Task(
            title = "Daily Task",
            reward = "৳10",
            description = "Complete the assigned activity."
        ),
        Task(
            title = "Profile Task",
            reward = "৳5",
            description = "Complete your profile information."
        )
    )

    MaterialTheme {

        Scaffold(

            bottomBar = {

                NavigationBar {

                    NavigationBarItem(
                        selected = page == "home",
                        onClick = { page = "home" },
                        icon = {},
                        label = { Text("Home") }
                    )

                    NavigationBarItem(
                        selected = page == "tasks",
                        onClick = { page = "tasks" },
                        icon = {},
                        label = { Text("Tasks") }
                    )

                    NavigationBarItem(
                        selected = page == "wallet",
                        onClick = { page = "wallet" },
                        icon = {},
                        label = { Text("Wallet") }
                    )

                    NavigationBarItem(
                        selected = page == "ref",
                        onClick = { page = "ref" },
                        icon = {},
                        label = { Text("Referral") }
                    )
                }
            }

        ) { paddingValues ->

            when (page) {

                "tasks" -> {
                    TaskPage(
                        tasks = tasks,
                        modifier = Modifier.padding(paddingValues)
                    )
                }

                "wallet" -> {
                    WalletPage(
                        modifier = Modifier.padding(paddingValues)
                    )
                }

                "ref" -> {
                    ReferralPage(
                        modifier = Modifier.padding(paddingValues)
                    )
                }

                else -> {
                    HomePage(
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
}

@Composable
fun HomePage(modifier: Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Welcome to Just2Profit",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text("Available balance")

        Text(
            text = "৳ 0.00",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Complete genuine tasks to earn rewards."
        )
    }
}

@Composable
fun TaskPage(
    tasks: List<Task>,
    modifier: Modifier
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        items(tasks) { task ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(text = task.description)

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Reward: ${task.reward}"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {}
                    ) {
                        Text("Open Task")
                    }
                }
            }
        }
    }
}

@Composable
fun WalletPage(modifier: Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Wallet",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Balance: ৳ 0.00")

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {}
        ) {
            Text("Request Withdrawal")
        }
    }
}

@Composable
fun ReferralPage(modifier: Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Referral",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Invite friends and earn rewards.")

        Spacer(modifier = Modifier.height(16.dp))

        Text("Your referral code: J2P2026")

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {}
        ) {
            Text("Share Referral")
        }
    }
}
