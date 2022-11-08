package com.mainApp.yelp_mini.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.mainApp.yelp_mini.R
import com.mainApp.yelp_mini.databinding.FragmentRestaurantOverviewBinding
import com.mainApp.yelp_mini.model.data.YelpRestaurantDetail
import com.mainApp.yelp_mini.view.activity.DetailActivity
import java.util.*
private const val TAG = "OverviewFragment"

class OverviewFragment(
    private val context: DetailActivity,
    val reviews: YelpRestaurantDetail,
) : Fragment(R.layout.fragment_restaurant_overview) {

    private lateinit var binding: FragmentRestaurantOverviewBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRestaurantOverviewBinding.bind(view)
        Log.d(TAG, "Adding review by " + reviews.name)
        bindInfo(reviews)
    }

    private fun bindInfo(body: YelpRestaurantDetail) {
        var price = body.price
        if (price == null) {
            price = "$"
        }
/*
        Glide.with(this@DetailActivity).load(body.imageUrl).apply(
            RequestOptions().transforms(
                CenterCrop(), RoundedCorners(20)
            )).into(binding.imageViewBusinessDetail)
*/
        binding.tvName.text = body.name
        binding.ratingBar.rating = body.rating.toFloat()
        binding.tvNumReviews.text = "${body.numReviews} Reviews"
        binding.tvPrice.text = price
        binding.tvCategory.text = body.categories.joinToString { c -> c.title }
        binding.tvAddress.text = "Address: ${body.location.address}"
        binding.tvPhone.text = "Call: ${body.phone}"
        binding.tvTransactions.text =
            body.transactions.joinToString { t ->
                t.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                }
            }


    }
}