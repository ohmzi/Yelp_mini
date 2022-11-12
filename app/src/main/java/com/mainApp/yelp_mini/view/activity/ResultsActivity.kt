package com.mainApp.yelp_mini.view.activity


import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mainApp.yelp_mini.databinding.ActivityResultBinding
import com.mainApp.yelp_mini.view.adapter.ResultsRestaurantsAdapter
import com.mainApp.yelp_mini.viewModel.ResultsViewModelClass


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
        val extras = intent.extras
        if (extras != null) {
            categoryTextInput = extras.getString("categoryTextInput") as String
            locationTextInput = extras.getString("locationTextInput") as String
            restaurantNameTextInput = extras.getString("restaurantNameTextInput") as String
        }
        binding = ActivityResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        with(binding) {
            rvRestaurants.layoutManager = LinearLayoutManager(this@ResultsActivity)
            rvRestaurants.adapter = recyclerAdapter
            shimmer()
        }

        val resultsViewModelClass: ResultsViewModelClass =
            ViewModelProvider(this)[ResultsViewModelClass::class.java]

        resultsViewModelClass.makeAPICall(categoryTextInput,
            locationTextInput,
            restaurantNameTextInput)

        resultsViewModelClass.getRestaurantsResultLists().observe(this) {

            if (it != null) {
                if (((it.total) == 0)) {
                    Log.d(TAG, "BlankResult, Error in getting list tostring $it")
                    binding.shimmerView.visibility = View.INVISIBLE
                    binding.imageViewLost.visibility = View.VISIBLE
                    Toast.makeText(this, "Error in getting list", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d(TAG, "NotBlankResult, $it.restaurants")
                    recyclerAdapter.setRestaurantsList(it.restaurants)
                    recyclerAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun shimmer() {
        binding.rvRestaurants.visibility = View.INVISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            with(binding) {
                shimmerView.startShimmer()
                rvRestaurants.visibility = View.VISIBLE
                shimmerView.stopShimmer()
                shimmerView.visibility = View.INVISIBLE
            }
        }, 4000)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}


