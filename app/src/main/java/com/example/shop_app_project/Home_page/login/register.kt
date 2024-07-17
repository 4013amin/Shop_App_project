package com.example.shop_app_project.Home_page.login

import android.app.Application
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
import com.example.shop_app_project.data.view_model.UserViewModelFactory
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun ScreenRegister(
    navController: NavController,
    userViewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(LocalContext.current.applicationContext as Application)
    )
) {
    val savedCredentials = userViewModel.getSavedCredentials()

    var username by remember { mutableStateOf(savedCredentials.first) }
    var password by remember { mutableStateOf(savedCredentials.second) }
    var phone by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }

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

        TextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone Number") }
        )

        TextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("City") }
        )

        TextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") }
        )

        TextField(
            value = postalCode,
            onValueChange = { postalCode = it },
            label = { Text("Postal Code") }
        )

        Button(onClick = {
            userViewModel.sendRegister(username, password, phone, city, address, postalCode)
            navController.navigate("Screen_login")
        }) {
            Text(text = "Submit")
        }

        LaunchedEffect(userViewModel.registrationResult.value) {
            if (userViewModel.registrationResult.value.isNotBlank()) {
                registrationMessage = userViewModel.registrationResult.value
            }
        }

        if (registrationMessage.isNotBlank()) {
            Text(text = registrationMessage)
        }
    }
}