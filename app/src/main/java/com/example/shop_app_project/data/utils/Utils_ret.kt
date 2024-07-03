package com.example.shop_app_project.data.utils

import com.example.shop_app_project.data.api.login
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Utils_ret {

    val api: login by lazy {
        Retrofit.Builder()
            .baseUrl(Utils.url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(login::class.java)

    }


}