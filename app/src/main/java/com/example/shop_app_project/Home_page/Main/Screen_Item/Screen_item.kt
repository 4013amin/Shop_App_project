package com.example.shop_app_project.Home_page.Main.Screen_Item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shop_app_project.data.models.product.PorductModel
import com.example.shop_app_project.data.view_model.ShoppingCartViewModel

@Composable
fun SearchPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Search Page", fontSize = 32.sp)
    }
}

@Composable
fun CartPage(cartViewModel: ShoppingCartViewModel = viewModel()) {
    val cartItems = cartViewModel.cartItems.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Cart Page", fontSize = 32.sp)

        // نمایش لیست محصولات در سبد خرید
        for (item in cartItems) {
            CartItem(product = item)
        }
    }
}

@Composable
fun CartItem(product: PorductModel) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
    ) {
        Text(text = product.name, fontSize = 20.sp)
        Text(text = product.description, fontSize = 14.sp, color = Color.Gray)
        Text(text = "$${product.price}", fontSize = 16.sp, color = Color.Black)
        // به اینجا می‌توانید تصویر محصول را نیز اضافه کنید اگر دارید
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
