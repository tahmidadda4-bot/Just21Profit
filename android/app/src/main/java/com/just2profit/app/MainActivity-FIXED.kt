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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Navy = Color(0xFF062B4F)
private val Green = Color(0xFF10C978)
private val Soft = Color(0xFFF4F7FA)
private val Blue = Color(0xFF1478D4)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Just2ProfitApp() }
    }
}

data class JTask(val title: String, val subtitle: String, val reward: String, val icon: String)

@Composable
fun Just2ProfitApp() {
    var screen by remember { mutableStateOf("home") }
    var drawer by remember { mutableStateOf(false) }
    var loggedIn by remember { mutableStateOf(false) }

    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Navy,
            secondary = Green,
            background = Soft,
            surface = Color.White
        )
    ) {
        if (!loggedIn) {
            AuthScreen(onLogin = { loggedIn = true }, onRegister = { loggedIn = true })
        } else {
            Box(Modifier.fillMaxSize().background(Soft)) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Just2Profit", fontWeight = FontWeight.Bold) },
                            navigationIcon = { Text("☰", fontSize = 26.sp, modifier = Modifier.padding(start = 16.dp).clickable { drawer = true }) },
                            actions = { Text("♧", fontSize = 24.sp, modifier = Modifier.padding(end = 16.dp)) }
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            NavItem("⌂", "হোম", screen == "home") { screen = "home" }
                            NavItem("▣", "কাজ", screen == "tasks") { screen = "tasks" }
                            NavItem("৳", "আয়", screen == "earnings") { screen = "earnings" }
                            NavItem("♙", "প্রোফাইল", screen == "profile") { screen = "profile" }
                        }
                    }
                ) { pad ->
                    when (screen) {
                        "tasks" -> TasksScreen(Modifier.padding(pad))
                        "earnings" -> EarningsScreen(Modifier.padding(pad))
                        "withdraw" -> WithdrawScreen(Modifier.padding(pad))
                        "profile" -> ProfileScreen(Modifier.padding(pad))
                        "settings" -> SettingsScreen(Modifier.padding(pad))
                        else -> HomeScreen(Modifier.padding(pad)) { screen = "tasks" }
                    }
                }
                if (drawer) {
                    SideMenu(
                        onClose = { drawer = false },
                        onSelect = { s -> drawer = false; screen = s }
                    )
                }
            }
        }
    }
}

@Composable
fun NavItem(icon: String, label: String, selected: Boolean, onClick: () -> Unit) {
    NavigationBarItem(selected = selected, onClick = onClick, icon = { Text(icon, fontSize = 22.sp) }, label = { Text(label) })
}

@Composable
fun AuthScreen(onLogin: () -> Unit, onRegister: () -> Unit) {
    var register by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize().background(Color.White).padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(40.dp))
        Text("📈", fontSize = 62.sp)
        Text("Just2Profit", fontSize = 34.sp, fontWeight = FontWeight.ExtraBold)
        Text("Work Smart • Earn More", color = Green, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(35.dp))
        Text(if (register) "রেজিস্টার করুন" else "লগইন করুন", fontSize = 25.sp, fontWeight = FontWeight.Bold)
        Text(if (register) "একটি নতুন অ্যাকাউন্ট তৈরি করুন" else "আপনার অ্যাকাউন্টে প্রবেশ করুন", color = Color.Gray)
        Spacer(Modifier.height(20.dp))
        if (register) {
            AppField("👤", "আপনার নাম")
            AppField("✉", "ইমেইল ঠিকানা")
            AppField("☎", "ফোন নম্বর")
            AppField("🔒", "পাসওয়ার্ড")
            AppField("🔒", "পাসওয়ার্ড নিশ্চিত করুন")
            PrimaryButton("রেজিস্টার করুন", onRegister)
            TextButton(onClick = { register = false }) { Text("ইতিমধ্যে অ্যাকাউন্ট আছে? লগইন করুন") }
        } else {
            AppField("✉", "ইমেইল / ফোন নম্বর")
            AppField("🔒", "পাসওয়ার্ড")
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = false, onCheckedChange = {})
                Text("আমাকে মনে রাখুন")
                Spacer(Modifier.weight(1f))
                Text("পাসওয়ার্ড ভুলে গেছেন?", color = Blue, fontSize = 12.sp)
            }
            PrimaryButton("লগইন করুন", onLogin)
            Text("অথবা", Modifier.padding(10.dp), color = Color.Gray)
            OutlinedButton(onClick = onLogin, Modifier.fillMaxWidth()) { Text("G  Google দিয়ে লগইন করুন") }
            TextButton(onClick = { register = true }) { Text("এখনও অ্যাকাউন্ট নেই? রেজিস্টার করুন") }
        }
    }
}

