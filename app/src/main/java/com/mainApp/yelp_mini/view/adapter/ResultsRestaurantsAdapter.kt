package com.mainApp.yelp_mini.view.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mainApp.yelp_mini.databinding.ItemRestaurantBinding
import com.mainApp.yelp_mini.model.data.YelpSearchResult
import com.mainApp.yelp_mini.view.activity.DetailActivity
import kotlin.math.roundToInt


class ResultsRestaurantsAdapter(private val activity: Activity) :
    RecyclerView.Adapter<ResultsRestaurantsAdapter.ViewHolder>() {


    private var restaurantsList: List<YelpSearchResult.YelpRestaurant>? = null

    fun setRestaurantsList(restaurants: List<YelpSearchResult.YelpRestaurant>?) {
        this.restaurantsList = restaurants
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ResultsRestaurantsAdapter.ViewHolder {
        val binding = ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return restaurantsList?.size ?: 0
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        restaurantsList?.get(position)?.let { holder.bind((it), activity) }
        holder.itemView.setOnClickListener(View.OnClickListener {
            val restaurant = restaurantsList?.get(position)
            if (restaurant != null) {
                openNewActivity(restaurant, holder.itemView.context)
            }
        })
    }

    private fun openNewActivity(restaurant: YelpSearchResult.YelpRestaurant, context: Context) {
        context.startActivity(Intent(context, DetailActivity::class.java)
            .putExtra("restaurantID", restaurant.id))
    }

    inner class ViewHolder(private val binding: ItemRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(restaurant: YelpSearchResult.YelpRestaurant, activity: Activity) {
            var price = restaurant.price
            if (price == null) {
                price = "$"
            }
            with(binding)
            {
                tvName.text = restaurant.name
                ratingBar.rating = restaurant.rating.toFloat()
                tvNumReviews.text = "${restaurant.numReviews} Reviews"
                tvAddress.text = restaurant.location.address
                tvCategory.text = restaurant.categories[0].title
                tvDistance.text = restaurant.distance.roundToInt().toString() + " km"
                tvPrice.text = price
            }
            Glide.with(activity).load(restaurant.imageUrl).apply(
                RequestOptions().transforms(
                    CenterCrop(), RoundedCorners(20)
                )
            ).into(binding.imageView)
        }
    }
}
