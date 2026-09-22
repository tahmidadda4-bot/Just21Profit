package com.just2profit.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private val Navy = Color(0xFF062342)
private val Green = Color(0xFF00C875)
private val LightBg = Color(0xFFF5F8FA)
private val Blue = Color(0xFF0D5AA7)

data class J2PTask(
    val title: String,
    val description: String,
    val reward: String,
    val icon: String
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Just2ProfitTheme {
                Just2ProfitApp()
            }
        }
    }
}

@Composable
fun Just2ProfitTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Navy,
            secondary = Green,
            background = LightBg,
            surface = Color.White
        ),
        content = content
    )
}

@Composable
fun Just2ProfitApp() {

    var showSplash by remember { mutableStateOf(true) }
    var loggedIn by remember { mutableStateOf(false) }
    var register by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1800)
        showSplash = false
    }

    if (showSplash) {
        SplashScreen()
        return
    }

    if (!loggedIn) {

        if (register) {
            RegisterScreen(
                onRegister = {
                    loggedIn = true
                },
                onLogin = {
                    register = false
                }
            )
        } else {
            LoginScreen(
                onLogin = {
                    loggedIn = true
                },
                onRegister = {
                    register = true
                }
            )
        }

        return
    }

    MainDashboard(
        onLogout = {
            loggedIn = false
            register = false
        }
    )
}

/* ---------------- SPLASH ---------------- */

@Composable
fun SplashScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Navy),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(105.dp)
                    .background(
                        Green,
                        RoundedCornerShape(50.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "↗",
                    fontSize = 60.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(18.dp))

            Text(
                "Just2Profit",
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                "Work Smart • Earn More",
                color = Color.White,
                fontSize = 15.sp
            )

            Spacer(Modifier.height(28.dp))

            Text(
                "ছোট ছোট কাজ করে\nবড় আয় করুন",
                color = Color.White,
                fontSize = 18.sp,
                lineHeight = 27.sp
            )

            Spacer(Modifier.height(45.dp))

            LinearProgressIndicator(
                modifier = Modifier.width(130.dp),
                color = Green,
                trackColor = Color.DarkGray
            )
        }
    }
}

/* ---------------- LOGIN ---------------- */

@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    onRegister: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    AuthBackground {

        Logo()

        Spacer(Modifier.height(28.dp))

        Text(
            "লগইন করুন",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Navy
        )

        Text(
            "আপনার অ্যাকাউন্টে প্রবেশ করুন",
            color = Color.Gray
        )

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("ইমেইল / ফোন নম্বর") },
            singleLine = true
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("পাসওয়ার্ড") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = false,
                    onCheckedChange = {}
                )
                Text("আমাকে মনে রাখুন", fontSize = 13.sp)
            }

            Text(
                "পাসওয়ার্ড ভুলে গেছেন?",
                color = Blue,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 14.dp)
            )
        }

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = onLogin,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Navy
            )
        ) {
            Text("লগইন করুন")
        }

        Spacer(Modifier.height(14.dp))

        Text("অথবা", color = Color.Gray)

        Spacer(Modifier.height(14.dp))

        OutlinedButton(
            onClick = onLogin,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("G  Google দিয়ে লগইন করুন")
        }

        Spacer(Modifier.height(22.dp))

        Row {
            Text("এখনও অ্যাকাউন্ট নেই? ")

            Text(
                "রেজিস্টার করুন",
                color = Blue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    onRegister()
                }
            )
        }
    }
}

/* ---------------- REGISTER ---------------- */

