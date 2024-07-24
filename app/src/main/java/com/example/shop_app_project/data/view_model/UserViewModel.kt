package com.example.shop_app_project.data.view_model

import android.app.Application
import android.content.Context
import android.util.Log
import android.util.Pair
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop_app_project.data.models.Profile.Profile
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
    var profile = mutableStateOf<List<Profile>>(arrayListOf())

    var isLoggedIn by mutableStateOf(false)

    private val shoppingCartViewModel = ShoppingCartViewModel(application)

    private val sharedPreferences =
        application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

<<<<<<< HEAD
    fun sendRegister(
        username: String,
        password: String,
        phone: String,
        city: String,
        address: String,
        postalCode: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                Utils_ret.api.registerUser(username, password, phone, city, address, postalCode)
=======
    fun sendRegister(username: String, password: String, phone: String, location: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                Utils_ret.api.registerUser(username, password, phone, location)
>>>>>>> UiMainPage
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
                editor.putString("phone", phone)
<<<<<<< HEAD
                editor.putString("city", city)
                editor.putString("address", address)
                editor.putString("postalCode", postalCode)
=======
                editor.putString("location", location)
>>>>>>> UiMainPage
                editor.apply()

            } else {
                Log.e("UserViewModel", "Registration failed: ${response.errorBody()}")
            }
        }
    }

<<<<<<< HEAD
//    fun sendLogin(username: String, address: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val response = try {
//                val user = login_model(
//                    username = username,
//                    address = address,
//                )
//                Utils_ret.api.loginUser(user)
//            } catch (e: IOException) {
//                Log.e("UserViewModel", "Network error occurred during login.", e)
//                login_result.value = "Network error occurred."
//                return@launch
//            } catch (e: HttpException) {
//                Log.e("UserViewModel", "HTTP error occurred during login: ${e.code()}", e)
//                login_result.value = "HTTP error occurred: ${e.code()}"
//                return@launch
//            }
//
//            if (response.isSuccessful && response.body() != null) {
//                Log.d("UserViewModel", "Login successful.")
//                registrationResult.value = "Login successful."
//
//            } else {
//                Log.e("UserViewModel", "Login failed: ${response.errorBody()}")
//            }
//        }
//    }
=======
    //checkLogin
    fun checkCredentials(): Boolean {
        val savedCredentials = getSavedCredentials()

        return savedCredentials.first.isNotBlank() && savedCredentials.second.isNotBlank()
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
>>>>>>> UiMainPage

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

    //Profile
    fun getProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                Utils_ret.api.getProfile()
            } catch (e: IOException) {
                Log.e("getProfileErrorIO", e.toString())
                return@launch
            } catch (e: HttpException) {
                Log.e("getProfileErrorHTTP", e.toString())
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                profile.value = response.body()!!
            }
        }
    }


    fun saveCredentials(username: String, password: String, phone: String, location: String) {
        with(sharedPreferences.edit()) {
            putString("username", username)
            putString("password", password)
            putString("phone", phone)
            putString("location", location)
            apply()
        }
    }


    fun getSavedCredentials(): Quadruple<String, String, String, String> {
        val username = sharedPreferences.getString("username", "") ?: ""
        val password = sharedPreferences.getString("password", "") ?: ""
        val phone = sharedPreferences.getString("phone", "") ?: ""
        val location = sharedPreferences.getString("location", "") ?: ""
        return Quadruple(username, password, phone, location)
    }

<<<<<<< HEAD

    fun addToCart(product: PorductModel) {
        shoppingCartViewModel.addToCart(product)
    }


    fun removeFromCart(product: PorductModel) {
        shoppingCartViewModel.removeFromCart(product)
    }


    fun getCartItems(): List<PorductModel> {
=======
    data class Quadruple<out A, out B, out C, out D>(
        val first: A,
        val second: B,
        val third: C,
        val fourth: D
    )

//    // تابع برای اضافه کردن به سبد خرید
//    fun addToCart(product: PorductModel) {
//        shoppingCartViewModel.addToCart(product)
//    }

    // تابع برای حذف از سبد خرید
    fun removeFromCart(product: com.example.shop_app_project.Home_page.Main.ProductModel) {
        shoppingCartViewModel.removeFromCart(product)
    }

    // تابع برای دریافت لیست محصولات در سبد خرید
    fun getCartItems(): List<com.example.shop_app_project.Home_page.Main.ProductModel> {
>>>>>>> UiMainPage
        return shoppingCartViewModel.getCartItems()
    }

    fun clearCart() {
        shoppingCartViewModel.clearCart()
    }
}




