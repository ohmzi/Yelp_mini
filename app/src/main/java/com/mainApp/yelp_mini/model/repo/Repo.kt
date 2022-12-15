package com.mainApp.yelp_mini.model.repo

import androidx.lifecycle.MutableLiveData
import com.mainApp.yelp_mini.model.data.YelpRestaurantDetail
import com.mainApp.yelp_mini.model.data.YelpRestaurantReviews
import com.mainApp.yelp_mini.model.data.YelpSearchResult

class Repo {
    private val apiCall = APICalls()
    private var restaurants: MutableLiveData<YelpSearchResult?> = MutableLiveData()
    private var restaurantsDetailList: YelpRestaurantDetail? = null
    private  var restaurantsReviewList: YelpRestaurantReviews? = null

    fun getRestaurantReview(restaurantID: String): YelpRestaurantReviews? {
        restaurantsReviewList = apiCall.restaurantReviewAPICall(restaurantID)
        return restaurantsReviewList
    }

    fun getRestaurantDetail(restaurantID: String): YelpRestaurantDetail? {
        restaurantsDetailList = apiCall.restaurantDetailAPICall(restaurantID)
        return restaurantsDetailList
    }

    suspend fun getRestaurantResult(
        categoryInput: String,
        locationInput: String,
        restaurantNameInput: String,
    ): YelpSearchResult? {
        return try {
            apiCall.restaurantResultAPICall(
                categoryInput,
                locationInput,
                restaurantNameInput
            )
        } catch (e: Exception) {
            null
        }
    }

    fun apiCall(restaurantID: String): YelpRestaurantDetail? {
        return try {
            apiCall.restaurantDetailAPICall2(restaurantID)
        } catch (e: Exception) {
            null
        }
    }

}