@Composable
fun RegisterScreen(
    onRegister: () -> Unit,
    onLogin: () -> Unit
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }

    AuthBackground {

        Text(
            "←",
            fontSize = 28.sp,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onLogin() }
        )

        Text(
            "রেজিস্টার করুন",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Navy
        )

        Text(
            "একটি নতুন অ্যাকাউন্ট তৈরি করুন",
            color = Color.Gray
        )

        Spacer(Modifier.height(18.dp))

        SmallField(name, { name = it }, "আপনার নাম")
        Spacer(Modifier.height(9.dp))

        SmallField(email, { email = it }, "ইমেইল ঠিকানা")
        Spacer(Modifier.height(9.dp))

        SmallField(phone, { phone = it }, "ফোন নম্বর")
        Spacer(Modifier.height(9.dp))

        PasswordField(
            password,
            { password = it },
            "পাসওয়ার্ড"
        )

        Spacer(Modifier.height(9.dp))

        PasswordField(
            confirm,
            { confirm = it },
            "পাসওয়ার্ড নিশ্চিত করুন"
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onRegister,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Navy
            )
        ) {
            Text("রেজিস্টার করুন")
        }

        Spacer(Modifier.height(18.dp))

        Row {
            Text("ইতিমধ্যে অ্যাকাউন্ট আছে? ")

            Text(
                "লগইন করুন",
                color = Blue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    onLogin()
                }
            )
        }
    }
}

@Composable
fun SmallField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        singleLine = true
    )
}

@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        visualTransformation = PasswordVisualTransformation(),
        singleLine = true
    )
}

/* ---------------- AUTH BACKGROUND ---------------- */

@Composable
fun AuthBackground(
    content: @Composable ColumnScope.() -> Unit
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp, vertical = 35.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content
        )
    }
}

@Composable
fun Logo() {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            "◉",
            color = Green,
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.width(8.dp))

        Text(
            "Just2",
            color = Navy,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            "Profit",
            color = Green,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/* ---------------- MAIN DASHBOARD ---------------- */

@Composable
fun MainDashboard(
    onLogout: () -> Unit
) {

    var page by remember { mutableStateOf("home") }
    var drawer by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBg)
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            when (page) {

                "home" -> HomePage(
                    onMenu = { drawer = true },
                    onPage = { page = it }
                )

                "tasks" -> TasksPage(
                    onBack = { page = "home" }
                )

                "earnings" -> EarningsPage(
                    onBack = { page = "home" }
                )

                "withdraw" -> WithdrawalPage(
                    onBack = { page = "home" }
                )

                "profile" -> ProfilePage(
                    onBack = { page = "home" }
                )

                "settings" -> SettingsPage(
                    onBack = { page = "home" }
                )
            }

            if (page == "home") {
                BottomNavigation(
                    page = page,
                    onPage = { page = it }
                )
            }
        }

        if (drawer) {

            SideMenu(
                onClose = { drawer = false },
                onPage = {
                    drawer = false
                    page = it
                },
                onLogout = onLogout
            )
        }
    }
}

/* ---------------- HOME ---------------- */

