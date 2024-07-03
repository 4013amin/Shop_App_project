package com.example.shop_app_project.data.api

import com.example.shop_app_project.data.models.register.User
import com.example.shop_app_project.data.models.register.login_model
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface login {

    @POST("register_user")
    suspend fun registerUser(@Body user: login_model): Response<login_model>


    @POST("login_user")
    suspend fun loginUser(@Body user: login_model): Response<login_model>

}