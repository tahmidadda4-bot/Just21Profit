package com.just2profit.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
            "Daily Task",
            "৳10",
            "Complete the assigned activity."
        ),
        Task(
            "Profile Task",
            "৳5",
            "Complete your profile information."
        ),
        Task(
            "Bonus Task",
            "৳15",
            "Complete today's bonus activity."
        )
    )

    MaterialTheme {

        Scaffold(
            bottomBar = {

                NavigationBar {

                    NavigationBarItem(
                        selected = page == "home",
                        onClick = { page = "home" },
                        icon = { Text("⌂") },
                        label = { Text("Home") }
                    )

                    NavigationBarItem(
                        selected = page == "tasks",
                        onClick = { page = "tasks" },
                        icon = { Text("✓") },
                        label = { Text("Tasks") }
                    )

                    NavigationBarItem(
                        selected = page == "wallet",
                        onClick = { page = "wallet" },
                        icon = { Text("৳") },
                        label = { Text("Wallet") }
                    )

                    NavigationBarItem(
                        selected = page == "ref",
                        onClick = { page = "ref" },
                        icon = { Text("👥") },
                        label = { Text("Referral") }
                    )
                }
            }
        ) { paddingValues ->

            when (page) {

                "tasks" -> TaskPage(
                    tasks = tasks,
                    modifier = Modifier.padding(paddingValues)
                )

                "wallet" -> WalletPage(
                    modifier = Modifier.padding(paddingValues)
                )

                "ref" -> ReferralPage(
                    modifier = Modifier.padding(paddingValues)
                )

                else -> HomePage(
                    modifier = Modifier.padding(paddingValues),
                    onTasksClick = { page = "tasks" }
                )
            }
        }
    }
}

@Composable
fun HomePage(
    modifier: Modifier,
    onTasksClick: () -> Unit
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {

            Text(
                text = "Just2Profit",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Welcome back!",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {

                Column(
                    modifier = Modifier.padding(22.dp)
                ) {

                    Text(
                        text = "Available Balance",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "৳ 0.00",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onTasksClick,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("View Tasks")
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Quick Access",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        item {

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        text = "Today's Tasks",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text("Complete genuine tasks and earn rewards.")

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = onTasksClick
                    ) {
                        Text("Start Task")
                    }
                }
            }
        }
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

        item {

            Text(
                text = "Daily Tasks",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        items(tasks) { task ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                shape = RoundedCornerShape(16.dp)
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(task.description)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Reward: ${task.reward}",
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth()
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
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text("Current Balance")

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "৳ 0.00",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Request Withdrawal")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Transaction History")
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
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Invite Friends",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Invite friends to Just2Profit and earn referral rewards."
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Your Referral Code",
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "J2P2026",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Share Referral")
                }
            }
        }
    }
}
