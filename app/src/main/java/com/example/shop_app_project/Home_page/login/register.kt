import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shop_app_project.data.view_model.UserViewModel
import com.example.shop_app_project.data.view_model.UserViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.airbnb.lottie.compose.*
import com.example.shop_app_project.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    userViewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(LocalContext.current.applicationContext as Application)
    ),
) {

    val savedCredentials = userViewModel.getSavedCredentials()

    var username by remember { mutableStateOf(savedCredentials.first) }
    var password by remember { mutableStateOf(savedCredentials.second) }
    var phone by remember { mutableStateOf(savedCredentials.third) }
    var location by remember { mutableStateOf(savedCredentials.fourth) }

    val textColor = Color(0xFFFFB004)

    var registrationMessage by remember { mutableStateOf("") }
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    var isLoggedIn by remember { mutableStateOf(userViewModel.checkCredentials()) }

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate("home")
        }
    }


    // location
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true || permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
            getLastLocation(fusedLocationClient) { loc ->
                location = loc
            }
        } else {
            registrationMessage = "Location permission denied."
        }
    }

    LaunchedEffect(Unit) {
        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                getLastLocation(fusedLocationClient) { loc ->
                    location = loc
                }
            }

            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                getLastLocation(fusedLocationClient) { loc ->
                    location = loc
                }
            }

            else -> {
                locationPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }

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
                painter = painterResource(id = R.drawable.register),
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
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier.padding(10.dp)
                ) {
                    OutlinedTextField(
                        value = username,
                        onValueChange = {
                            username = it
                            userViewModel.saveCredentials(username, password, phone, location)
                        },
                        label = { Text(text = "Username", color = Color.Black) },
                        modifier = Modifier.padding(10.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Username Icon",
                                tint = Color.Black
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
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
                            userViewModel.saveCredentials(username, password, phone, location)
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
                        label = { Text(text = "Password", color = Color.Black) },
                        modifier = Modifier.padding(10.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Password Icon",
                                tint = Color.Black
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Black,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                        ),
                        textStyle = TextStyle(textColor)

                    )

                    OutlinedTextField(
                        value = phone,
                        onValueChange = {
                            phone = it
                            userViewModel.saveCredentials(username, password, phone, location)
                        },
                        label = { Text(text = "Phone", color = Color.Black) },
                        modifier = Modifier.padding(10.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = "Phone Icon",
                                tint = Color.Black
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Black,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                        ),
                        textStyle = LocalTextStyle.current.copy(color = Color.Black)
                    )

//                    OutlinedTextField(
//                        value = location,
//                        onValueChange = {
//                            location = it
//                            userViewModel.saveCredentials(username, password, phone, location)
//                        },
//                        label = { Text(text = "Location", color = Color.Black) },
//                        modifier = Modifier.padding(10.dp),
//                        leadingIcon = {
//                            Icon(
//                                imageVector = Icons.Default.LocationOn,
//                                contentDescription = "Location Icon",
//                                tint = Color.Black
//                            )
//                        },
//                        colors = TextFieldDefaults.outlinedTextFieldColors(
//                            focusedBorderColor = MaterialTheme.colorScheme.primary,
//                            unfocusedBorderColor = Color.Black,
//                            cursorColor = MaterialTheme.colorScheme.primary,
//                            focusedLabelColor = MaterialTheme.colorScheme.primary,
//                        ),
//                        textStyle = TextStyle(textColor)
//
//                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { navController.navigate("home") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Color(0xFFE0F7FA)),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color(0xFFE0F7FA),
                                contentColor = Color.Black
                            )
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Register Icon",
                                    tint = Color.Black
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Register", color = Color.Black)
                            }
                        }

                        Button(
                            onClick = { navController.navigate("ScreenLogin") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Color(0xFFFFB004)),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color(0xFFFFB004),
                                contentColor = Color.Black
                            )
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Login Icon",
                                    tint = Color.Black
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Login", color = Color.Black)
                            }
                        }

                    }
                }
            }
        }
    }
    LaunchedEffect(userViewModel.registrationResult.value) {
        if (userViewModel.registrationResult.value.isNotBlank()) {
            registrationMessage = userViewModel.registrationResult.value
        }
    }

    if (registrationMessage.isNotBlank()) {
        Text(text = registrationMessage, modifier = Modifier.padding(16.dp))
    }
}

@SuppressLint("MissingPermission")
fun getLastLocation(
    fusedLocationClient: FusedLocationProviderClient,
    onLocationReceived: (String) -> Unit
) {
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        location?.let {
            val loc = "Lat: ${it.latitude}, Lon: ${it.longitude}"
            onLocationReceived(loc)
        } ?: run {
            onLocationReceived("Turn on your locatio")
        }
    }.addOnFailureListener {
        onLocationReceived("Turn on your location")
    }
}
