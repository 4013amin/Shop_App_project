package com.example.shop_app_project.Home_page.Main.Screen_Item

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.shop_app_project.Home_page.Main.UiHomePage
import com.example.shop_app_project.data.models.product.PorductModel
import com.example.shop_app_project.data.view_model.ShoppingCartViewModel
import com.example.shop_app_project.data.view_model.UserViewModel


data class NavigationsItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)

val navItems = listOf(
    NavigationsItem("home", "Home", Icons.Default.Home),
    NavigationsItem("search", "Search", Icons.Default.Search),
    NavigationsItem("cart", "Cart", Icons.Default.ShoppingCart),
    NavigationsItem("profile", "Profile", Icons.Default.Person)
)
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

                navItems.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            if (item.route == "cart") {
                                BadgedBox(badge = {
                                    if (cartItems.isNotEmpty()) {
                                        Badge {
                                            Text(text = cartItems.size.toString())
                                        }
                                    }
                                }) {
                                    Icon(imageVector = item.icon, contentDescription = item.title)
                                }
                            } else {
                                Icon(imageVector = item.icon, contentDescription = item.title)
                            }
                        },
                        label = { Text(item.title) },
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController as NavHostController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                UiHomePage(
                    userViewModel = userViewModel,
                    cartViewModel = shoppingCartViewModel,
                    navController = navController
                )
            }
            composable("search") {
                SearchPage(
                    userViewModel = userViewModel,
                    shoppingCartViewModel = shoppingCartViewModel,
                    navController = navController
                )
            }
            composable("cart") { CartPage(cartViewModel = shoppingCartViewModel) }
            composable("profile") { ProfilePage() }
            composable("single_product/{productId}") { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId")?.toInt()
                productId?.let {
                    ProductDetailsPage(
                        navController = navController,
                        productId = it,
                        userViewModel = userViewModel ,
                        shoppingCartViewModel
                    )
                }
            }

        }
    }
}