@Composable
fun HomePage(
    onMenu: () -> Unit,
    onPage: (String) -> Unit
) {

    LazyColumn(
    modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 8.dp)
) {

        item {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Navy)
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    "☰",
                    color = Color.White,
                    fontSize = 28.sp,
                    modifier = Modifier.clickable {
                        onMenu()
                    }
                )

                Spacer(Modifier.width(18.dp))

                Text(
                    "Just2",
                    color = Color.White,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "Profit",
                    color = Green,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.weight(1f))

                Text(
                    "♧",
                    color = Color.White,
                    fontSize = 25.sp
                )
            }
        }

        item {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Navy)
                    .padding(horizontal = 18.dp, vertical = 8.dp)
            ) {

                Text(
                    "শুভগত, Tahmid",
                    color = Color.White,
                    fontSize = 16.sp
                )

                Text(
                    "Level 1",
                    color = Color.White,
                    fontSize = 12.sp
                )

                Spacer(Modifier.height(15.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Green
                    )
                ) {

                    Row(
                        modifier = Modifier.padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column {
                            Text(
                                "মোট আয়",
                                color = Color.White
                            )

                            Text(
                                "৳ 256.75",
                                color = Color.White,
                                fontSize = 27.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(Modifier.weight(1f))

                        Text(
                            "▣",
                            color = Color.White,
                            fontSize = 30.sp
                        )
                    }
                }
            }
        }

        item {

            Row(
                modifier = Modifier.padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "আজকের কাজ",
                    value = "3/5",
                    icon = "▣"
                )

                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "মোট কাজ",
                    value = "12",
                    icon = "✓"
                )
            }
        }

        item {

            Row(
                modifier = Modifier.padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "ইনস্ট্যান্ট বোনাস",
                    value = "৳ 0.00",
                    icon = "৳"
                )

                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "রেফারেল",
                    value = "2",
                    icon = "♣"
                )
            }
        }

        item {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 12.dp,
                        end = 12.dp,
                        top = 18.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    "আজকের কাজ",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.weight(1f))

                Text(
                    "সব দেখুন ›",
                    color = Blue,
                    modifier = Modifier.clickable {
                        onPage("tasks")
                    }
                )
            }
        }

        item {

            TaskPreview(
                icon = "▶",
                title = "ভিডিও দেখুন",
                time = "30 সেকেন্ড",
                reward = "৳ 5.00"
            )
        }

        item {

            TaskPreview(
                icon = "★",
                title = "অ্যাপ রিভিউ দিন",
                time = "2 মিনিট",
                reward = "৳ 10.00"
            )
        }
    }
}

@Composable
fun StatCard(
    modifier: Modifier,
    title: String,
    value: String,
    icon: String
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(13.dp)
    ) {

        Column(
            modifier = Modifier.padding(13.dp)
        ) {

            Text(
                icon,
                color = Green,
                fontSize = 22.sp
            )

            Spacer(Modifier.height(4.dp))

            Text(
                title,
                fontSize = 12.sp,
                color = Color.Gray
            )

            Text(
                value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TaskPreview(
    icon: String,
    title: String,
    time: String,
    reward: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 5.dp),
        shape = RoundedCornerShape(13.dp)
    ) {

        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                icon,
                fontSize = 28.sp,
                color = Blue
            )

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    title,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    time,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            Text(
                reward,
                color = Green,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/* ---------------- TASKS ---------------- */

@Composable
fun TasksPage(onBack: () -> Unit) {

    val tasks = listOf(
        J2PTask(
            "ভিডিও দেখুন",
            "৩০ সেকেন্ডের ভিডিও দেখুন",
            "৳ 5.00",
            "▶"
        ),
        J2PTask(
            "অ্যাপ রিভিউ দিন",
            "২ মিনিটে একটি রিভিউ সম্পন্ন করুন",
            "৳ 10.00",
            "★"
        ),
        J2PTask(
            "সার্ভে পূরণ করুন",
            "৩ মিনিটের ছোট সার্ভে",
            "৳ 15.00",
            "▣"
        ),
        J2PTask(
            "ওয়েবসাইট ভিজিট",
            "১ মিনিট ওয়েবসাইটে থাকুন",
            "৳ 5.00",
            "↗"
        ),
        J2PTask(
            "বন্ধুকে রেফার করুন",
            "একজন নতুন ইউজার রেফার করুন",
            "৳ 50.00",
            "♣"
        )
    )

    SimpleTopBar("কাজের তালিকা", onBack)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {

        items(tasks) { task ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {

                Row(
                    modifier = Modifier.padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        task.icon,
                        fontSize = 28.sp,
                        color = Blue
                    )

                    Spacer(Modifier.width(13.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            task.title,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            task.description,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )

                        Text(
                            task.reward,
                            color = Green,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = {}
                    ) {
                        Text("কাজ করুন")
                    }
                }
            }
        }
    }
}

/* ---------------- EARNINGS ---------------- */

@Composable
fun EarningsPage(onBack: () -> Unit) {

    SimpleTopBar("আমার আয়", onBack)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {

        item {

            BalanceCard()

            Spacer(Modifier.height(12.dp))
        }

        items(
            listOf(
                "ভিডিও দেখার জন্য" to "+৳ 5.00",
                "অ্যাপ রিভিউ" to "+৳ 10.00",
                "রেফারেল বোনাস" to "+৳ 50.00",
                "কাজের আয়" to "+৳ 15.00",
                "ভিডিও দেখার জন্য" to "+৳ 5.00"
            )
        ) { item ->

            TransactionItem(
                title = item.first,
                amount = item.second
            )
        }
    }
}

/* ---------------- WITHDRAWAL ---------------- */

@Composable
fun WithdrawalPage(onBack: () -> Unit) {

    var method by remember { mutableStateOf("bKash") }
    var amount by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }

    SimpleTopBar("উত্তোলন", onBack)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        BalanceCard()

        Spacer(Modifier.height(18.dp))

        Text(
            "উত্তোলনের পদ্ধতি",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(Modifier.height(10.dp))

        PaymentOption(
            name = "bKash",
            selected = method == "bKash"
        ) {
            method = "bKash"
        }

        PaymentOption(
            name = "Nagad",
            selected = method == "Nagad"
        ) {
            method = "Nagad"
        }

        PaymentOption(
            name = "ব্যাংক ট্রান্সফার",
            selected = method == "Bank"
        ) {
            method = "Bank"
        }

        Spacer(Modifier.height(14.dp))

        OutlinedTextField(
            value = number,
            onValueChange = { number = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("উত্তোলনের ঠিকানা / নম্বর") },
            singleLine = true
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("উত্তোলনের পরিমাণ (৳)") },
            singleLine = true
        )

        Spacer(Modifier.height(18.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text("উত্তোলন রিকোয়েস্ট করুন")
        }
    }
}

@Composable
fun PaymentOption(
    name: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }
    ) {

        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                name,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold
            )

            RadioButton(
                selected = selected,
                onClick = onClick
            )
        }
    }
}

