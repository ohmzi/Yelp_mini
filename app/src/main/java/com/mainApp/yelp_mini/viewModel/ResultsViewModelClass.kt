package com.mainApp.yelp_mini.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mainApp.yelp_mini.model.data.YelpSearchResult

open class ResultsViewModelClass : ViewModel() {
    private val TAG = "ResultsViewModelClass"
    lateinit var categoryInputLocal: String
    lateinit var locationInputLocal: String
    lateinit var restaurantNameInputLocal: String
    val restaurants: MutableLiveData<YelpSearchResult> = MutableLiveData()


    fun getLiveDataObserver(): MutableLiveData<YelpSearchResult> {
        return restaurants
    }

}