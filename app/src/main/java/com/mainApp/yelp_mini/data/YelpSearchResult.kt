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
        val categories: List<YelpCategory>,
        val location: YelpLocation,
    )
}

data class YelpBusinessDetail(
    val name: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("is_claimed") val isClaimed: String,
    @SerializedName("is_closed") val isClosed: String,
    val url: String,
    @SerializedName("display_phone") val phone: String,
    @SerializedName("review_count") val numReviews: Int,
    val categories: List<YelpCategory>,
    val rating: Double,
    val location: YelpLocation,
    val photos: List<String>,
    val price: String,
    // hours
    val transactions: List<String>,
)

data class YelpCategory(
    val title: String,
)

data class YelpLocation(
    @SerializedName("address1") val address: String,
)
