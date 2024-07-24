package com.example.shop_app_project.data.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop_app_project.Home_page.Main.Screen_Item.ProductModel
import com.example.shop_app_project.Home_page.Main.SharedPreferencesManager.SharedPreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ProductModel(
    val name: String,
    val description: String,
    val price: Int,
    val image: Int,
    val updated_at: String,
)

class ShoppingCartViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    private val _cartItems = MutableStateFlow<List<com.example.shop_app_project.Home_page.Main.ProductModel>>(emptyList())
    val cartItems: StateFlow<List<com.example.shop_app_project.Home_page.Main.ProductModel>> get() = _cartItems

    init {
        viewModelScope.launch {
            _cartItems.value = SharedPreferencesManager.loadCartItems(context)
        }
    }

    fun addToCart(product: com.example.shop_app_project.Home_page.Main.ProductModel) {
        viewModelScope.launch {
            val updatedCartItems = _cartItems.value.toMutableList().apply { add(product) }
            _cartItems.value = updatedCartItems
            SharedPreferencesManager.saveCartItems(context, updatedCartItems)
        }
    }

    fun removeFromCart(product: com.example.shop_app_project.Home_page.Main.ProductModel) {
        viewModelScope.launch {
            val updatedCartItems = _cartItems.value.toMutableList().apply { remove(product) }
            _cartItems.value = updatedCartItems
            SharedPreferencesManager.saveCartItems(context, updatedCartItems)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            _cartItems.value = emptyList()
            SharedPreferencesManager.clearCartItems(context)
        }
    }

    fun getCartItems(): List<com.example.shop_app_project.Home_page.Main.ProductModel> {
        return _cartItems.value
    }
}