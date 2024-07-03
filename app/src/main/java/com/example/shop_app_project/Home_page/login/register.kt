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
fun ScreenRegister(navController: NavHostController, userViewModel: UserViewModel = viewModel()) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // State to hold the registration message
    var registrationMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Register", fontSize = 60.sp)

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
            userViewModel.sendRegister(username, password)
            navController.navigate("Screen_login")
        }) {
            Text(text = "Submit")
        }

        // Display registration result message
        LaunchedEffect(userViewModel.registrationResult.value) {
            if (userViewModel.registrationResult.value.isNotBlank()) {
                registrationMessage = userViewModel.registrationResult.value
            }
        }

        // Display registration message
        if (registrationMessage.isNotBlank()) {
            Text(text = registrationMessage)
        }
    }
}
