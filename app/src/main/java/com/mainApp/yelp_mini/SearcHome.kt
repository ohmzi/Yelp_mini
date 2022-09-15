package com.mainApp.yelp_mini

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.mainApp.yelp_mini.databinding.ActivitySearchHomeBinding


class SearchHome() : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySearchHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        var categoryTextInput: String
        var locationTextInput: String
        var restaurantNameTextInput: String

        super.onCreate(savedInstanceState)

        binding = ActivitySearchHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // finding the button
        val searchButton = findViewById<Button>(R.id.bnSearch)

        // finding the edit text
        val categoryInput = findViewById<EditText>(R.id.categoryInput)
        val locationInput = findViewById<EditText>(R.id.locationInput)
        val restaurantNameInput = findViewById<EditText>(R.id.restaurantNameInput)

        // Setting On Click Listener
        searchButton.setOnClickListener {

            // Getting the user input
            categoryTextInput = categoryInput.text.toString()
            locationTextInput = locationInput.text.toString()
            restaurantNameTextInput = restaurantNameInput.text.toString()



            // Showing the user input
         //   Toast.makeText(this, categoryTextInput, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)

            val extras = Bundle()
            extras.putString("categoryTextInput", categoryTextInput)
            extras.putString("locationTextInput", locationTextInput)
            extras.putString("restaurantNameTextInput", restaurantNameTextInput)
            Log.w("SearchHome", "$restaurantNameTextInput + $locationTextInput + $categoryTextInput")

            intent.putExtras(extras)
            startActivity(intent)

        }
    }
}





