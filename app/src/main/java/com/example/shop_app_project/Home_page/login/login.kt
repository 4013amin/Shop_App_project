package com.example.shop_app_project.Home_page.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shop_app_project.data.view_model.UserViewModel

@Composable
fun ScreenLogin(navController: NavHostController, userViewModel: UserViewModel = viewModel()) {

    val savedCredentials = userViewModel.getSavedCredentials()

    var username by remember { mutableStateOf(savedCredentials.first) }
    var password by remember { mutableStateOf(savedCredentials.second) }
    var show_login_Message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Login", fontSize = 60.sp)

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") }
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Button(onClick = {
            // Handle login logic here
//            userViewModel.sendLogin(username, password)
            navController.navigate("Screen_register")
        }) {
            Text(text = "Login")
        }

        //massage_login_for_ok
        LaunchedEffect(userViewModel.login_result.value) {
            if (userViewModel.login_result.value.isNotBlank()) {
                show_login_Message = userViewModel.login_result.value
            }
        }

        Text(text = show_login_Message)

    }
}
