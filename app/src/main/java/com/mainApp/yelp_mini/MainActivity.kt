package com.mainApp.yelp_mini


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mainApp.yelp_mini.adapter.RestaurantsAdapter
import com.mainApp.yelp_mini.databinding.ActivityMainBinding
import com.mainApp.yelp_mini.viewModel.ViewModelClass

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val recyclerAdapter by lazy { RestaurantsAdapter(this) }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.rvRestaurants.layoutManager = LinearLayoutManager(this)
        binding.rvRestaurants.adapter = recyclerAdapter
    }

    private fun initViewModel() {
        val viewModelClass: ViewModelClass =
            ViewModelProvider(this).get(ViewModelClass::class.java)
        viewModelClass.getLiveDataObserver().observe(this) {
            if (it != null) {
                recyclerAdapter.setRestaurantsList(it.restaurants)
                recyclerAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
        }
        viewModelClass.makeAPICall()
    }

}