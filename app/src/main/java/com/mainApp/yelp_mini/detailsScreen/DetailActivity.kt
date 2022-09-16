package com.mainApp.yelp_mini.detailsScreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.mainApp.yelp_mini.R
import com.mainApp.yelp_mini.data.YelpRestaurantDetail
import com.mainApp.yelp_mini.data.RestaurantReview
import com.mainApp.yelp_mini.data.YelpRestaurantReviews
import com.mainApp.yelp_mini.fragments.MyFragmentPagerAdapter
import com.mainApp.yelp_mini.fragments.OverviewFragment
import com.mainApp.yelp_mini.fragments.ReviewsFragment
import com.mainApp.yelp_mini.retro_services.RetroInstance
import com.mainApp.yelp_mini.retro_services.YelpService
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_restaurant_overview.*
import kotlinx.android.synthetic.main.fragment_restaurant_reviews.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

private const val TAG = "DetailActivityAPICALL"
private const val BASE_URL = "https://api.yelp.com/v3/"
private const val API_KEY =
    "Vzx4IpLKoLVNrsNwyg6jc-4qq_TOmV_w2h8jl0QJmnmwV_cKIBkCpWSRbp5ws7D4wxw6eSIbVtUrIIRB6-BadCBge5xxnICs4h92A-8nuORQJGtW9MQvvk_IGH0fY3Yx"
private lateinit var restaurantID: String

class DetailActivity : AppCompatActivity() {
    val reviews = mutableListOf<RestaurantReview>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras
        if (extras != null) {
            restaurantID = extras.getString("restaurantID") as String
        }

        val viewPager = findViewById<View>(R.id.viewpager) as ViewPager
        val pagerAdapter = MyFragmentPagerAdapter(supportFragmentManager, this)
        viewPager.adapter = pagerAdapter

        pagerAdapter.addFragment(OverviewFragment(), "Overview")
        pagerAdapter.addFragment(ReviewsFragment(), "Reviews")
        pagerAdapter.notifyDataSetChanged()

        val tabLayout = findViewById<View>(R.id.sliding_tabs) as TabLayout
        tabLayout.setupWithViewPager(viewPager)

        restaurantDetailAPICall()
        restaurantReviewAPICall()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun restaurantDetailAPICall() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
                supportActionBar!!.title = body.name
                photos.addAll(body.photos)
                bindInfo(body)
            }
            override fun onFailure(call: Call<YelpRestaurantDetail>, t: Throwable) {
                Log.i(TAG, "onFailure $t")
            }
        })
    }

    private fun restaurantReviewAPICall() {
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
                bindReviews()
                reviews.addAll(body.reviews)
            }
            override fun onFailure(call: Call<YelpRestaurantReviews>, t: Throwable) {
                Log.i(TAG, "onFailure $t")
            }
        })
    }


    @SuppressLint("SetTextI18n")
    fun bindInfo(body: YelpRestaurantDetail) {
        var price = body.price
        if (price == null) {
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
        tvTransactions.text =
            body.transactions.joinToString { t ->
                t.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                }
            }


    }

    fun bindReviews() {
        rvReviews.adapter = DetailReviewAdapter(this, reviews)
        rvReviews.layoutManager = LinearLayoutManager(this)
        rvReviews.addItemDecoration(
            DividerItemDecoration(
                rvReviews.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }
}