package com.example.shop_app_project.Home_page.Main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.shop_app_project.Home_page.Main.Screen_Item.BottomNavigations
import com.example.shop_app_project.data.view_model.ShoppingCartViewModel
import com.example.shop_app_project.data.view_model.UserViewModel
import com.example.shop_app_project.ui.theme.Shop_App_projectTheme

@ExperimentalFoundationApi
class MainPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Shop_App_projectTheme {
                val navController = rememberNavController()
                val userViewModel: UserViewModel = viewModel()
                val shoppingCartViewModel: ShoppingCartViewModel = viewModel()

                Column(modifier = Modifier.fillMaxSize()) {
                    UiHomePage(userViewModel = userViewModel, shoppingCartViewModel)
                }

                BottomNavigations(navController, userViewModel , shoppingCartViewModel)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UiHomePage(userViewModel: UserViewModel = viewModel(), cartViewModel: ShoppingCartViewModel) {
    userViewModel.getAllProducts()
    val products by userViewModel.products
    val pagerState = rememberPagerState(pageCount = { products.size })

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
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val product = products[page]
                ProductItem(
                    name = product.name,
                    description = product.description,
                    price = product.price,
                    image = product.image,
                    addToCart = {
                        cartViewModel.addToCart(product)
                    }
                )
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
    addToCart: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberImagePainter(image),
            contentDescription = null,
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = name, fontSize = 24.sp, color = Color.Black)
        Text(text = description, fontSize = 16.sp, color = Color.Gray)
        Text(text = "$$price", fontSize = 16.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = addToCart) {
            Text(text = "Add to Cart")
        }
    }
}
