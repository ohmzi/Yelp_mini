package com.mainApp.yelp_mini.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mainApp.yelp_mini.model.data.YelpSearchResult
import com.mainApp.yelp_mini.model.repo.Repo
import kotlinx.coroutines.launch

private const val TAG = "ResultsViewModelClass"

open class ResultsViewModel : ViewModel() {
    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    private val _restaurants: MutableLiveData<YelpSearchResult?> = MutableLiveData()
    val restaurants: LiveData<YelpSearchResult?> = _restaurants

    private val repo by lazy { Repo() }

    @SuppressLint("LongLogTag")
    fun makeAPICall(categoryInput: String, locationInput: String, restaurantNameInput: String) {
        viewModelScope.launch {
            val response =
                repo.getRestaurantResult(categoryInput, locationInput, restaurantNameInput)

            if (response != null) {
                _restaurants.value = response
            } else {
                _errorLiveData.value = "error occurred"
            }
            Log.w("$TAG makeAPICall", repo.toString())
        }

    }
}