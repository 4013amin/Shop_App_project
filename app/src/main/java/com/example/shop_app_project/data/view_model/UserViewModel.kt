package com.example.shop_app_project.data.view_model

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.shop_app_project.data.models.product.Category
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
    var category = mutableStateOf<List<Category>>(arrayListOf())


    //chech_for_login
    var isLoggedIn by mutableStateOf(false)

    private val shoppingCartViewModel = ShoppingCartViewModel(application)

    private val sharedPreferences =
        application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun sendRegister(username: String, address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                Utils_ret.api.registerUser(username, address)
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
                editor.putString("password", address)
                editor.apply()

            } else {
                Log.e("UserViewModel", "Registration failed: ${response.errorBody()}")
            }
        }
    }

    fun sendLogin(username: String, address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                val user = login_model(
                    username = username,
                    address = address,
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
                Utils_ret.api.getProducts()
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
                Log.d("UserViewModel", "Products fetched successfully: ${response.body()}")
                products.value = response.body()!!
            } else {
                Log.e(
                    "UserViewModel",
                    "Failed to fetch products: ${response.errorBody()?.string()}"
                )
            }

            getCategories()
        }
    }


    //send Categories request
    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                Utils_ret.api.getCategories()
            } catch (e: IOException) {
                Log.e("UserViewModel", "Network error occurred while fetching products.", e)
                return@launch
            } catch (e: HttpException) {
                Log.e(
                    "UserViewModel",
                    "HTTP error occurred while fetching products: ${e.code()}",
                    e

                )
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                category.value = response.body()!!
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

    // تابع برای اضافه کردن به سبد خرید
    fun addToCart(product: PorductModel) {
        shoppingCartViewModel.addToCart(product)
    }

    // تابع برای حذف از سبد خرید
    fun removeFromCart(product: PorductModel) {
        shoppingCartViewModel.removeFromCart(product)
    }

    // تابع برای دریافت لیست محصولات در سبد خرید
    fun getCartItems(): List<PorductModel> {
        return shoppingCartViewModel.getCartItems()
    }

    // تابع برای پاک کردن سبد خرید
    fun clearCart() {
        shoppingCartViewModel.clearCart()
    }
}



