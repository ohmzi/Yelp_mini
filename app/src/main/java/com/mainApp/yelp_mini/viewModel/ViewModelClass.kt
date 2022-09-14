package com.mainApp.yelp_mini.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mainApp.yelp_mini.data.YelpSearchResult
import com.mainApp.yelp_mini.retro_services.RetroInstance
import com.mainApp.yelp_mini.retro_services.YelpService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelClass : ViewModel() {
    private val TAG = "ViewModel"
    private val API_KEY =
        "Vzx4IpLKoLVNrsNwyg6jc-4qq_TOmV_w2h8jl0QJmnmwV_cKIBkCpWSRbp5ws7D4wxw6eSIbVtUrIIRB6-BadCBge5xxnICs4h92A-8nuORQJGtW9MQvvk_IGH0fY3Yx"

    val restaurants: MutableLiveData<YelpSearchResult> = MutableLiveData()


    fun getLiveDataObserver(): MutableLiveData<YelpSearchResult> {
        return restaurants
    }

    fun makeAPICall() {

        val retroInstance = RetroInstance.getRetroInstance()
        val yelpService = retroInstance.create(YelpService::class.java)
        val call = yelpService.searchRestaurants("Bearer $API_KEY", "Bagel", "Paris")


        call.enqueue(
            object :
                Callback<YelpSearchResult> {
                override fun onResponse(
                    call: Call<YelpSearchResult>,
                    response: Response<YelpSearchResult>,
                ) {
                    Log.i(TAG, "onResponse $response")
                    val body = response.body()
                    if (body == null) {
                        Log.w(
                            TAG,
                            "Did not receive valid response body from Yelp API... exiting"
                        )
                        return
                    } else {
                        restaurants.postValue(body)
                        Log.d("MakeAPICall", "onResponse $body")
                        //    Log.d("restaurantsAT2", "onResponse ${restaurants[2]}")

                    }

                }

                override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                    Log.i(TAG, "onFailure $t")
                }
            })

    }


}