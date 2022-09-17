package com.mainApp.yelp_mini

import android.annotation.SuppressLint
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class ValidatorTest {

    @SuppressLint("CheckResult")
    @Test
    fun whenInputIsValid() {
        val restaurantName = "Tim Hortons"
        val category = "Coffee"
        val location = "Lasalle"
        val result = Validator.validateInput(restaurantName, category, location)
        assertThat(result).isEqualTo(true)
    }

    @SuppressLint("CheckResult")
    @Test
    fun validateInputForLocation() {
        val location = ""
        val result = Validator.validateInputForLocation(location)
        assertThat(result).isEqualTo(false)
    }

}