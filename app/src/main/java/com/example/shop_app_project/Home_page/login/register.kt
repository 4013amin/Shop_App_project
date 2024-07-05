package com.example.shop_app_project.Home_page.login

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shop_app_project.data.view_model.UserViewModel
import com.example.shop_app_project.data.view_model.UserViewModelFactory
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.shop_app_project.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenRegister(
    navController: NavHostController,
    userViewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(LocalContext.current.applicationContext as Application)
    )
) {
    val savedCredentials = userViewModel.getSavedCredentials()

    var username by remember { mutableStateOf(savedCredentials.first) }
    var password by remember { mutableStateOf(savedCredentials.second) }

    var registrationMessage by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color(android.graphics.Color.parseColor("#ffffff")))
        , horizontalAlignment = Alignment.CenterHorizontally)
    {
        Image(painterResource(id = R.drawable.top_background),
            contentDescription =null,
            contentScale = ContentScale.FillWidth)

        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.height(170.dp))




        var user by remember { mutableStateOf("userName") }

        TextField(value = user , {text->user = text} ,
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
                .padding(start = 66.dp, end = 66.dp, top = 8.dp, bottom = 8.dp)
                .border(
                    1.dp, Color(android.graphics.Color.parseColor("#7d32a8")),
                    shape = RoundedCornerShape(50.dp)
                ),

            shape = RoundedCornerShape(50.dp),
            textStyle = TextStyle(textAlign = TextAlign.Center,
                color = Color(android.graphics.Color.parseColor("#7d32a8")),
                fontSize = 14.sp),
            colors = TextFieldDefaults.textFieldColors(
                Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent)


        )

        var pass by remember { mutableStateOf("password") }

        TextField(value = pass, {text->pass=text},
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
                .padding(start = 66.dp, end = 66.dp, top = 8.dp, bottom = 8.dp)
                .border(
                    1.dp, Color(android.graphics.Color.parseColor("#7d32a8")),
                    shape = RoundedCornerShape(50.dp)
                ),

            shape = RoundedCornerShape(50.dp),
            textStyle = TextStyle(textAlign = TextAlign.Center,
                color = Color(android.graphics.Color.parseColor("#7d32a8")),
                fontSize = 14.sp),
            colors = TextFieldDefaults.textFieldColors(
                Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent))

        Button(onClick = { /*TODO*/ }
            ,  modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
                .padding(start = 66.dp, end = 66.dp, top = 8.dp, bottom = 8.dp)
        )
        {

           // Text(text = "Login" , )
        }


     Button(onClick = {
            userViewModel.saveCredentials(username, password)
            navController.navigate("Screen_login")
//            userViewModel.sendRegister(username, password)
        }) {
          //  Text(text = "Submit")
        }

        LaunchedEffect(userViewModel.registrationResult.value) {
            if (userViewModel.registrationResult.value.isNotBlank()) {
                registrationMessage = userViewModel.registrationResult.value
            }
        }

        if (registrationMessage.isNotBlank()) {
           // Text(text = registrationMessage)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun showrgester () {
    ScreenRegister(navController = NavHostController(context = LocalContext.current))
}