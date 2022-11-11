package com.mainApp.yelp_mini.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mainApp.yelp_mini.model.data.YelpRestaurantDetail
import com.mainApp.yelp_mini.model.data.YelpRestaurantReviews
import com.mainApp.yelp_mini.model.repo.RepoClass

private const val TAG = "DetailViewModelClass"

class DetailViewModelClass : ViewModel() {
    private val repoClass by lazy { RepoClass() }
    private var restaurantsDetailList: MutableLiveData<YelpRestaurantDetail> = MutableLiveData()
    private var restaurantsReviewList: MutableLiveData<YelpRestaurantReviews> = MutableLiveData()


    fun getRestaurantsDetailLists(): MutableLiveData<YelpRestaurantDetail> {
        return restaurantsDetailList
    }

    fun getRestaurantsReviewLists(): MutableLiveData<YelpRestaurantReviews> {
        return restaurantsReviewList
    }

    fun restaurantDetailAPICall(restaurantID: String) {
        restaurantsDetailList = repoClass.getRestaurantDetail(restaurantID)
    }

    fun restaurantReviewAPICall(restaurantID: String) {
        restaurantsReviewList = repoClass.getRestaurantReview(restaurantID)
    }
}