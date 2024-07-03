package com.example.shop_app_project.data.api

import com.example.shop_app_project.data.models.product.PorductModel
import com.example.shop_app_project.data.models.register.login_model
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface login {
    @FormUrlEncoded
    @POST("register_user")
    suspend fun registerUser(@Body user: login_model): Response<login_model>

    @FormUrlEncoded
    @POST("login_user")
    suspend fun loginUser(@Body user: login_model): Response<login_model>


    @GET("/api/products/")
    suspend fun get_products(): Response<PorductModel>


}