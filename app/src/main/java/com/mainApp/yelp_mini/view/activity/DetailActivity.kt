package com.mainApp.yelp_mini.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.mainApp.yelp_mini.R
import com.mainApp.yelp_mini.databinding.ActivityDetailBinding
import com.mainApp.yelp_mini.model.data.RestaurantReview
import com.mainApp.yelp_mini.view.adapter.MyFragmentPagerAdapter
import com.mainApp.yelp_mini.view.fragment.OverviewFragment
import com.mainApp.yelp_mini.view.fragment.ReviewsFragment
import com.mainApp.yelp_mini.viewModel.DetailViewModelClass

private const val TAG = "DetailActivityAPICALL"
private const val BASE_URL = "https://api.yelp.com/v3/"
private const val API_KEY =
    "Vzx4IpLKoLVNrsNwyg6jc-4qq_TOmV_w2h8jl0QJmnmwV_cKIBkCpWSRbp5ws7D4wxw6eSIbVtUrIIRB6-BadCBge5xxnICs4h92A-8nuORQJGtW9MQvvk_IGH0fY3Yx"
private lateinit var restaurantID: String

class DetailActivity : AppCompatActivity() {
    val reviews = mutableListOf<RestaurantReview>()
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras
        if (extras != null) {
            restaurantID = extras.getString("restaurantID") as String
        }

        val viewPager = binding.viewpager
        val pagerAdapter = MyFragmentPagerAdapter(supportFragmentManager, this)
        viewPager.adapter = pagerAdapter


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
                pagerAdapter.addFragment(OverviewFragment(this, it), "Overview")
                pagerAdapter.notifyDataSetChanged()
                supportActionBar!!.title = it.name

            }
        }

        detailViewModelClass.getRestaurantsReviewLists().observe(this) {
            if (it.reviews.isEmpty()) {
                Log.d("Blankresult", "Error in getting list")
                Toast.makeText(this, "Error in getting list", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("BlankresultNot", "$it.restaurants")
                reviews.addAll(it.reviews)
                pagerAdapter.addFragment(ReviewsFragment(this, reviews), "Reviews")
                pagerAdapter.notifyDataSetChanged()

            }
        }

        detailViewModelClass.restaurantDetailAPICall(restaurantID)
        detailViewModelClass.restaurantReviewAPICall(restaurantID)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}