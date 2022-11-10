package com.mainApp.yelp_mini.model.repo

class RepoClass {
    private val apiCall = APIClass()

    fun getRestaurantReview(restaurantID: String) =
        apiCall.restaurantReviewAPICall(restaurantID)

    fun getRestaurantDetail(restaurantID: String) =
        apiCall.restaurantDetailAPICall(restaurantID)

    fun getRestaurantResult(
        categoryInput: String,
        locationInput: String,
        restaurantNameInput: String,
    ) =
        apiCall.restaurantResultAPICall(categoryInput, locationInput, restaurantNameInput)
}

