package com.mainApp.yelp_mini.view.activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.mainApp.yelp_mini.R
import com.mainApp.yelp_mini.databinding.ActivityDetailBinding
import com.mainApp.yelp_mini.model.data.RestaurantReview
import com.mainApp.yelp_mini.model.data.YelpRestaurantDetail
import com.mainApp.yelp_mini.view.adapter.MyFragmentPagerAdapter
import com.mainApp.yelp_mini.view.fragment.OverviewFragment
import com.mainApp.yelp_mini.view.fragment.ReviewsFragment
import com.mainApp.yelp_mini.viewModel.DetailViewModelClass
private const val TAG = "DetailActivityAPICALL"


class DetailActivity : AppCompatActivity() {
    private lateinit var restaurantID: String
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
                Log.d(TAG, "Error in getting list for Restaurant Detail")
                Toast.makeText(this, "Error in getting list", Toast.LENGTH_SHORT).show()
            } else {
                Log.d(TAG, "Restaurant Detail for $it.restaurants")
                pagerAdapter.addFragment(OverviewFragment(this, it), "Overview")
                //   pagerAdapter.getItem(0)
                pagerAdapter.notifyDataSetChanged()
                bindBusinessPicture(it)
                supportActionBar!!.title = it.name
            }
        }

        detailViewModelClass.getRestaurantsReviewLists().observe(this) {
            if (it.reviews.isEmpty()) {
                Log.d(TAG, "Error in getting list for Restaurant Review")
                Toast.makeText(this, "Error in getting list", Toast.LENGTH_SHORT).show()
            } else {
                Log.d(TAG, "Restaurant Review for $it.restaurants")
                reviews.addAll(it.reviews)
                pagerAdapter.addFragment(ReviewsFragment(this, reviews), "Reviews")
                //  pagerAdapter.getItem(1)
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

    private fun bindBusinessPicture(body: YelpRestaurantDetail) {
        Glide.with(this@DetailActivity).load(body.imageUrl).apply(
            RequestOptions().transforms(
                CenterCrop(), RoundedCorners(20)
            )).into(binding.imageViewBusinessDetail)

    }

}