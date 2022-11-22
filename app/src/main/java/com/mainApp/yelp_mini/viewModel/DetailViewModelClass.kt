package com.mainApp.yelp_mini.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mainApp.yelp_mini.model.data.YelpRestaurantDetail
import com.mainApp.yelp_mini.model.data.YelpRestaurantReviews
import com.mainApp.yelp_mini.model.repo.Repo

private const val TAG = "DetailViewModelClass"

class DetailViewModelClass : ViewModel() {
    private val repo by lazy { Repo() }
    private var restaurantsDetailList: MutableLiveData<YelpRestaurantDetail?> = MutableLiveData()
    private var restaurantsReviewList: MutableLiveData<YelpRestaurantReviews?> = MutableLiveData()


    fun getRestaurantsDetailLists(): MutableLiveData<YelpRestaurantDetail?> {
        return restaurantsDetailList
    }

    fun getRestaurantsReviewLists(): MutableLiveData<YelpRestaurantReviews?> {
        return restaurantsReviewList
    }

    fun restaurantDetailAPICall(restaurantID: String) {
        restaurantsDetailList.postValue (repo.getRestaurantDetail(restaurantID))
    }

    fun restaurantReviewAPICall(restaurantID: String) {
        restaurantsReviewList.postValue(repo.getRestaurantReview(restaurantID))
    }
}