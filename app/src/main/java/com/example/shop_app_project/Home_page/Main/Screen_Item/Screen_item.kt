package com.example.shop_app_project.Home_page.Main.Screen_Item

import android.annotation.SuppressLint
import com.example.shop_app_project.data.view_model.ShoppingCartViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.shop_app_project.R
import com.google.gson.Gson
import androidx.compose.foundation.pager.rememberPagerState


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
    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFFFFF))
                    .padding(16.dp)
            ) {
                // SearchBar placed at the top
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                // Content below the SearchBar
//                CategoryFilter()
                Spacer(modifier = Modifier.height(16.dp))
                ProductGrid(shoppingCartViewModel, navController)
            }
        }
    )
}

@ExperimentalMaterial3Api
@Composable
fun SearchBar(modifier: Modifier) {
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
fun ProductGrid(
    cartViewModel: ShoppingCartViewModel,
    navController: NavController
) {
    val products = listOf(
        ProductModel(
            "Dog Food",
            "wdadwd",
            2500,
            R.drawable.tools,
            listOf(R.drawable.dog, R.drawable.dog, R.drawable.dog)
        ),
        ProductModel(
            "Dog Food",
            "wdadwd",
            2500,
            R.drawable.tools,
            listOf(R.drawable.dog, R.drawable.dog, R.drawable.dog)
        ),
        ProductModel(
            "Dog Food",
            "wdadwd",
            2500,
            R.drawable.tools,
            listOf(R.drawable.dog, R.drawable.dog, R.drawable.dog)
        ),
        ProductModel(
            "Dog Food",
            "wdadwd",
            2500,
            R.drawable.tools,
            listOf(R.drawable.dog, R.drawable.dog, R.drawable.dog)
        ),
        ProductModel(
            "Dog Food",
            "wdadwd",
            2500,
            R.drawable.tools,
            listOf(R.drawable.dog, R.drawable.dog, R.drawable.dog)
        ),
        ProductModel(
            "Dog Food",
            "wdadwd",
            2500,
            R.drawable.tools,
            listOf(R.drawable.dog, R.drawable.dog, R.drawable.dog)
        ),
        ProductModel(
            "Dog Food",
            "wdadwd",
            2500,
            R.drawable.tools,
            listOf(R.drawable.dog, R.drawable.dog, R.drawable.dog)
        ),
        ProductModel(
            "Dog Food",
            "wdadwd",
            2500,
            R.drawable.tools,
            listOf(R.drawable.dog, R.drawable.dog, R.drawable.dog)
        ),
    )

    // Create a grid layout with 2 columns
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            ProductItemSearchPage(
                name = product.name,
                description = product.description,
                price = product.price,
                image = product.image,
                addToCart = {
                    // Add to cart functionality
                },
                onClick = {
                    navController.navigate("singlePage")
                }
            )
        }
    }
}


@Composable
fun ProductItemSearchPage(
    name: String,
    description: String,
    price: Int,
    image: Int,
    addToCart: () -> Unit,
    onClick: () -> Unit
) {
    var isSelected by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .width(300.dp)
            .height(260.dp)
            .padding(8.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFFFA500), RoundedCornerShape(8.dp)) // Orange border
            .clickable(onClick = onClick)
    ) {

        Box {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            // Discount banner
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(Color(0xFFFFA500), RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Discount : ${price}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
                .fillMaxWidth()
        ) {

            Text(
                text = name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFCC9C99)
            )

            Text(
                text = description,
                fontSize = 12.sp,
                color = Color(0xFFCC9C99),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "$$price",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF388E3C)
                )

                IconButton(
                    onClick = addToCart,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add to cart",
                        tint = Color(0xFFFFA500)
                    )
                }
            }
        }
    }
}


//cardPage
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
fun ProductDetailsPage(navController: NavController) {
    var isExpanded by remember { mutableStateOf(false) }
    var counter by remember { mutableStateOf(1) }
    val product = ProductModel(
        name = "Symply Dog Adult Chicken With Rice & Vegetables",
        description = "High-quality dog food with chicken, rice, and vegetables.",
        price = 199,
        image = R.drawable.dog,
        additionalImages = listOf(
            R.drawable.dog,
            R.drawable.dog,
            R.drawable.dog,
            R.drawable.dog,
            R.drawable.dog
        ) // Example images
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Image Slider
        val pagerState = rememberPagerState(pageCount = { product.additionalImages.size })

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) { page ->
            Image(
                painter = painterResource(id = product.additionalImages[page]),
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Dot Pager Indicator
        DotPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            activeColor = Color.Black,
            inactiveColor = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Product details
        Text(
            text = product.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = product.description,
            fontSize = 16.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "£${product.price}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Quantity counter
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

        // Repeat Order Button
        Button(
            onClick = { /* Handle repeat order logic */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE0B2))
        ) {
            Text(text = "Repeat Order")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Show/Hide Product Detail Button
        Button(
            onClick = { isExpanded = !isExpanded },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F7FA))
        ) {
            Text(
                text = if (isExpanded) "Hide Product Detail" else "Show Product Detail",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }

        if (isExpanded) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { /* Handle composition logic */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text(text = "Composition", color = Color.White)
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

@Composable
fun DotPagerIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    activeColor: Color,
    inactiveColor: Color
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { index ->
            val color = if (pagerState.currentPage == index) activeColor else inactiveColor
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(color, shape = RoundedCornerShape(50))
                    .padding(2.dp)
            )
        }
    }
}

data class ProductModel(
    val name: String,
    val description: String,
    val price: Int,
    val image: Int,
    val additionalImages: List<Int>,
)

