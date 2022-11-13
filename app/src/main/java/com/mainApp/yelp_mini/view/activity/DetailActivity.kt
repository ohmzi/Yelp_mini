package com.mainApp.yelp_mini.view.activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mainApp.yelp_mini.databinding.ActivityDetailBinding
import com.mainApp.yelp_mini.model.data.YelpRestaurantDetail
import com.mainApp.yelp_mini.view.adapter.MyFragmentPagerAdapter
import com.mainApp.yelp_mini.view.fragment.OverviewFragment
import com.mainApp.yelp_mini.view.fragment.ReviewsFragment
import com.mainApp.yelp_mini.viewModel.DetailViewModelClass

private const val TAG = "DetailActivityAPICALL"


class DetailActivity : AppCompatActivity() {
    private lateinit var restaurantID: String
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras
        if (extras != null) {
            restaurantID = extras.getString("restaurantID") as String
        }

        val viewPager = binding.viewpager
        val pagerAdapter = MyFragmentPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter

        val tabLayout = binding.slidingTabs
        tabLayout.setupWithViewPager(viewPager)
        val detailViewModelClass: DetailViewModelClass =
            ViewModelProvider(this)[DetailViewModelClass::class.java]

        if (restaurantID != null) {
            detailViewModelClass.restaurantDetailAPICall(restaurantID)
            detailViewModelClass.restaurantReviewAPICall(restaurantID)
        } else {
            Toast.makeText(this, "Error in getting list", Toast.LENGTH_SHORT).show()
        }

        detailViewModelClass.getRestaurantsDetailLists().observe(this) {

            if (it != null) {
                if (it.name.isEmpty()) {
                    Log.d("Blankresult", "Error in getting list")
                    Toast.makeText(this, "Error in getting list", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("BlankresultNot", "$it.restaurants")
                    OverviewFragment(it)
                        .let { it2 -> pagerAdapter.addFragment(it2, "Overview") }
                    pagerAdapter.notifyDataSetChanged()
                    bindBusinessPicture(it)
                    supportActionBar?.title = it.name

                }
            }
        }

        detailViewModelClass.getRestaurantsReviewLists().observe(this) {
            if (it != null) {
                if (it.reviews.isEmpty()) {
                    Log.d("Blankresult", "Error in getting list")
                    Toast.makeText(this, "Error in getting list", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("BlankresultNot", "$it.restaurants")
                    pagerAdapter.addFragment(ReviewsFragment(this, it.reviews), "Reviews")
                    pagerAdapter.notifyDataSetChanged()

                }
            }
        }
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