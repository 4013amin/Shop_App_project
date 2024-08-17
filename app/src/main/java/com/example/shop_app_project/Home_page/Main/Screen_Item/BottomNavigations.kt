import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.shop_app_project.Home_page.Main.Screen_Item.CartPage
import com.example.shop_app_project.Home_page.Main.Screen_Item.ProductDetailsPage
import com.example.shop_app_project.Home_page.Main.Screen_Item.ProfilePage
import com.example.shop_app_project.Home_page.Main.Screen_Item.SearchPage
import com.example.shop_app_project.Home_page.Main.UiHomePage
import com.example.shop_app_project.data.view_model.ShoppingCartViewModel
import com.example.shop_app_project.data.view_model.UserViewModel
import java.lang.reflect.Modifier

data class NavigationsItem(
    val route: String,
    val title: String,
    val icon: ImageVector,
)

val navItems = listOf(
    NavigationsItem("home", "Shop", Icons.Default.Home),
    NavigationsItem("search", "Explore", Icons.Default.Search),
    NavigationsItem("cart", "Cart", Icons.Default.ShoppingCart),
)

//var products = mutableStateOf<List<PorductModel>>(arrayListOf())

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigations(
    navController: NavHostController,
    userViewModel: UserViewModel,
    shoppingCartViewModel: ShoppingCartViewModel,
) {
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFFFFFFFF),
                contentColor = Color.White
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val cartItems by shoppingCartViewModel.cartItems.collectAsState()

                navItems.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            Box(
                            ) {
                                if (item.route == "cart") {
                                    BadgedBox(badge = {
                                        if (cartItems.isNotEmpty()) {
                                            Badge {
                                                Text(text = cartItems.size.toString())
                                            }
                                        }
                                    }) {
                                        Icon(
                                            imageVector = item.icon,
                                            contentDescription = item.title,
                                            modifier = androidx.compose.ui.Modifier.size(28.dp) // اندازه آیکون
                                        )
                                    }
                                } else {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = item.title,
                                        modifier = androidx.compose.ui.Modifier.size(24.dp) // اندازه آیکون
                                    )
                                }
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
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFFFF5722),
                            unselectedIconColor = Color.Black,
                            selectedTextColor = Color(0xFFFF5722),
                            unselectedTextColor = Color.Black
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavGraph(
            navController = navController,
            shoppingCartViewModel = shoppingCartViewModel,
            modifier = androidx.compose.ui.Modifier.padding(innerPadding)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    navController: NavHostController,
    shoppingCartViewModel: ShoppingCartViewModel,
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier
) {
    NavHost(navController = navController, startDestination = "home", modifier = modifier) {
        composable("home") {
            UiHomePage(cartViewModel = shoppingCartViewModel, navController = navController)
        }
        composable("search") {
            SearchPage(navController = navController, shoppingCartViewModel)
        }

        composable("singleProduct") {
            ProductDetailsPage(navController)
        }
        composable("cart") {
            CartPage(shoppingCartViewModel,navController)
        }
    }
}