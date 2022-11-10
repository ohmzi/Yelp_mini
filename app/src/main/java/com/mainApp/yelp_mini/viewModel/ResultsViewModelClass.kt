package com.mainApp.yelp_mini.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mainApp.yelp_mini.model.data.YelpSearchResult
import com.mainApp.yelp_mini.model.repo.RepoClass

open class ResultsViewModelClass : ViewModel() {
    private val TAG = "ResultsViewModelClass"

    val restaurants: MutableLiveData<YelpSearchResult> = MutableLiveData()
    private val repoClass by lazy { RepoClass() }


    fun getLiveDataObserver(): MutableLiveData<YelpSearchResult> {
        return restaurants
    }

    @SuppressLint("LongLogTag")
    fun makeAPICall(categoryInput: String, locationInput: String, restaurantNameInput: String) {
        repoClass.getRestaurantResult(categoryInput, locationInput, restaurantNameInput)
        Log.w("$TAG makeAPICall", repoClass.toString())

    }


/* fun makeAPICall(categoryInput: String, locationInput: String, restaurantNameInput: String) {
     val retroInstance = RetroInstance.getRetroInstance()
     val yelpService = retroInstance.create(YelpService::class.java)
     val call = yelpService.getRestaurantsResults("Bearer $API_KEY",
         restaurantNameInput,
         categoryInput,
         locationInput)


     call.enqueue(
         object :
             Callback<YelpSearchResult> {
             override fun onResponse(
                 call: Call<YelpSearchResult>,
                 response: Response<YelpSearchResult>,
             ) {
                 Log.i(TAG, "makeAPICall onResponse on call $response")
                 val body = response.body()
                 if (body == null) {
                     Log.w(
                         TAG,
                         "body = null, ${body?.total} , Did not receive valid response body from Yelp API... exiting"
                     )
                     Log.w(
                         TAG,
                         "restaurants.value?.total, ${restaurants.value?.total} , Did not receive valid response body from Yelp API... exiting"
                     )
                     return

                 } else {
                     Log.w(
                         TAG,
                         "body != null. ${body.total}"
                     )
                     restaurants.postValue(body)//since this is in else statement when null is not possible, the nullable value is not getting sent.
                     Log.w(TAG, "onResponse $body")

                 }

             }

             override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                 Log.i(TAG, "onFailure $t")
             }
         })

 }*/
}