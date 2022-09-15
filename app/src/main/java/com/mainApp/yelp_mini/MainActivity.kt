package com.mainApp.yelp_mini


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mainApp.yelp_mini.adapter.RestaurantsAdapter
import com.mainApp.yelp_mini.databinding.ActivityMainBinding
import com.mainApp.yelp_mini.viewModel.ViewModelClass
import kotlinx.android.synthetic.main.activity_main.*

private lateinit var binding: ActivityMainBinding
private lateinit var categoryTextInput: String
private lateinit var locationTextInput: String
private lateinit var restaurantNameTextInput: String

class MainActivity : AppCompatActivity() {
    private val recyclerAdapter by lazy { RestaurantsAdapter(this) }

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

        val viewModelClass: ViewModelClass =
            ViewModelProvider(this)[ViewModelClass::class.java]
        viewModelClass.getLiveDataObserver().observe(this) {
            if (it.restaurants.isEmpty()) {
                Log.d("Blankresult", "Error in getting list")
                Toast.makeText(this, "Error in getting list", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("BlankresultNot", "$it.restaurants")

                recyclerAdapter.setRestaurantsList(it.restaurants)
                recyclerAdapter.notifyDataSetChanged()
            }
        }
        viewModelClass.makeAPICall(categoryTextInput, locationTextInput, restaurantNameTextInput)


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}


