package com.mainApp.yelp_mini.resultsScreen


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mainApp.yelp_mini.databinding.ActivityResultBinding
import kotlinx.android.synthetic.main.activity_result.*


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
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Yelp Results"
        val extras = intent.extras
        if (extras != null) {
            categoryTextInput = extras.getString("categoryTextInput") as String
            locationTextInput = extras.getString("locationTextInput") as String
            restaurantNameTextInput = extras.getString("restaurantNameTextInput") as String
        }

        binding = ActivityResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.rvRestaurants.layoutManager = LinearLayoutManager(this)
        binding.rvRestaurants.adapter = recyclerAdapter

        val resultsViewModelClass: ResultsViewModelClass =
            ViewModelProvider(this)[ResultsViewModelClass::class.java]
        resultsViewModelClass.getLiveDataObserver().observe(this) {
            if (((it.total) == 0)) {
                Log.d(TAG, "BlankResult, Error in getting list tostring ${it.toString()}")
                imageViewLost.visibility = View.VISIBLE
                Toast.makeText(this, "Error in getting list", Toast.LENGTH_SHORT).show()
            } else {
                Log.d(TAG, "NotBlankResult, $it.restaurants")
                recyclerAdapter.setRestaurantsList(it.restaurants)
                recyclerAdapter.notifyDataSetChanged()
            }
            Log.w(TAG, "END OF CALL,(it.total) ${it.total}")

        }
        resultsViewModelClass.makeAPICall(categoryTextInput, locationTextInput, restaurantNameTextInput)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}


