package com.mainApp.yelp_mini.view.activity


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mainApp.yelp_mini.databinding.ActivityResultBinding
import com.mainApp.yelp_mini.view.adapter.ResultsRestaurantsAdapter
import com.mainApp.yelp_mini.viewModel.ResultsViewModel


class ResultsActivity : AppCompatActivity() {
    private val recyclerAdapter by lazy { ResultsRestaurantsAdapter(this) }

    private lateinit var binding: ActivityResultBinding
    private lateinit var categoryTextInput: String
    private lateinit var locationTextInput: String
    private lateinit var restaurantNameTextInput: String
    private val TAG = "ResultsActivity"


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Yelp Results"
        intent.extras.let {
            val bundle = it
            if (bundle != null) {
                categoryTextInput = bundle.getString("categoryTextInput") as String
                locationTextInput = bundle.getString("locationTextInput") as String
                restaurantNameTextInput = bundle.getString("restaurantNameTextInput") as String
            }
        }
        binding = ActivityResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        with(binding) {
            rvRestaurants.layoutManager = LinearLayoutManager(this@ResultsActivity)
            rvRestaurants.adapter = recyclerAdapter
            shimmerView.startShimmer()
            rvRestaurants.visibility = View.VISIBLE
        }

        val resultsViewModelClass: ResultsViewModel =
            ViewModelProvider(this)[ResultsViewModel::class.java]

        resultsViewModelClass.makeAPICall(
            categoryTextInput,
            locationTextInput,
            restaurantNameTextInput
        )

        resultsViewModelClass.restaurants.observe(this) {
            val searchResults = it
            if (searchResults != null) {
                if (searchResults.total == 0) {
                    Log.d(TAG, "BlankResult, Error in getting list tostring $searchResults")
                    with(binding) {
                        shimmerView.stopShimmer()
                        shimmerView.visibility = View.INVISIBLE
                        imageViewLost.visibility = View.VISIBLE
                    }
                    Toast.makeText(this, "Error in getting list", Toast.LENGTH_SHORT).show()
                } else {
                    binding.shimmerView.stopShimmer()
                    binding.shimmerView.visibility = View.INVISIBLE
                    Log.d(TAG, "NotBlankResult, $searchResults.restaurants")
                    recyclerAdapter.setRestaurantsList(searchResults.restaurants)
                    recyclerAdapter.notifyDataSetChanged()
                }
            }
        }

        resultsViewModelClass.errorLiveData.observe(this) {
            //Do something related to error scenario
            //show toast or popup
            binding.shimmerView.stopShimmer()
            binding.shimmerView.visibility = View.INVISIBLE
            Toast.makeText(this, " API Error ", Toast.LENGTH_LONG).show()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}


