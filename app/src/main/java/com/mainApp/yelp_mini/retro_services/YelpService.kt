package com.mainApp.yelp_mini.retro_services

import com.mainApp.yelp_mini.data.YelpBusinessDetail
import com.mainApp.yelp_mini.data.YelpReviews
import com.mainApp.yelp_mini.data.YelpSearchResult

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface YelpService {
    @GET("businesses/search")
    fun searchRestaurants(
        @Header("Authorization") authHeader: String,
        @Query("term") searchTerm: String,
        @Query("categories") categories: String,
        @Query("location") location: String,
    ): Call<YelpSearchResult>

    @GET("businesses/{id}")
    fun getDetails(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String,
    ): Call<YelpBusinessDetail>

    @GET("businesses/{id}/reviews")
    fun getReviews(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String,
    ): Call<YelpReviews>
}
