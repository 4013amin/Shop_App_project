package com.example.shop_app_project.Home_page.login

import RegisterScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shop_app_project.Home_page.Main.Screen_Item.CartPage
import com.example.shop_app_project.Home_page.Main.Screen_Item.ProductDetailsPage
import com.example.shop_app_project.Home_page.Main.Screen_Item.ProfilePage
import com.example.shop_app_project.Home_page.Main.Screen_Item.SearchPage
import com.example.shop_app_project.Home_page.Main.UiHomePage
import com.example.shop_app_project.data.view_model.ShoppingCartViewModel
import com.example.shop_app_project.data.view_model.UserViewModel
import com.example.shop_app_project.ui.theme.Shop_App_projectTheme

class MainLogin : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Shop_App_projectTheme {
                val navController = rememberNavController()
                val userViewModel: UserViewModel = viewModel()
                val shoppingCartViewModel: ShoppingCartViewModel = viewModel()
                var isLoggedIn by remember { mutableStateOf(userViewModel.checkCredentials()) }
                LaunchedEffect(isLoggedIn) {
                    if (isLoggedIn) {
                        navController.navigate("home")
                    }
                }
                bottomnavigations(navController, userViewModel, shoppingCartViewModel)
            }
        }
    }
}

data class navigationsItem(
    val route: String,
    val title: String,
    val icon: ImageVector,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun bottomnavigations(
    navController: NavController,
    userViewModel: UserViewModel,
    shoppingCartViewModel: ShoppingCartViewModel
) {

    val items = listOf(
        navigationsItem("ScreenRegister", "Register", Icons.Default.Person),
        navigationsItem("ScreenLogin", "Login", Icons.Default.PlayArrow)
    )


//    Scaffold(
//        bottomBar = {
//            NavigationBar {
//                val navBackStackEntry by navController.currentBackStackEntryAsState()
//                val currentRoute = navBackStackEntry?.destination?.route
//                items.forEach { item ->
//                    NavigationBarItem(
//                        icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
//                        label = { Text(item.title) },
//                        selected = currentRoute == item.route,
//                        onClick = {
//                            navController.navigate(item.route) {
//                                // Pop up to the start destination of the graph to
//                                // avoid building up a large stack of destinations
//                                // on the back stack as users select items
//                                popUpTo(navController.graph.startDestinationId) {
//                                    saveState = true
//                                }
//                                // Avoid multiple copies of the same destination when
//                                // reselecting the same item
//                                launchSingleTop = true
//                                // Restore state when reselecting a previously selected item
//                                restoreState = true
//                            }
//                        }
//                    )
//                }
//            }
//        }
//    ) { innerPadding ->
    NavHost(
        navController as NavHostController,
        startDestination = "ScreenRegister",
    ) {

        composable("ScreenRegister") { RegisterScreen(navController, userViewModel) }
        composable("ScreenLogin") { LoginScreen(navController, userViewModel) }
        composable("home") {
            UiHomePage(cartViewModel = shoppingCartViewModel, navController = navController)
        }
        composable("search") {
            SearchPage(
                navController = navController, shoppingCartViewModel
            )
        }
        composable("profile") { ProfilePage() }
        composable("singlePage") {
            ProductDetailsPage(navController , shoppingCartViewModel)
        }
        composable("cart") {
            CartPage(shoppingCartViewModel, navController)
        }

    }
}




