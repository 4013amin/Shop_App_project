package com.example.shop_app_project.Home_page.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shop_app_project.R
import com.example.shop_app_project.data.view_model.UserViewModel

@Composable
fun ScreenLogin(navController: NavController, userViewModel: UserViewModel = viewModel()) {

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, userViewModel: UserViewModel) {
    val savedCredentials = userViewModel.getSavedCredentials()
    var username by remember { mutableStateOf(savedCredentials.first) }
    var password by remember { mutableStateOf(savedCredentials.second) }
    val textColor = Color(0xFFFFB004)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE0F7FA))
                .padding(15.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.login),
                contentDescription = "Register",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
            Spacer(modifier = Modifier.height(50.dp))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Color.White)
                    .padding(16.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(10.dp)
                ) {
                    OutlinedTextField(
                        value = username,
                        onValueChange = {
                            username = it
                            userViewModel.saveCredentials(username, password, "", "")
                        },
                        label = { Text(text = "username", color = Color.Black) },
                        modifier = Modifier.padding(10.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "username Icon",
                                tint = Color.Black
                            )
                        },
                        colors = outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Black,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                        ),
                        textStyle = TextStyle(textColor)
                    )


                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            userViewModel.saveCredentials(username, password, "", "")
                        },
                        label = { Text(text = "password", color = Color.Black) },
                        modifier = Modifier.padding(10.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "password Icon",
                                tint = Color.Black
                            )
                        },
                        colors = outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Black,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                        ),
                        textStyle = TextStyle(textColor)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun showLogin() {
    val navController = rememberNavController()
    var userViewModel: UserViewModel = viewModel()
    LoginScreen(navController = navController, userViewModel = userViewModel)
}

