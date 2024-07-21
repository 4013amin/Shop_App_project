package com.example.shop_app_project.Home_page.Main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import com.google.gson.Gson
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberImagePainter
import com.example.shop_app_project.R
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

val gson = Gson()

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
    val imageRes: Int,
)

data class ProductModel(
    val name: String,
    val description: String,
    val price: Int,
    val image: String,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiHomePage(
    userViewModel: UserViewModel = viewModel(),
    cartViewModel: ShoppingCartViewModel,
    navController: NavController,
) {
    val categories = listOf(
        CategoryModel("Cat", R.drawable.cat),
        CategoryModel("Dog", R.drawable.dog),
        CategoryModel("Test", R.drawable.logo),
        // add more categories as needed
    )

    val products = listOf(
        ProductModel(
            "Dog Food",
            "High-quality dog food",
            50,
            "https://via.placeholder.com/150"
        ),
        ProductModel("Cat Toy", "Fun toy for cats", 20, "https://via.placeholder.com/150"),
        ProductModel("Bird Cage", "Spacious bird cage", 150, "https://via.placeholder.com/150"),
        ProductModel("Fish Tank", "Large fish tank", 100, "https://via.placeholder.com/150"),
        ProductModel(
            "Rabbit Hutch",
            "Comfortable hutch for rabbits",
            120,
            "https://via.placeholder.com/150"
        )
    )

    Scaffold(
        modifier = Modifier
            .background(color = Color.White)
            .padding(0.dp),
        topBar = {
            TopAppBar(
                title = { Text(text = "Pet Store") },
                navigationIcon = {
                    IconButton(onClick = {
                        // Handle menu click
                    }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("cart")
                    }) {
                        BadgedBox(badge = {
                            val cartItems by cartViewModel.cartItems.collectAsState()
                            if (cartItems.isNotEmpty()) {
                                Badge {
                                    Text(text = cartItems.size.toString())
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Cart"
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            item {
                Text(
                    text = "Shop",
                    fontSize = 30.sp,
                    color = Color(0xFF047D09),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(18.dp))
            }

            // Categories
            item {
                LazyRow(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    items(categories) { category ->
                        CategoryItem(imageRes = category.imageRes, name = category.name)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(18.dp))
            }

            // Products
            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    items(products) { product ->
                        ProductItem(
                            name = product.name,
                            description = product.description,
                            price = product.price,
                            image = product.image,
                            addToCart = {
                            },
                            onClick = {
                                val productJson = gson.toJson(product)
                                navController.navigate("single_product?product=$productJson")
                            }
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }

            //CatProduct
            item {
                Text(
                    text = "Cat",
                    fontSize = 30.sp,
                    color = Color(0xFF047D09),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(18.dp))
            }

            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {

                    items(products) { product ->
                        ProductItem(
                            name = product.name,
                            description = product.description,
                            price = product.price,
                            image = product.image,
                            addToCart = {
                            },
                            onClick = {
                                val productJson = gson.toJson(product)
                                navController.navigate("single_product?product=$productJson")
                            }
                        )
                    }
                }
            }

            //DogProduct
            item {
                Text(
                    text = "Dog",
                    fontSize = 30.sp,
                    color = Color(0xFF047D09),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            item {
                LazyRow(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                ) {
                    items(products) { product ->
                        ProductItem(
                            name = product.name,
                            description = product.description,
                            price = product.price,
                            image = product.image,
                            addToCart = { /*TODO*/ }) {

                        }
                    }
                }
            }
        }
    }
}


//
//
//            item {
//                LazyRow(
//                    modifier = Modifier.padding(vertical = 8.dp)
//                ) {
//                    items(products) { product ->
//                        ProductItem(
//                            name = product.name,
//                            description = product.description,
//                            price = product.price,
//                            image = product.image,
//                            addToCart = {
////                                cartViewModel.addToCart(product)
//                            },
//                            onClick = {
//                                val productJson = gson.toJson(product)
//                                navController.navigate("single_product?product=$productJson")
//                            }
//                        )
//                    }
//                }
//            }
//        }
//    }
//}


@Composable
fun CategoryItem(imageRes: Int, name: String) {
    val isPressed = remember { mutableStateOf(false) }
    val backgroundColor =
        if (isPressed.value) Color.Green else Color(0xFFA5D6A7)

    Box(
        modifier = Modifier
            .padding(8.dp)
            .width(150.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
            .height(100.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        isPressed.value = true
                    },
                    onPress = {
                        tryAwaitRelease()
                        isPressed.value = false
                    }
                )
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = name,
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
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
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .width(180.dp)
            .padding(8.dp)
            .background(Color(0xFFE8F5E9), RoundedCornerShape(8.dp))
            .padding(8.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(image),
            contentDescription = null,
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B5E20)
        )
        Text(
            text = description,
            fontSize = 12.sp,
            color = Color.Gray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "$$price",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF388E3C)
        )
        Spacer(modifier = Modifier.height(4.dp))
        IconButton(
            onClick = addToCart,

            ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart, contentDescription = "", tint = Color(
                    0xDF008A06
                )
            )
        }
    }
}

