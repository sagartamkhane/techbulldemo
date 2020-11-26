package com.android.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class Movie (

	@SerializedName("Search") val search : List<Search>,
	@SerializedName("totalResults") val totalResults : Int,
	@SerializedName("Response") val response : Boolean
)