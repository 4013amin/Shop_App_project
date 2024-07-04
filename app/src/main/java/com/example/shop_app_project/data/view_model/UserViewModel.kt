package com.example.shop_app_project.data.view_model

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.AndroidViewModel
import com.example.shop_app_project.data.models.product.PorductModel
import com.example.shop_app_project.data.models.register.login_model
import com.example.shop_app_project.data.utils.Utils_ret
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class UserViewModel(application: Application) : AndroidViewModel(application) {
    var registrationResult = mutableStateOf("")
    var login_result = mutableStateOf("")
    var products = mutableStateOf<List<PorductModel>>(arrayListOf())

    private val sharedPreferences =
        application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

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
                Log.e("UserViewModel", "Network error occurred during registration.", e)
                registrationResult.value = "Network error occurred."
                return@launch
            } catch (e: HttpException) {
                Log.e("UserViewModel", "HTTP error occurred during registration: ${e.code()}", e)
                registrationResult.value = "HTTP error occurred: ${e.code()}"
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                Log.d("UserViewModel", "Registration successful.")
                registrationResult.value = "Registration successful."

                //shared
                var editor = sharedPreferences.edit()
                editor.putString("username", username)
                editor.putString("password", password)
                editor.apply()

            } else {
                Log.e("UserViewModel", "Registration failed: ${response.errorBody()}")
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
                Log.e("UserViewModel", "Network error occurred during login.", e)
                login_result.value = "Network error occurred."
                return@launch
            } catch (e: HttpException) {
                Log.e("UserViewModel", "HTTP error occurred during login: ${e.code()}", e)
                login_result.value = "HTTP error occurred: ${e.code()}"
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                Log.d("UserViewModel", "Login successful.")
                registrationResult.value = "Login successful."

            } else {
                Log.e("UserViewModel", "Login failed: ${response.errorBody()}")
            }
        }
    }

    fun getAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                Utils_ret.api.get_products()
            } catch (e: IOException) {
                Log.e("UserViewModel", "Network error occurred while fetching products.", e)
                registrationResult.value = "Network error occurred."
                return@launch
            } catch (e: HttpException) {
                Log.e(
                    "UserViewModel",
                    "HTTP error occurred while fetching products: ${e.code()}",
                    e
                )
                registrationResult.value = "HTTP error occurred: ${e.code()}"
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                Log.d("UserViewModel", "Products fetched successfully.")
                products.value = response.body()!!

            } else {
                Log.e("UserViewModel", "Failed to fetch products: ${response.errorBody()}")
            }
        }
    }

    fun saveCredentials(username: String, password: String) {
        with(sharedPreferences.edit()) {
            putString("username", username)
            putString("password", password)
            apply()
        }
    }

    fun getSavedCredentials(): Pair<String, String> {
        val username = sharedPreferences.getString("username", "") ?: ""
        val password = sharedPreferences.getString("password", "") ?: ""
        return Pair(username, password)
    }
}

