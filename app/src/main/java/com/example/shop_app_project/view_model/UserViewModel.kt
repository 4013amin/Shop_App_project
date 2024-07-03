package com.example.shop_app_project.view_model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop_app_project.data.models.register.login_model
import com.example.shop_app_project.data.utils.Utils_ret
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class UserViewModel : ViewModel() {
    // Mutable state to hold the result message
    var registrationResult = mutableStateOf("")

    fun sendRegister(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                val user = login_model(
                    username = username,
                    password = password,
                    address = "address",
                    city = "city",
                    country = "country",
                    phone = "phone",
                    postal_code = "postal_code"
                )
                Utils_ret.api.registerUser(user)
            } catch (e: IOException) {
                registrationResult.value = "Network error occurred."
                return@launch
            } catch (e: HttpException) {
                registrationResult.value = "HTTP error occurred: ${e.code()}"
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                registrationResult.value = "Registration successful."
            }
        }
    }

    fun sendLogin(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                val user = login_model(
                    username = username,
                    password = password,
                    address = "",
                    city = "",
                    country = "",
                    phone = "",
                    postal_code = ""
                )
                Utils_ret.api.loginUser(user)
            } catch (e: IOException) {
                registrationResult.value = "Network error occurred."
                return@launch
            } catch (e: HttpException) {
                registrationResult.value = "HTTP error occurred: ${e.code()}"
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                registrationResult.value = "Login successful."
            }
        }
    }
}

