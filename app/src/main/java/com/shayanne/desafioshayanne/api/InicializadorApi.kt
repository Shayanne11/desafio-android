package com.shayanne.desafioshayanne.api

import androidx.annotation.VisibleForTesting
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object InicializadorApi {

    var okHttpClient = OkHttpClient.Builder().build()

    @VisibleForTesting
    var baseUrl = "https://api.github.com/"

    val webClientGithub by lazy { Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiWebClientRequest::class.java)
    }
}