/* ---------------- PROFILE ---------------- */

@Composable
fun ProfilePage(onBack: () -> Unit) {

    SimpleTopBar("প্রোফাইল", onBack)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Row(
                modifier = Modifier.padding(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    "●",
                    color = Blue,
                    fontSize = 55.sp
                )

                Spacer(Modifier.width(15.dp))

                Column {
                    Text(
                        "Tahmid",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Text("tahmid@gmail.com")

                    Text(
                        "Level 1",
                        color = Color.White,
                        modifier = Modifier
                            .background(
                                Blue,
                                RoundedCornerShape(10.dp)
                            )
                            .padding(horizontal = 9.dp, vertical = 3.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(15.dp))

        ProfileRow("ব্যক্তিগত তথ্য")
        ProfileRow("পেমেন্ট তথ্য")
        ProfileRow("কাজের ইতিহাস")
        ProfileRow("রেফারেল লিংক")
        ProfileRow("সাপোর্ট")
    }
}

@Composable
fun ProfileRow(text: String) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {}
    ) {

        Row(
            modifier = Modifier.padding(17.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(text)

            Spacer(Modifier.weight(1f))

            Text("›")
        }
    }
}

/* ---------------- SETTINGS ---------------- */

@Composable
fun SettingsPage(onBack: () -> Unit) {

    SimpleTopBar("সেটিংস", onBack)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            "ভাষা / Language",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            RadioButton(
                selected = true,
                onClick = {}
            )

            Text("বাংলা")

            Spacer(Modifier.width(20.dp))

            RadioButton(
                selected = false,
                onClick = {}
            )

            Text("English")
        }

        Spacer(Modifier.height(15.dp))

        SettingRow("🔔", "নোটিফিকেশন")
        SettingRow("◐", "ডার্ক মোড")
        SettingRow("◈", "সিকিউরিটি")
        SettingRow("ⓘ", "অ্যাপ সম্পর্কে")
        SettingRow("☎", "সাহায্য ও সাপোর্ট")

        Spacer(Modifier.weight(1f))

        Text(
            "Version 1.0.0",
            color = Color.Gray,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun SettingRow(
    icon: String,
    title: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(icon, fontSize = 20.sp)

            Spacer(Modifier.width(14.dp))

            Text(title)

            Spacer(Modifier.weight(1f))

            Text("›")
        }
    }
}

/* ---------------- SIDE MENU ---------------- */

@Composable
fun SideMenu(
    onClose: () -> Unit,
    onPage: (String) -> Unit,
    onLogout: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.45f))
    ) {

        Column(
            modifier = Modifier
                .width(300.dp)
                .fillMaxHeight()
                .background(Navy)
                .padding(18.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    "●",
                    color = Color.White,
                    fontSize = 48.sp
                )

                Spacer(Modifier.width(12.dp))

                Column {

                    Text(
                        "Tahmid",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "tahmid@gmail.com",
                        color = Color.LightGray,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(Modifier.height(25.dp))

            MenuItem("⌂", "হোম") {
                onPage("home")
            }

            MenuItem("▣", "কাজ") {
                onPage("tasks")
            }

            MenuItem("৳", "আয়") {
                onPage("earnings")
            }

            MenuItem("↥", "উত্তোলন") {
                onPage("withdraw")
            }

            MenuItem("♣", "রেফার করুন") {
                onPage("home")
            }

            MenuItem("●", "প্রোফাইল") {
                onPage("profile")
            }

            MenuItem("◈", "সাপোর্ট") {
                onPage("home")
            }

            MenuItem("⚙", "সেটিংস") {
                onPage("settings")
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF8E2741)
                )
            ) {
                Text("লগআউট")
            }
        }

        Spacer(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable {
                    onClose()
                }
        )
    }
}

