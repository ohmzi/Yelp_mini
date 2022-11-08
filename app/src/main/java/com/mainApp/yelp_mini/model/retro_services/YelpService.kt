package com.mainApp.yelp_mini.model.retro_services

import com.mainApp.yelp_mini.model.data.YelpRestaurantDetail
import com.mainApp.yelp_mini.model.data.YelpRestaurantReviews
import com.mainApp.yelp_mini.model.data.YelpSearchResult

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface YelpService {
    @GET("businesses/search")
    fun getRestaurantsResults(
        @Header("Authorization") authHeader: String,
        @Query("term") searchTerm: String,
        @Query("categories") categories: String,
        @Query("location") location: String,
    ): Call<YelpSearchResult>

    @GET("businesses/{id}")
    fun getRestaurantsDetails(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String,
    ): Call<YelpRestaurantDetail>

    @GET("businesses/{id}/reviews")
    fun getRestaurantsReviews(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String,
    ): Call<YelpRestaurantReviews>
}
