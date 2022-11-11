package com.mainApp.yelp_mini.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mainApp.yelp_mini.model.data.YelpSearchResult
import com.mainApp.yelp_mini.model.repo.RepoClass

open class ResultsViewModelClass : ViewModel() {
    private val TAG = "ResultsViewModelClass"
    private var restaurants: MutableLiveData<YelpSearchResult> = MutableLiveData()
    private val repoClass by lazy { RepoClass() }

    fun getLiveDataObserver(): MutableLiveData<YelpSearchResult> {
        return restaurants
    }

    @SuppressLint("LongLogTag")
    fun makeAPICall(categoryInput: String, locationInput: String, restaurantNameInput: String) {
        restaurants =
            repoClass.getRestaurantResult(categoryInput, locationInput, restaurantNameInput)
        Log.w("$TAG makeAPICall", repoClass.toString())

    }
}