package com.mainApp.yelp_mini.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mainApp.yelp_mini.databinding.ItemReviewBinding
import com.mainApp.yelp_mini.model.data.RestaurantReview
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "RestaurantsAdapter"

class DetailReviewAdapter(
    private val context: Context,
    private val reviews: List<RestaurantReview>,
) : RecyclerView.Adapter<DetailReviewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount() = reviews.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    inner class ViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(review: RestaurantReview) {

            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

            val dateFormat = SimpleDateFormat("EEE, M/d/yy 'at' h:mm a")
            var dateTime = review.timestamp
            try {
                dateTime = dateFormat.format(format.parse(review.timestamp) as Date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            with(binding) {
                tvReviewName.text = review.user.name
                rbReview.rating = review.rating.toFloat()
                tvReviewDate.text = dateTime
                tvReviewText.text = review.text
            }
        }
    }
}
