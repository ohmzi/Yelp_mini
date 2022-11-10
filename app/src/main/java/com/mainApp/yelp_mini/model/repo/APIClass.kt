package com.mainApp.yelp_mini.model.repo

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mainApp.yelp_mini.model.data.YelpRestaurantDetail
import com.mainApp.yelp_mini.model.data.YelpRestaurantReviews
import com.mainApp.yelp_mini.model.data.YelpSearchResult
import com.mainApp.yelp_mini.model.retroServices.RetroInstance
import com.mainApp.yelp_mini.model.retroServices.YelpService
import com.mainApp.yelp_mini.model.util.API_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "APIClass"

class APIClass {

    val restaurantsDetailList: MutableLiveData<YelpRestaurantDetail> = MutableLiveData()
    val restaurantsReviewList: MutableLiveData<YelpRestaurantReviews?> = MutableLiveData()
    val restaurants: MutableLiveData<YelpSearchResult> = MutableLiveData()


    fun restaurantReviewAPICall(restaurantID: String) {
        val retroInstance = RetroInstance.getRetroInstance()
        val yelpService = retroInstance.create(YelpService::class.java)
        val call = yelpService.getRestaurantsReviews("Bearer $API_KEY", restaurantID)


        call.enqueue(object : Callback<YelpRestaurantReviews> {
            @SuppressLint("LongLogTag")
            override fun onResponse(
                call: Call<YelpRestaurantReviews>,
                response: Response<YelpRestaurantReviews>,
            ) {
                Log.i("$TAG RestaurantReviewAPICall", "onResponse $response")
                val body = response.body()
                if (body == null) {
                    Log.w(TAG, "Did not receive valid response body from Yelp API... exiting")
                    return
                }
                //   bindReviews()
                // reviews.addAll(body.reviews)
                restaurantsReviewList.postValue(body)//since this is in else statement when null is not possible, the nullable value is not getting sent.

            }

            override fun onFailure(call: Call<YelpRestaurantReviews>, t: Throwable) {
                Log.i(TAG, "onFailure $t")
            }
        })
    }


    fun restaurantDetailAPICall(restaurantID: String) {
        val photos = mutableListOf<String>()
        val retroInstance = RetroInstance.getRetroInstance()
        val yelpService = retroInstance.create(YelpService::class.java)
        val call = yelpService.getRestaurantsDetails("Bearer $API_KEY", restaurantID)

        call.enqueue(object : Callback<YelpRestaurantDetail> {
            @SuppressLint("LongLogTag")
            override fun onResponse(
                call: Call<YelpRestaurantDetail>,
                response: Response<YelpRestaurantDetail>,
            ) {
                Log.i("$TAG RestaurantDetailAPICall", "onResponse $response")
                val body = response.body()
                if (body == null) {
                    Log.w(TAG, "Did not receive valid response body from Yelp API... exiting")
                    return
                }
                Log.w(TAG, body.transactions.toString())
                photos.addAll(body.photos)
                //    bindInfo(body)
                restaurantsDetailList.postValue(body)//since this is in else statement when null is not possible, the nullable value is not getting sent.

            }

            override fun onFailure(call: Call<YelpRestaurantDetail>, t: Throwable) {
                Log.i(TAG, "onFailure $t")
            }
        })
    }

    fun restaurantResultAPICall(
        categoryInput: String,
        locationInput: String,
        restaurantNameInput: String,
    ) {
        val retroInstance = RetroInstance.getRetroInstance()
        val yelpService = retroInstance.create(YelpService::class.java)
        val call = yelpService.getRestaurantsResults("Bearer $API_KEY",
            restaurantNameInput,
            categoryInput,
            locationInput)


        call.enqueue(
            object :
                Callback<YelpSearchResult> {
                @SuppressLint("LongLogTag")
                override fun onResponse(
                    call: Call<YelpSearchResult>,
                    response: Response<YelpSearchResult>,
                ) {
                    Log.i("$TAG RestaurantResultAPICall",
                        "makeAPICall onResponse on call $response")
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