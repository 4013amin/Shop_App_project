package com.example.shop_app_project.data.view_model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.shop_app_project.data.models.product.PorductModel

class ShoppingCartViewModel : ViewModel() {
    private val _cartItems = mutableStateListOf<PorductModel>()
    val cartItems = mutableStateOf(_cartItems)

    fun addToCart(product: PorductModel) {
        _cartItems.add(product)
        cartItems.value = _cartItems // نیازی به تبدیل به SnapshotStateList نیست
    }

    fun removeFromCart(product: PorductModel) {
        _cartItems.remove(product)
        cartItems.value = _cartItems // نیازی به تبدیل به SnapshotStateList نیست
    }

    fun getCartItems(): List<PorductModel> {
        return _cartItems.toList()
    }

    fun clearCart() {
        _cartItems.clear()
        cartItems.value = _cartItems // نیازی به تبدیل به SnapshotStateList نیست
    }
}
