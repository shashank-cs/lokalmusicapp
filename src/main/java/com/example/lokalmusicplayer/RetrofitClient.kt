
package com.example.lokalmusicplayer

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search/songs")
    suspend fun searchSongs(
        @Query("query") query: String,
        @Query("limit") limit: Int = 20
    ): ApiResponse
}

object RetrofitClient {

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://saavn.sumit.co/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
