package com.example.shop_app_project.Home_page.Main.Screen_Item

import android.annotation.SuppressLint
import com.example.shop_app_project.data.view_model.ShoppingCartViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.shop_app_project.Home_page.Main.ProductItem
import com.example.shop_app_project.R
import com.example.shop_app_project.data.view_model.UserViewModel
import com.google.gson.Gson

var gson = Gson()


data class CategoryModel(
    val name: String,
    val imageRes: Int,
)

data class prductmodelfack(
    val name: String,
    val des: String,
    val price: Int,
    val image: Int
)


@ExperimentalMaterial3Api
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchPage(navController: NavController, shoppingCartViewModel: ShoppingCartViewModel) {
    SearchBar()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Explore Categories") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFF3E0))
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                SearchBar()
                Spacer(modifier = Modifier.height(16.dp))
                CategoryFilter()
                Spacer(modifier = Modifier.height(16.dp))

                ProductGrid(shoppingCartViewModel, navController)
            }
        }
    )
}

@ExperimentalMaterial3Api
@Composable
fun SearchBar() {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    TextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text(text = "Search Product or Brand") },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp)),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}


@Composable
fun CategoryFilter() {
    val categories = listOf("All", "Dog", "Cat", "Small Animal", "Bird")
    var selectedCategory by remember { mutableStateOf("All") }

    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(categories) { category ->
            CategoryChip(
                category = category,
                isSelected = selectedCategory == category,
                onClick = { selectedCategory = category }
            )
        }
    }
}

@Composable
fun CategoryChip(category: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) Color(0xFFFFE0B2) else Color.White)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = category, color = if (isSelected) Color.Black else Color.Gray)
    }
}

@Composable
fun ProductGrid(
    cartViewModel: ShoppingCartViewModel,
    navController: NavController
) {
    val products = listOf(
        ProductModel("Dog Food", "wdadwd", 2500, R.drawable.tools),
        ProductModel("Dog Food", "wdadwd", 2500, R.drawable.tools),
        ProductModel("Dog Food", "wdadwd", 2500, R.drawable.tools),
        ProductModel("Dog Food", "wdadwd", 2500, R.drawable.tools),
        ProductModel("Dog Food", "wdadwd", 2500, R.drawable.tools),
        ProductModel("Dog Food", "wdadwd", 2500, R.drawable.tools),
        ProductModel("Dog Food", "wdadwd", 2500, R.drawable.tools),
        ProductModel("Dog Food", "wdadwd", 2500, R.drawable.tools),
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(products.chunked(2)) { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                rowItems.forEach { product ->
                    ProductItem(
                        name = product.name,
                        description = product.description,
                        price = product.price,
                        image = product.image,
                        addToCart = {
//                            cartViewModel.addToCart(product)
                        },
                        onClick = {

                            navController.navigate("singlePage")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ProductCard(product: prductmodelfack) {
    val navController = rememberNavController()
    val userViewModel: UserViewModel = viewModel()
    val shoppingCartViewModel: ShoppingCartViewModel = viewModel()

    Box(
        modifier = Modifier
            .background(Color(0xFFE0F7FA))
            .clip(shape = RoundedCornerShape(20.dp))
            .padding(10.dp)
            .clickable {
                navController.navigate("singleProduct")
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier
                .background(Color(0xFFE0F7FA))
                .padding(13.dp)
        ) {
            Image(
                painter = painterResource(id = product.image),
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Text(
                text = product.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                maxLines = 1
            )
        }
    }
}

@Composable
fun CartPage(cartViewModel: ShoppingCartViewModel) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val textColor = Color(0xFFFFB004)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        if (cartItems.isEmpty()) {
            EmptyCartAnimation()

        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp)
            ) {
                items(cartItems) { product ->
                    CartItem(
                        product = product,
                        onRemove = { cartViewModel.removeFromCart(product) }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .background(Color.White)
            ) {
                Button(
                    onClick = { cartViewModel.clearCart() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE0F7FA),
                        contentColor = Color.Black
                    )
                ) {
                    Text(text = "Clear Cart")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { cartViewModel.clearCart() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFB004),
                        contentColor = Color.Black
                    )
                ) {
                    Text(text = "The Payment")
                }
            }
        }
    }
}

@Composable
fun CartItem(
    product: com.example.shop_app_project.Home_page.Main.ProductModel,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFFFFB004))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(data = product.image),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = product.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE0F7FA)
            )
            Text(text = product.description, fontSize = 14.sp, color = Color.Black, maxLines = 1)
            Text(text = "$${product.price}", fontSize = 16.sp, color = Color.Black)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = onRemove,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE0F7FA),
                contentColor = Color.Black
            )
        ) {
            Text(text = "Remove", color = Color.Black)
        }
    }
}

@Composable
fun EmptyCartAnimation() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clip(RoundedCornerShape(0.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.cartcat),
            contentDescription = "Empty Cart",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun showImage() {
    EmptyCartAnimation()
}


@Composable
fun ProfilePage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Profile Page", fontSize = 32.sp)
    }
}


@Composable
fun ProductDetailsPage(
    navController: NavController,
) {

    var isExpanded by remember { mutableStateOf(false) }
    var counter by remember {
        mutableStateOf(1)
    }
    val product = ProductModel(
        name = "Symply Dog Adult Chicken With Rice & Vegetables",
        description = "High-quality dog food with chicken, rice, and vegetables.",
        price = 199,
        image = R.drawable.dog,
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = product.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = product.name, fontSize = 24.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = product.description, fontSize = 16.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "£${product.price}",
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            IconButton(onClick = { if (counter > 1) counter -= 1 }) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Decrease quantity",
                    tint = Color(0xFFD20311)
                )
            }
            Text(
                text = "$counter",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            IconButton(onClick = { counter += 1 }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Increase quantity",
                    tint = Color(0xFF00BF07)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = { /* Handle repeat order logic */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE0B2))
        ) {
            Text(text = "Repeat Order")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { isExpanded = !isExpanded },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F7FA))
        ) {
            Text(
                text = if (isExpanded) "Hide Product Detail" else "Show Product Detail",
                color = Color.Black, fontWeight = FontWeight.Bold
            )
        }

        if (isExpanded) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { /* Handle composition logic */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text(text = "Composition")
            }

            Spacer(modifier = Modifier.height(16.dp))


            Text(
                text = "This is additional product detail information that is displayed when the section is expanded.",
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Handle add to cart logic */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF09BE10))
        ) {
            Text(text = "ADD TO CART", color = Color.White)
        }
    }
}

data class ProductModel(
    val name: String,
    val description: String,
    val price: Int,
    val image: Int,
)
