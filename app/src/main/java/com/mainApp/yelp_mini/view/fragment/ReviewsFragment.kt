package com.mainApp.yelp_mini.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mainApp.yelp_mini.R
import com.mainApp.yelp_mini.databinding.FragmentRestaurantReviewsBinding
import com.mainApp.yelp_mini.model.data.RestaurantReview
import com.mainApp.yelp_mini.view.activity.DetailActivity
import com.mainApp.yelp_mini.view.adapter.DetailReviewAdapter

class ReviewsFragment(
    private val context: DetailActivity,
    val reviews: List<RestaurantReview>,
) : Fragment(R.layout.fragment_restaurant_reviews) {

    private lateinit var binding: FragmentRestaurantReviewsBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRestaurantReviewsBinding.bind(view)
        bindReviews()
    }

    private fun bindReviews() {
        binding.rvReviews.adapter = DetailReviewAdapter(context, reviews)
        binding.rvReviews.layoutManager = LinearLayoutManager(context)
        binding.rvReviews.addItemDecoration(
            DividerItemDecoration(
                binding.rvReviews.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

}