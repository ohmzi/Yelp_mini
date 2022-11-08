package com.mainApp.yelp_mini.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mainApp.yelp_mini.model.data.YelpRestaurantDetail
import com.mainApp.yelp_mini.model.data.YelpRestaurantReviews
import com.mainApp.yelp_mini.model.retro_services.API_KEY
import com.mainApp.yelp_mini.model.retro_services.RetroInstance
import com.mainApp.yelp_mini.model.retro_services.YelpService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "DetailActivityAPICALL"

class DetailViewModelClass : ViewModel() {

    val restaurantsDetailList: MutableLiveData<YelpRestaurantDetail> = MutableLiveData()
    val restaurantsReviewList: MutableLiveData<YelpRestaurantReviews> = MutableLiveData()


    fun getRestaurantsDetailLists(): MutableLiveData<YelpRestaurantDetail> {
        return restaurantsDetailList
    }

    fun getRestaurantsReviewLists(): MutableLiveData<YelpRestaurantReviews> {
        return restaurantsReviewList
    }

    fun restaurantDetailAPICall(restaurantID: String) {
        val photos = mutableListOf<String>()
        val retroInstance = RetroInstance.getRetroInstance()
        val yelpService = retroInstance.create(YelpService::class.java)
        val call = yelpService.getRestaurantsDetails("Bearer $API_KEY", restaurantID)

        call.enqueue(object : Callback<YelpRestaurantDetail> {
            override fun onResponse(
                call: Call<YelpRestaurantDetail>,
                response: Response<YelpRestaurantDetail>,
            ) {
                Log.i(TAG, "onResponse $response")
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


    fun restaurantReviewAPICall(restaurantID: String) {
        val retroInstance = RetroInstance.getRetroInstance()
        val yelpService = retroInstance.create(YelpService::class.java)
        val call = yelpService.getRestaurantsReviews("Bearer $API_KEY", restaurantID)


        call.enqueue(object : Callback<YelpRestaurantReviews> {
            override fun onResponse(
                call: Call<YelpRestaurantReviews>,
                response: Response<YelpRestaurantReviews>,
            ) {
                Log.i(TAG, "onResponse $response")
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
}