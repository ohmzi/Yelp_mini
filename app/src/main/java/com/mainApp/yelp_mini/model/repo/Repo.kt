package com.mainApp.yelp_mini.model.repo

import androidx.lifecycle.MutableLiveData
import com.mainApp.yelp_mini.model.data.YelpRestaurantDetail
import com.mainApp.yelp_mini.model.data.YelpRestaurantReviews
import com.mainApp.yelp_mini.model.data.YelpSearchResult

class Repo {
    private val apiCall = APICalls()
    private var restaurants: MutableLiveData<YelpSearchResult?> = MutableLiveData()
    private var restaurantsDetailList: MutableLiveData<YelpRestaurantDetail?> = MutableLiveData()
    private  var restaurantsReviewList: YelpRestaurantReviews? = null

    fun getRestaurantReview(restaurantID: String): YelpRestaurantReviews? {
        restaurantsReviewList = apiCall.restaurantReviewAPICall(restaurantID)
        return restaurantsReviewList
    }

    fun getRestaurantDetail(restaurantID: String): MutableLiveData<YelpRestaurantDetail?> {
        restaurantsDetailList = apiCall.restaurantDetailAPICall(restaurantID)
        return restaurantsDetailList
    }

    fun getRestaurantResult(
        categoryInput: String,
        locationInput: String,
        restaurantNameInput: String,
    ): MutableLiveData<YelpSearchResult?> {
        restaurants =
            apiCall.restaurantResultAPICall(categoryInput,
                locationInput,
                restaurantNameInput)
        return restaurants
    }


}