@Composable
fun MenuItem(
    icon: String,
    title: String,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            icon,
            color = Color.White,
            fontSize = 21.sp
        )

        Spacer(Modifier.width(17.dp))

        Text(
            title,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

/* ---------------- BOTTOM NAV ---------------- */

@Composable
fun BottomNavigation(
    page: String,
    onPage: (String) -> Unit
) {

    NavigationBar(
        containerColor = Color.White
    ) {

        NavigationBarItem(
            selected = page == "home",
            onClick = { onPage("home") },
            icon = { Text("⌂") },
            label = { Text("হোম") }
        )

        NavigationBarItem(
            selected = page == "tasks",
            onClick = { onPage("tasks") },
            icon = { Text("▣") },
            label = { Text("কাজ") }
        )

        NavigationBarItem(
            selected = page == "earnings",
            onClick = { onPage("earnings") },
            icon = { Text("৳") },
            label = { Text("আয়") }
        )

        NavigationBarItem(
            selected = page == "profile",
            onClick = { onPage("profile") },
            icon = { Text("●") },
            label = { Text("প্রোফাইল") }
        )
    }
}

/* ---------------- COMMON ---------------- */

@Composable
fun SimpleTopBar(
    title: String,
    onBack: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Navy)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            "←",
            color = Color.White,
            fontSize = 27.sp,
            modifier = Modifier.clickable {
                onBack()
            }
        )

        Spacer(Modifier.width(18.dp))

        Text(
            title,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun BalanceCard() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Green
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Text(
                "মোট আয়",
                color = Color.White
            )

            Text(
                "৳ 256.75",
                color = Color.White,
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TransactionItem(
    title: String,
    amount: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {

        Row(
            modifier = Modifier.padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                "●",
                color = Green,
                fontSize = 25.sp
            )

            Spacer(Modifier.width(12.dp))

            Text(
                title,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Medium
            )

            Text(
                amount,
                color = Green,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
