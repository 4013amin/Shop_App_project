package com.example.shop_app_project.Home_page.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavigatorState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shop_app_project.ui.theme.Shop_App_projectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Shop_App_projectTheme {
                val navController = rememberNavController()
                bottom_navigations(navController)
            }
        }
    }
}

@Composable
fun nav() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Screen_register") {
        composable("Screen_register") {
            ScreenRegister(navController)
        }
        composable("Screen_login") {
            ScreenLogin(navController)
        }
    }
}


data class Navigations_Item(
    val route: String,
    val title: String,
    val icon: ImageVector
)

@Composable
fun bottom_navigations(navController: NavController) {
    val items = listOf(
        Navigations_Item("Screen_register", "Register", Icons.Default.Add),
        Navigations_Item("Screen_login", "Login", Icons.Default.AccountCircle)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.startDestinationId) {
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
    ) { innerPadding ->
        NavHost(
            navController as NavHostController,
            startDestination = "Screen_register",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("Screen_register") { ScreenRegister(navController) }
            composable("Screen_login") { ScreenLogin(navController) }
        }
    }
}
