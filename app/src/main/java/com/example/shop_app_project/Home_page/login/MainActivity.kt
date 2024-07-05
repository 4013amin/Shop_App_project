package com.example.shop_app_project.Home_page.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shop_app_project.ui.theme.Shop_App_projectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Shop_App_projectTheme {
                ScreenRegiste()
            }
        }
    }
}

@Composable
fun nav() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Screen_register") {
        composable("Screen_register") {
            ScreenRegister(navController)
        }
        composable("Screen_login") {
            ScreenLogin(navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenRegiste() {
    nav()
}

