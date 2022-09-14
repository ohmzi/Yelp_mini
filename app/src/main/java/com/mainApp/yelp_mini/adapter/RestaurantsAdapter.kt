package com.mainApp.yelp_mini.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mainApp.yelp_mini.R
import com.mainApp.yelp_mini.data.YelpSearchResult
import kotlinx.android.synthetic.main.item_restaurant.view.*
import kotlin.math.roundToInt

class RestaurantsAdapter(val activity: Activity) :
    RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>() {


    private var restaurantsList: List<YelpSearchResult.YelpRestaurant>? = null

    fun setRestaurantsList(restaurants: List<YelpSearchResult.YelpRestaurant>?) {
        this.restaurantsList = restaurants
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(activity).inflate(R.layout.item_restaurant, parent, false)
        )
    }


    override fun getItemCount(): Int {
        return if (restaurantsList == null) 0
        else restaurantsList?.size!!
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(restaurantsList?.get(position)!!, activity)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(restaurant: YelpSearchResult.YelpRestaurant, activity: Activity) {
            itemView.tvName.text = restaurant.name
            itemView.ratingBar.rating = restaurant.rating.toFloat()
            itemView.tvNumReviews.text = "${restaurant.numReviews.toString()} Reviews"
            itemView.tvAddress.text = restaurant.location.address
            itemView.tvCategory.text = restaurant.categories[0].title
            itemView.tvDistance.text = restaurant.distance.roundToInt().toString() + " km"
            itemView.tvPrice.text = restaurant.price
            Glide.with(activity).load(restaurant.imageUrl).apply(
                RequestOptions().transforms(
                    CenterCrop(), RoundedCorners(20)
                )
            ).into(itemView.imageView)
        }
    }
}
