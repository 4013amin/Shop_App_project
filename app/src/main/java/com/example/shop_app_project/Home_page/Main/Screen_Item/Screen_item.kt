package com.example.shop_app_project.Home_page.Main.Screen_Item

import com.example.shop_app_project.data.models.product.PorductModel
import com.example.shop_app_project.data.view_model.ShoppingCartViewModel
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.shop_app_project.Home_page.Main.ProductItem
import com.example.shop_app_project.R
import com.example.shop_app_project.data.view_model.UserViewModel
import com.google.gson.Gson

var gson = Gson()
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPage(
    userViewModel: UserViewModel = viewModel(),
    shoppingCartViewModel: ShoppingCartViewModel = viewModel(),
    navController: NavController,
) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(Unit) {
        userViewModel.getAllProducts()
    }

    val products by userViewModel.products

    //search_filter
    var search_filter = products.filter { product ->
        product.name.contains(searchText.text, ignoreCase = true)
    }

    Log.d("SearchPage", "Number of products: ${products.size}")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp)),
            placeholder = {
                Text(
                    "Search here...",
                    style = MaterialTheme.typography.labelMedium.copy(color = Color.Black)
                )
            },
            colors = TextFieldDefaults.textFieldColors(Color.Black),
            textStyle = MaterialTheme.typography.bodyLarge
        )

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            items(search_filter) { product ->
                ProductItem(
                    name = product.name,
                    description = product.description,
                    price = product.price,
                    image = product.image,
                    addToCart = {
                        shoppingCartViewModel.addToCart(product)
                    },
                    onClick = {
                        val productGson = gson.toJson(product)
                        navController.navigate("single_product?product=${productGson}")
                    }
                )

            }
        }
    }
}


@Composable
fun CartPage(cartViewModel: ShoppingCartViewModel = viewModel()) {
    val cartItems by cartViewModel.cartItems.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Cart Page", fontSize = 32.sp)

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(cartItems) { product ->
                CartItem(product = product, onRemove = { cartViewModel.removeFromCart(product) })
            }
        }

        Button(
            onClick = { cartViewModel.clearCart() },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Clear Cart")
        }
    }
}

@Composable
fun CartItem(product: PorductModel, onRemove: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(data = product.image),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(Color.Gray),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .size(105.dp)
        ) {
            Text(text = product.name, fontSize = 20.sp)
            Text(text = product.description, fontSize = 14.sp, color = Color.Gray, maxLines = 1)
            Text(text = "$${product.price}", fontSize = 16.sp, color = Color.Black)
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = onRemove) {
            Text(text = "Remove")
        }
    }
}

@Composable
fun ProfilePage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(android.graphics.Color.parseColor("#f2f1f6"))),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConstraintLayout(
            modifier = Modifier
                .height(250.dp)
                .background(color = Color(android.graphics.Color.parseColor("#32357a")))
        ) {
            val (topImage, profileImage, title, backButton, editButton) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.arc_3),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .constrainAs(topImage) {
                        bottom.linkTo(parent.bottom)
                    }
            )

            Image(
                painter = painterResource(id = R.drawable.user_1),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .constrainAs(profileImage) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Text(
                text = "Profile Name",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(profileImage.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            IconButton(
                onClick = { navController.navigate("home") },
                modifier = Modifier
                    .constrainAs(backButton) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = null,
                    tint = Color.White
                )
            }

            IconButton(
                onClick = { /* TODO: handle edit button click */ },
                modifier = Modifier
                    .constrainAs(editButton) {
                        top.linkTo(parent.top, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.write),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "About Me",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Text(
                text = "This is a sample description about the user. It can contain information like interests, hobbies, etc.",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Button(
                onClick = { /* TODO: handle button click */ },
                colors = ButtonDefaults.buttonColors(Color(android.graphics.Color.parseColor("#32357a")))
            ) {
                Text(
                    text = "Edit Profile",
                    color = Color.White
                )
            }
        }
    }
}


@Composable
fun ProductDetailsPage(
    navController: NavController,
    productJson: String,
    userViewModel: UserViewModel,
    cartViewModel: ShoppingCartViewModel,
) {
    val gson = remember { Gson() }
    val product = gson.fromJson(productJson, PorductModel::class.java)

    product?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(it.image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = it.name, fontSize = 24.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it.description, fontSize = 16.sp, color = Color.Gray)
            Text(text = it.created_at, fontSize = 16.sp, color = Color.Gray)
            Text(text = "\$${it.stock}", fontSize = 20.sp, color = Color.Black)
            Text(text = "\$${it.category}", fontSize = 20.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "\$${it.price}", fontSize = 20.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { cartViewModel.addToCart(it) }) {
                Text(text = "Add to Cart")
            }
        }
    } ?: run {
        Text(text = "Product not found", fontSize = 16.sp, color = Color.Red)
    }
}
