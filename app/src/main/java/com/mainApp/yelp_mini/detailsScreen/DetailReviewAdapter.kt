package com.mainApp.yelp_mini.detailsScreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mainApp.yelp_mini.R
import com.mainApp.yelp_mini.data.RestaurantReview
import kotlinx.android.synthetic.main.item_review.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "RestaurantsAdapter"

class DetailReviewAdapter(
    private val context: Context,
    private val reviews: List<RestaurantReview>,
) : RecyclerView.Adapter<DetailReviewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_review, parent, false))
    }

    override fun getItemCount() = reviews.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(review: RestaurantReview) {
            itemView.tvReviewName.text = review.user.name
            itemView.rbReview.rating = review.rating.toFloat()

            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            var date: Date = Date()
            try {
                date = format.parse(review.timestamp)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            val dateFormat = SimpleDateFormat("EEE, M/d/yy 'at' h:mm a")
            var dateTime = review.timestamp
            try {
                dateTime = dateFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            itemView.tvReviewDate.text = dateTime
            itemView.tvReviewText.text = review.text
        }
    }
}