@Composable
fun AppField(icon: String, hint: String) {
    OutlinedTextField(value = "", onValueChange = {}, placeholder = { Text(hint) }, leadingIcon = { Text(icon) }, singleLine = true, modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp))
}

@Composable
fun PrimaryButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick, Modifier.fillMaxWidth().height(52.dp), colors = ButtonDefaults.buttonColors(containerColor = Navy), shape = RoundedCornerShape(8.dp)) { Text(text) }
}

@Composable
fun HomeScreen(modifier: Modifier, onTasks: () -> Unit) {
    val tasks = listOf(
        JTask("ভিডিও দেখুন", "30 সেকেন্ড", "৳ 5.00", "▶"),
        JTask("অ্যাপ রিভিউ দিন", "2 মিনিট", "৳ 10.00", "★")
    )
    LazyColumn(modifier.fillMaxSize().padding(14.dp)) {
        item {
            Text("সুপ্রভাত, Tahmid", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Level 1", color = Color.Gray)
            Spacer(Modifier.height(12.dp))
            BalanceCard("মোট আয়", "৳ 256.75")
            Spacer(Modifier.height(12.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StatCard("আজকের কাজ", "3/5", "▣", Modifier.weight(1f))
                StatCard("মোট কাজ", "12", "✓", Modifier.weight(1f))
            }
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StatCard("ইন্টারনাল বোনাস", "৳ 0.00", "৳", Modifier.weight(1f))
                StatCard("রেফারেল", "2", "♣", Modifier.weight(1f))
            }
            Spacer(Modifier.height(18.dp))
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text("আজকের কাজ", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.weight(1f)); Text("সব দেখুন ›", color = Blue, modifier = Modifier.clickable { onTasks() })
            }
            Spacer(Modifier.height(8.dp))
        }
        items(tasks) { TaskRow(it) }
    }
}

@Composable
fun BalanceCard(title: String, amount: String) {
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = Green)) {
        Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) { Text(title, color = Color.White); Text(amount, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold) }
            Text("▣", color = Color.White, fontSize = 34.sp)
        }
    }
}

@Composable
fun StatCard(title: String, value: String, icon: String, modifier: Modifier) {
    Card(modifier, shape = RoundedCornerShape(12.dp)) { Column(Modifier.padding(14.dp)) { Text(icon, color = Green, fontSize = 22.sp); Text(title, fontSize = 12.sp, color = Color.Gray); Text(value, fontSize = 18.sp, fontWeight = FontWeight.Bold) } }
}

@Composable
fun TaskRow(task: JTask) {
    Card(Modifier.fillMaxWidth().padding(vertical = 4.dp), shape = RoundedCornerShape(12.dp)) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(task.icon, fontSize = 28.sp, modifier = Modifier.padding(end = 12.dp))
            Column(Modifier.weight(1f)) { Text(task.title, fontWeight = FontWeight.Bold); Text(task.subtitle, color = Color.Gray, fontSize = 12.sp) }
            Text(task.reward, color = Green, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun TasksScreen(modifier: Modifier) {
    val tasks = listOf(
        JTask("ভিডিও দেখুন", "30 সেকেন্ড", "৳ 5.00", "▶"),
        JTask("অ্যাপ রিভিউ দিন", "2 মিনিট", "৳ 10.00", "★"),
        JTask("সার্ভে পূরণ করুন", "3 মিনিট", "৳ 15.00", "▣"),
        JTask("ওয়েবসাইট ভিজিট", "1 মিনিট", "৳ 5.00", "↗"),
        JTask("বন্ধুকে রেফার করুন", "প্রতি রেফারেলে", "৳ 50.00", "♣")
    )
    LazyColumn(modifier.fillMaxSize().padding(14.dp)) {
        item { Text("কাজের তালিকা", fontSize = 24.sp, fontWeight = FontWeight.Bold); Spacer(Modifier.height(12.dp)) }
        items(tasks) { task ->
            Card(Modifier.fillMaxWidth().padding(vertical = 5.dp), shape = RoundedCornerShape(12.dp)) {
                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(task.icon, fontSize = 28.sp, modifier = Modifier.padding(end = 12.dp))
                    Column(Modifier.weight(1f)) { Text(task.title, fontWeight = FontWeight.Bold); Text("${task.subtitle} • ${task.reward}", color = Color.Gray) }
                    Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Green)) { Text("কাজ করুন") }
                }
            }
        }
    }
}

