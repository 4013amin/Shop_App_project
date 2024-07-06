package com.example.shop_app_project.Home_page.Main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.shop_app_project.Home_page.Main.Screen_Item.BottomNavigations
import com.example.shop_app_project.Home_page.login.ScreenRegister
import com.example.shop_app_project.data.models.product.PorductModel
import com.example.shop_app_project.data.view_model.ShoppingCartViewModel
import com.example.shop_app_project.data.view_model.UserViewModel
import com.example.shop_app_project.ui.theme.Shop_App_projectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Shop_App_projectTheme {
                val navController = rememberNavController()
                val userViewModel: UserViewModel = viewModel()
                val shoppingCartViewModel: ShoppingCartViewModel = viewModel()


                BottomNavigations(navController, userViewModel, shoppingCartViewModel)
            }
        }
    }
}

data class CategoryModel(
    val name: String,
    val products: List<PorductModel>
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UiHomePage(
    userViewModel: UserViewModel = viewModel(),
    cartViewModel: ShoppingCartViewModel,
    navController: NavController
) {
    userViewModel.getAllProducts()
    val products by userViewModel.products

    // Sample categories, you can get them from your viewmodel
    val categories = listOf(
        CategoryModel("Category 1", products.take(10)),
        CategoryModel("Category 2", products.drop(10).take(10)),
        CategoryModel("Category 3", products.drop(20))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Products", fontSize = 32.sp, modifier = Modifier.padding(bottom = 16.dp))

        if (products.isEmpty()) {
            Log.d("UiHomePage", "No products available.")
        } else {
            // HorizontalPager for top products
            val pagerState = rememberPagerState(pageCount = { 10.coerceAtMost(products.size) })

            LaunchedEffect(Unit) {
                while (true) {
                    pagerState.animateScrollToPage((pagerState.currentPage + 1) % pagerState.pageCount)
                    kotlinx.coroutines.delay(3000) // Delay for 3 seconds between each scroll
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 100.dp) // Add horizontal padding
            ) { page ->
                val product = products[page]
                ProductItem(
                    name = product.name,
                    description = product.description,
                    price = product.price,
                    image = product.image,
                    addToCart = {
                        cartViewModel.addToCart(product)
                    },
                    onClick = {}
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // LazyColumn for categorized products
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(categories) { category ->
                    Text(
                        text = category.name,
                        fontSize = 24.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp)
                    )

                    LazyRow {
                        items(category.products) { product ->
                            ProductItem(
                                name = product.name,
                                description = product.description,
                                price = product.price,
                                image = product.image,
                                addToCart = {
                                    cartViewModel.addToCart(product)
                                },
                                onClick = {}
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(
    name: String,
    description: String,
    price: Int,
    image: String,
    addToCart: () -> Unit,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(180.dp) // Set a fixed width for items in LazyRow
            .padding(8.dp)
            .background(Color.LightGray)
            .padding(8.dp) // Reduce padding for better appearance
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(image),
            contentDescription = null,
            modifier = Modifier
                .height(120.dp) // Reduce height for better appearance in LazyRow
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp)) // Reduce space between elements
        Text(text = name, fontSize = 14.sp, color = Color.Black)
        Text(text = description, fontSize = 10.sp, color = Color.Gray)
        Text(text = "$$price", fontSize = 12.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(4.dp))
        Button(onClick = addToCart) {
            Text(text = "Add to Cart", fontSize = 12.sp) // Reduce button text size
        }
    }
}
