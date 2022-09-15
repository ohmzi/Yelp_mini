package com.mainApp.yelp_mini

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mainApp.yelp_mini.data.YelpBusinessDetail
import com.mainApp.yelp_mini.retro_services.RetroInstance
import com.mainApp.yelp_mini.retro_services.YelpService
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "DetailActivityAPICALL"
private const val BASE_URL = "https://api.yelp.com/v3/"
private const val API_KEY =
    "Vzx4IpLKoLVNrsNwyg6jc-4qq_TOmV_w2h8jl0QJmnmwV_cKIBkCpWSRbp5ws7D4wxw6eSIbVtUrIIRB6-BadCBge5xxnICs4h92A-8nuORQJGtW9MQvvk_IGH0fY3Yx"
private lateinit var restaurantID: String

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val extras = intent.extras
        if (extras != null) {
            restaurantID = extras.getString("restaurantID") as String
            //  drestaurantID.text = "ID: $restaurantID"
        }

        makeAPICall()
    }


    fun makeAPICall() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        val photos = mutableListOf<String>()

        val retroInstance = RetroInstance.getRetroInstance()
        val yelpService = retroInstance.create(YelpService::class.java)
        val call = yelpService.getDetails("Bearer $API_KEY", restaurantID)


        call.enqueue(object : Callback<YelpBusinessDetail> {
            override fun onResponse(
                call: Call<YelpBusinessDetail>,
                response: Response<YelpBusinessDetail>,
            ) {
                Log.i(TAG, "onResponse $response")
                val body = response.body()
                if (body == null) {
                    Log.w(TAG, "Did not receive valid response body from Yelp API... exiting")
                    return
                }
                Log.w(TAG, body.transactions.toString())

                photos.addAll(body.photos)
                bind(body)
            }

            override fun onFailure(call: Call<YelpBusinessDetail>, t: Throwable) {
                Log.i(TAG, "onFailure $t")
            }
        })


    }


    fun bind(body: YelpBusinessDetail) {
        var price = body.price
        if (price == null){
            price = "$"
        }
        Glide.with(this@DetailActivity).load(body.imageUrl).apply(
            RequestOptions().transforms(
                CenterCrop(), RoundedCorners(20)
            )).into(imageViewBusinessDetail)

        tvName.text = body.name
        ratingBar.rating = body.rating.toFloat()
        tvNumReviews.text = "${body.numReviews} Reviews"

        tvPrice.text = price
        tvCategory.text = body.categories.joinToString { c -> c.title }
        tvAddress.text = "Address: ${body.location.address}"
        tvPhone.text = "Call: ${body.phone}"
        tvHours.text = body.transactions.joinToString { t -> t.capitalize() }


    }
}