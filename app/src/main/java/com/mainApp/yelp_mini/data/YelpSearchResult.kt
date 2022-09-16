package com.mainApp.yelp_mini.data

import com.google.gson.annotations.SerializedName

data class YelpSearchResult(
    val total: Int,
    @SerializedName("businesses") val restaurants: List<YelpRestaurant>,
) {

    data class YelpRestaurant(
        val id: String,
        val name: String,
        val rating: Double,
        val price: String,
        @SerializedName("review_count") val numReviews: Int,
        val distance: Double,
        @SerializedName("image_url") val imageUrl: String,
        val categories: List<RestaurantCategory>,
        val location: RestaurantLocation,
    )
}

data class YelpRestaurantDetail(
    val name: String,
    @SerializedName("image_url") val imageUrl: String,
    val url: String,
    @SerializedName("display_phone") val phone: String,
    @SerializedName("review_count") val numReviews: Int,
    val categories: List<RestaurantCategory>,
    val rating: Double,
    val location: RestaurantLocation,
    val photos: List<String>,
    val price: String,
    val transactions: List<String>,
)

data class RestaurantCategory(
    val title: String,
)

data class RestaurantLocation(
    @SerializedName("address1") val address: String,
)

data class YelpRestaurantReviews(
    val reviews: List<RestaurantReview>,
    val total: Int,
)

data class RestaurantReview(
    val id: String,
    val rating: Double,
    val user: UsersFromReviews,
    val text: String,
    @SerializedName("time_created") val timestamp: String,
    val url: String,
)

data class UsersFromReviews(
    val id: String,
    @SerializedName("profile_url") val profileUrl: String,
    @SerializedName("image_url") val imageUrl: String,
    val name: String,
)