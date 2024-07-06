package com.example.shop_app_project.Home_page.Main.Screen_Item

import com.example.shop_app_project.data.models.product.PorductModel
import com.example.shop_app_project.data.view_model.ShoppingCartViewModel
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.shop_app_project.Home_page.Main.ProductItem
import com.example.shop_app_project.data.view_model.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPage(
    userViewModel: UserViewModel = viewModel(),
    shoppingCartViewModel: ShoppingCartViewModel = viewModel()
) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    // Trigger fetching of products when the composable is first created
    LaunchedEffect(Unit) {
        userViewModel.getAllProducts()
    }

    val products by userViewModel.products

    // Logging the size of the products list
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

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(products.filter {
                it.name.contains(searchText.text, ignoreCase = true)
            }) { product ->
                ProductItem(
                    name = product.name,
                    description = product.description,
                    price = product.price,
                    image = product.image,
                    addToCart = {
                        shoppingCartViewModel.addToCart(product)
                    }
                )
                Divider(color = Color.Gray, thickness = 1.dp)
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

        Column {
            Text(text = product.name, fontSize = 20.sp)
            Text(text = product.description, fontSize = 14.sp, color = Color.Gray)
            Text(text = "$${product.price}", fontSize = 16.sp, color = Color.Black)
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = onRemove) {
            Text(text = "Remove")
        }
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
