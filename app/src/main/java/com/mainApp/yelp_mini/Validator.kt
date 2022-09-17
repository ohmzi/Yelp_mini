package com.mainApp.yelp_mini

object Validator {
    fun validateInput(restaurantName: String, category: String, location: String): Boolean {
        return (restaurantName.isNotBlank() || category.isNotBlank() || location.isNotBlank())
    }

    fun validateInputForLocation(location: String): Boolean {
        return (location.isNotBlank())
    }
}