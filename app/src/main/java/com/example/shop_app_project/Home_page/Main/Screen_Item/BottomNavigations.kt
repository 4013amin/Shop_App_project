package com.example.shop_app_project.Home_page.Main.Screen_Item//package com.example.shop_app_project.Home_page.Main.Screen_Item

import androidx.compose.foundation.layout.padding

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.shop_app_project.Home_page.Main.UiHomePage
import com.example.shop_app_project.data.models.product.PorductModel
import com.example.shop_app_project.data.view_model.ShoppingCartViewModel
import com.example.shop_app_project.data.view_model.UserViewModel
import com.google.gson.Gson


data class NavigationsItem(
    val route: String,
    val title: String,
    val icon: ImageVector,
)
//
//val navItems = listOf(
//    NavigationsItem("home", "Shop", Icons.Default.Shop),
//    NavigationsItem("search", "Explore", Icons.Default.Search),
//    NavigationsItem("cart", "Cart", Icons.Default.ShoppingCart),
//    NavigationsItem("profile", "Profile", Icons.Default.Person)
//)

var products = mutableStateOf<List<PorductModel>>(arrayListOf())

@Composable
fun BottomNavigations(
    navController: NavController,
    userViewModel: UserViewModel,
    shoppingCartViewModel: ShoppingCartViewModel,
) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val cartItems by shoppingCartViewModel.cartItems.collectAsState()

//                navItems.forEach { item ->
//                    NavigationBarItem(
//                        icon = {
//                            if (item.route == "cart") {
//                                BadgedBox(badge = {
//                                    if (cartItems.isNotEmpty()) {
//                                        Badge {
//                                            Text(text = cartItems.size.toString())
//                                        }
//                                    }
//                                }) {
//                                    Icon(imageVector = item.icon, contentDescription = item.title)
//                                }
//                            } else {
//                                Icon(imageVector = item.icon, contentDescription = item.title)
//                            }
//                        },
//                        label = { Text(item.title) },
//                        selected = currentRoute == item.route,
//                        onClick = {
//                            navController.navigate(item.route) {
//                                popUpTo(navController.graph.startDestinationId) {
//                                    saveState = true
//                                }
//                                launchSingleTop = true
//                                restoreState = true
//                            }
//                        }
//                    )
//                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController as NavHostController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                UiHomePage(cartViewModel = shoppingCartViewModel, navController = navController)
            }
            composable("search") {
                SearchPage(
                    userViewModel = userViewModel,
                    shoppingCartViewModel = shoppingCartViewModel,
                    navController = navController
                )
            }
            composable("profile") { ProfilePage() }
            composable("singlePage") {
                ProductDetailsPage(navController)
            }
            composable("cart") {
                CartPage(shoppingCartViewModel)
            }
        }
    }
}

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    userViewModel: UserViewModel,
    shoppingCartViewModel: ShoppingCartViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            UiHomePage(cartViewModel = shoppingCartViewModel, navController = navController)
        }
        composable("singleProduct") {
            ProductDetailsPage(navController)
        }
        composable("cart") {
            CartPage(shoppingCartViewModel)
        }
    }
}
