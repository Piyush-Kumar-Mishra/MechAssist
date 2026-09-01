package com.example.mechassist.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.mechassist.viewmodel.MechanicViewModel

sealed class BottomNavItem(val title: String, val icon: ImageVector) {
    object Dashboard : BottomNavItem("Dashboard", Icons.Default.Home)
    object History : BottomNavItem("History", Icons.Default.DateRange)
    object Profile : BottomNavItem("Profile", Icons.Default.Person)
}

@Composable
fun MainScreen(
    mechanicViewModel: MechanicViewModel,
    onMechanicClick: (String) -> Unit
) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    val items = listOf(
        BottomNavItem.Dashboard,
        BottomNavItem.History,
        BottomNavItem.Profile
    )

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = Color(0xFF1C1B1F)
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF1C1B1F),
                            selectedTextColor = Color(0xFF1C1B1F),
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray,
                            indicatorColor = Color(0xFFF0F0F0)
                        )
                    )
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (selectedIndex) {
                0 -> DashboardScreen(
                    viewModel = mechanicViewModel,
                    onMechanicClick = onMechanicClick
                )
                1 -> HistoryScreen()
                2 -> ProfileScreen()
            }
        }
    }
}
