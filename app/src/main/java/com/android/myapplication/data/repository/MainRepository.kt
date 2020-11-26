package com.android.myapplication.data.repository

import com.android.myapplication.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getMovie(s: String, apiKey: String) = apiHelper.getMovie(s,apiKey)
}