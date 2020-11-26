package com.android.myapplication.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getMovie(s: String, apiKey: String) = apiService.getMovie(s,apiKey)
}