@Composable
fun EarningsScreen(modifier: Modifier) {
    LazyColumn(modifier.fillMaxSize().padding(14.dp)) {
        item { BalanceCard("মোট আয়", "৳ 256.75"); Spacer(Modifier.height(14.dp)); Text("আমার আয়", fontSize = 24.sp, fontWeight = FontWeight.Bold); Spacer(Modifier.height(8.dp)) }
        items(listOf("ভিডিও দেখার জন্য" to "৳ 5.00", "অ্যাপ রিভিউ" to "৳ 10.00", "রেফারেল বোনাস" to "৳ 50.00", "কাজের আয়" to "৳ 15.00")) { (a,b) ->
            Card(Modifier.fillMaxWidth().padding(vertical = 4.dp)) { Row(Modifier.padding(14.dp)) { Text(a, Modifier.weight(1f)); Text("+ $b", color = Green, fontWeight = FontWeight.Bold) } }
        }
    }
}

@Composable
fun WithdrawScreen(modifier: Modifier) {
    var method by remember { mutableStateOf("bKash") }
    Column(modifier.fillMaxSize().padding(16.dp)) {
        Text("উত্তোলন", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp)); BalanceCard("উপলব্ধ ব্যালেন্স", "৳ 256.75")
        Spacer(Modifier.height(18.dp)); Text("উত্তোলনের পদ্ধতি", fontWeight = FontWeight.Bold)
        listOf("bKash", "Nagad", "ব্যাংক ট্রান্সফার").forEach { m ->
            Card(Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable { method = m }) { Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) { RadioButton(method == m, { method = m }); Text(m) } }
        }
        AppField("☎", "অ্যাকাউন্ট নম্বর")
        AppField("৳", "উত্তোলনের পরিমাণ")
        Spacer(Modifier.height(8.dp)); PrimaryButton("উত্তোলনের রিকোয়েস্ট করুন") {}
    }
}

@Composable
fun ProfileScreen(modifier: Modifier) {
    Column(modifier.fillMaxSize().padding(16.dp)) {
        Card(Modifier.fillMaxWidth()) { Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) { Text("👤", fontSize = 48.sp); Spacer(Modifier.width(12.dp)); Column { Text("Tahmid", fontWeight = FontWeight.Bold, fontSize = 20.sp); Text("tahmid@gmail.com", color = Color.Gray); Text("Level 1", color = Blue) } } }
        Spacer(Modifier.height(12.dp)); Text("12", fontWeight = FontWeight.Bold); Text("মোট কাজ"); Text("৳ 256.75", fontWeight = FontWeight.Bold); Text("মোট আয়"); Text("2", fontWeight = FontWeight.Bold); Text("রেফারেল")
        Spacer(Modifier.height(15.dp)); listOf("ব্যক্তিগত তথ্য", "পেমেন্ট তথ্য", "কাজের ইতিহাস", "রেফারেল লিংক", "সাপোর্ট").forEach { item -> Card(Modifier.fillMaxWidth().padding(vertical = 3.dp)) { Text(item + "  ›", Modifier.padding(16.dp)) } }
    }
}

@Composable
fun SettingsScreen(modifier: Modifier) {
    Column(modifier.fillMaxSize().padding(16.dp)) {
        Text("সেটিংস", fontSize = 24.sp, fontWeight = FontWeight.Bold); Spacer(Modifier.height(15.dp))
        Card(Modifier.fillMaxWidth()) { Column(Modifier.padding(16.dp)) { Text("ভাষা / Language", fontWeight = FontWeight.Bold); Text("◉ বাংলা"); Text("○ English") } }
        Spacer(Modifier.height(8.dp)); listOf("🔔 নোটিফিকেশন", "◐ ডার্ক মোড", "🛡 সিকিউরিটি", "ℹ অ্যাপ সম্পর্কে", "♧ সাহায্য ও সাপোর্ট").forEach { x -> Card(Modifier.fillMaxWidth().padding(vertical = 3.dp)) { Text(x, Modifier.padding(16.dp)) } }
        Spacer(Modifier.height(20.dp)); Text("Version 1.0.0", color = Color.Gray, modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}

@Composable
fun SideMenu(onClose: () -> Unit, onSelect: (String) -> Unit) {
    Box(Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.35f))) {
        Column(Modifier.width(310.dp).fillMaxHeight().background(Navy).padding(18.dp)) {
            Text("Just2Profit", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold)
            Text("Tahmid", color = Color.White, modifier = Modifier.padding(top = 20.dp))
            Spacer(Modifier.height(18.dp))
            listOf("⌂  হোম" to "home", "▣  কাজ" to "tasks", "◉  আয়" to "earnings", "৳  উত্তোলন" to "withdraw", "♣  রেফার করুন" to "home", "♙  প্রোফাইল" to "profile", "◉  সাপোর্ট" to "home", "⚙  সেটিংস" to "settings").forEach { (label, s) ->
                Text(label, color = Color.White, fontSize = 17.sp, modifier = Modifier.fillMaxWidth().clickable { onSelect(s) }.padding(vertical = 12.dp))
            }
            Spacer(Modifier.weight(1f)); OutlinedButton(onClick = onClose, Modifier.fillMaxWidth()) { Text("↪ লগআউট", color = Color.White) }
        }
    }
}
