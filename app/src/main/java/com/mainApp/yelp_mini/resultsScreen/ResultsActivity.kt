package com.mainApp.yelp_mini.resultsScreen


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mainApp.yelp_mini.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding
private lateinit var categoryTextInput: String
private lateinit var locationTextInput: String
private lateinit var restaurantNameTextInput: String

class ResultsActivity : AppCompatActivity() {
    private val recyclerAdapter by lazy { ResultsRestaurantsAdapter(this) }

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

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.rvRestaurants.layoutManager = LinearLayoutManager(this)
        binding.rvRestaurants.adapter = recyclerAdapter

        val resultsViewModelClass: ResultsViewModelClass =
            ViewModelProvider(this)[ResultsViewModelClass::class.java]
        resultsViewModelClass.getLiveDataObserver().observe(this) {
            if (it.restaurants.isEmpty()) {
                Log.d("Blankresult", "Error in getting list")
                Toast.makeText(this, "Error in getting list", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("BlankresultNot", "$it.restaurants")

                recyclerAdapter.setRestaurantsList(it.restaurants)
                recyclerAdapter.notifyDataSetChanged()
            }
        }
        resultsViewModelClass.makeAPICall(categoryTextInput, locationTextInput, restaurantNameTextInput)


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}


