package com.android.myapplication.data.api


import com.android.myapplication.data.model.Movie
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/")
    suspend fun getMovie(@Query("s") s: String,
                         @Query("apikey") apikey: String): Movie

}