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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.shop_app_project.Home_page.Main.Screen_Item.BottomNavigations
import com.example.shop_app_project.data.view_model.ShoppingCartViewModel
import com.example.shop_app_project.data.view_model.UserViewModel
import com.example.shop_app_project.ui.theme.Shop_App_projectTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.shop_app_project.data.models.product.Category
import kotlinx.coroutines.delay

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
    val image: String // URL تصویر
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UiHomePage(
    userViewModel: UserViewModel = viewModel(),
    cartViewModel: ShoppingCartViewModel,
    navController: NavController
) {
    val products by userViewModel.products
    val categories by userViewModel.category

    LaunchedEffect(Unit) {
        userViewModel.getAllProducts()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Location",
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = "New York, USA",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }

        item {
            Text(
                text = "#SpecialForYou",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        if (products.isEmpty()) {
            item {
                Log.d("UiHomePage", "No products available.")
            }
        } else {
            val cyclicProducts = products.cycle()
            item {
                val pagerState = rememberPagerState(pageCount = { cyclicProducts.size / 3 })

                LaunchedEffect(Unit) {
                    while (true) {
                        pagerState.animateScrollToPage((pagerState.currentPage + 1) % pagerState.pageCount)
                        delay(3000)
                    }
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 0.dp)
                ) { page ->
                    val start = page * 3
                    val end = (start + 3).coerceAtMost(cyclicProducts.size)
                    val productsInPage = cyclicProducts.subList(start, end)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        productsInPage.forEach { product ->
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

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "بیشتر",
                        fontSize = 14.sp,
                        color = Color.Red,
                        modifier = Modifier.clickable { /* TODO: See All action */ }
                    )
                    Text(
                        text = "دسته بندی ها",
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
            }

            item {
                val pagerState =
                    rememberPagerState(pageCount = { categories.size / categories.size })

                LaunchedEffect(Unit) {
                    while (true) {
                        pagerState.animateScrollToPage((pagerState.currentPage + 1) % pagerState.pageCount)
                        delay(100)
                    }
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) { page ->
                    val start = page * 10
                    val end = (start + 10).coerceAtMost(categories.size)
                    val categoriesInPage = categories.subList(start, end)

                    LazyRow(
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        items(categoriesInPage) { category ->
                            CategoryItem(image = category.image, name = category.name)
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "بیشتر",
                        fontSize = 14.sp,
                        color = Color.Red,
                        modifier = Modifier.clickable { }
                    )
                    Text(
                        text = "محصولات",
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
            }

            item {
                LazyRow(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    items(products) { product ->
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

fun <T> List<T>.cycle(): List<T> {
    return this + this
}

@Composable
fun CategoryItem(image: String, name: String) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = image),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.LightGray),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            fontSize = 16.sp,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
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
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)), // Make image rounded
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp)) // Reduce space between elements
        Text(text = name, fontSize = 14.sp, color = Color.Black) // Set text color to white
        Text(
            text = description,
            fontSize = 10.sp,
            color = Color.Gray,
            maxLines = 1, // Limit to 1 line
            overflow = TextOverflow.Ellipsis // Add ellipsis if the text is too long
        )
        Text(text = "$$price", fontSize = 12.sp, color = Color.Black) // Set text color to white
        Spacer(modifier = Modifier.height(4.dp))
        Button(onClick = addToCart) {
            Text(text = "Add to Cart", fontSize = 12.sp) // Reduce button text size
        }
    }
}
