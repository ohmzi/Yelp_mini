package com.mainApp.yelp_mini.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mainApp.yelp_mini.model.data.YelpSearchResult
import com.mainApp.yelp_mini.model.retro_services.RetroInstance
import com.mainApp.yelp_mini.model.retro_services.YelpService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultsViewModelClass : ViewModel() {
    private val TAG = "ViewModel"
    private val API_KEY =
        "Vzx4IpLKoLVNrsNwyg6jc-4qq_TOmV_w2h8jl0QJmnmwV_cKIBkCpWSRbp5ws7D4wxw6eSIbVtUrIIRB6-BadCBge5xxnICs4h92A-8nuORQJGtW9MQvvk_IGH0fY3Yx"
    private lateinit var categoryInputLocal: String
    private lateinit var locationInputLocal: String
    private lateinit var restaurantNameInputLocal: String
    val restaurants: MutableLiveData<YelpSearchResult> = MutableLiveData()


    fun getLiveDataObserver(): MutableLiveData<YelpSearchResult> {
        return restaurants
    }

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