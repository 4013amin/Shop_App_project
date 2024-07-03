package com.example.shop_app_project.Home_page

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shop_app_project.ui.theme.Shop_App_projectTheme
import com.example.shop_app_project.view_model.UserViewModel

class Main_page : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Shop_App_projectTheme {
                Ui_Home_page()
            }
        }
    }
}

@Composable
fun Ui_Home_page(userViewModel: UserViewModel = viewModel()) {
    userViewModel.getAllProducts()
    val products by userViewModel.products

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Products", fontSize = 32.sp, modifier = Modifier.padding(bottom = 16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(products) { product ->
                ProductItem(
                    body = product.body,
                    title = product.title
                )
            }
        }
    }
}

@Composable
fun ProductItem(body: String, title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        Text(text = title, fontSize = 24.sp, color = Color.Black)
        Text(text = body, fontSize = 16.sp, color = Color.Gray)
    }
}

@Preview(showBackground = true)
@Composable
fun show_data() {
    Shop_App_projectTheme {
        Ui_Home_page()
    }
}
