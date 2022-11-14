package com.mainApp.yelp_mini.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mainApp.yelp_mini.model.data.YelpSearchResult
import com.mainApp.yelp_mini.model.repo.Repo
private const val TAG = "ResultsViewModelClass"

open class ResultsViewModel : ViewModel() {
    private var restaurants: MutableLiveData<YelpSearchResult?> = MutableLiveData()
    private val repo by lazy { Repo() }

    fun getRestaurantsResultLists(): MutableLiveData<YelpSearchResult?> {
        return restaurants
    }

    @SuppressLint("LongLogTag")
    fun makeAPICall(categoryInput: String, locationInput: String, restaurantNameInput: String) {
        restaurants =
            repo.getRestaurantResult(categoryInput, locationInput, restaurantNameInput)
        Log.w("$TAG makeAPICall", repo.toString())

    }
}