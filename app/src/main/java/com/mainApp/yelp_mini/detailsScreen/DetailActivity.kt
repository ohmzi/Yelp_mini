package com.mainApp.yelp_mini.detailsScreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.mainApp.yelp_mini.R
import com.mainApp.yelp_mini.data.RestaurantReview
import com.mainApp.yelp_mini.data.YelpRestaurantDetail
import com.mainApp.yelp_mini.fragments.MyFragmentPagerAdapter
import com.mainApp.yelp_mini.fragments.OverviewFragment
import com.mainApp.yelp_mini.fragments.ReviewsFragment
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_restaurant_overview.*
import kotlinx.android.synthetic.main.fragment_restaurant_reviews.*
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

        val detailViewModelClass: DetailViewModelClass =
            ViewModelProvider(this)[DetailViewModelClass::class.java]

        detailViewModelClass.getRestaurantsDetailLists().observe(this) {
            if (it.name.isEmpty()) {
                Log.d("Blankresult", "Error in getting list")
                Toast.makeText(this, "Error in getting list", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("BlankresultNot", "$it.restaurants")

                // recyclerAdapter.setRestaurantsList(it)
                //     recyclerAdapter.notifyDataSetChanged()
                bindInfo(it)
                supportActionBar!!.title = it.name

            }
        }
        detailViewModelClass.getRestaurantsReviewLists().observe(this) {
            if (it.reviews.isEmpty()) {
                Log.d("Blankresult", "Error in getting list")
                Toast.makeText(this, "Error in getting list", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("BlankresultNot", "$it.restaurants")

                // recyclerAdapter.setRestaurantsList(it)
                //     recyclerAdapter.notifyDataSetChanged()
                bindReviews()
                reviews.addAll(it.reviews)
            }
        }


        detailViewModelClass.restaurantDetailAPICall(restaurantID)
        detailViewModelClass.restaurantReviewAPICall(restaurantID)


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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