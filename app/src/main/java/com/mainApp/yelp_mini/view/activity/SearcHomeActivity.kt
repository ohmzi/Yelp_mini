package com.mainApp.yelp_mini.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.mainApp.yelp_mini.R
import com.mainApp.yelp_mini.databinding.ActivitySearchHomeBinding


class SearchHome : AppCompatActivity() {
    private lateinit var binding: ActivitySearchHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {

         lateinit var categoryTextInput : String
         lateinit var locationTextInput : String
         lateinit var restaurantNameTextInput : String

        super.onCreate(savedInstanceState)


        binding = ActivitySearchHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val searchButton = binding.bnSearch

        searchButton.setOnClickListener {

            with(binding){
                categoryTextInput = categoryInput.text.toString()
                locationTextInput = locationInput.text.toString()
                restaurantNameTextInput = restaurantNameInput.text.toString()
            }

            val intent = Intent(this, ResultsActivity::class.java)

            val extras = Bundle()
            extras.putString("categoryTextInput", categoryTextInput)
            extras.putString("locationTextInput", locationTextInput)
            extras.putString("restaurantNameTextInput", restaurantNameTextInput)
            Log.w("SearchHome",
                "$restaurantNameTextInput + $locationTextInput + $categoryTextInput")

            intent.putExtras(extras)

            if (locationTextInput.equals("")) {
                binding.locationInput.error = "Required"
                binding.locationInput.hint =
                    "Please Enter Address, City or Postal Code"
            } else {
                startActivity(intent)
            }

        }
    }
}





