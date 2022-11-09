package com.mainApp.yelp_mini.viewModel.repo

import android.util.Log
import com.mainApp.yelp_mini.model.data.YelpSearchResult
import com.mainApp.yelp_mini.model.retroServices.RetroInstance
import com.mainApp.yelp_mini.model.retroServices.YelpService
import com.mainApp.yelp_mini.model.util.API_KEY
import com.mainApp.yelp_mini.viewModel.ResultsViewModelClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class repoClass : ResultsViewModelClass() {
    private val TAG = "Repo"

    fun makeAPICall(categoryInput: String, locationInput: String, restaurantNameInput: String) {
        categoryInputLocal = categoryInput
        locationInputLocal = locationInput
        restaurantNameInputLocal = restaurantNameInput

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

    }
}