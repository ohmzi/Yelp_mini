package com.mainApp.yelp_mini.view.adapter

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
import com.mainApp.yelp_mini.R
import com.mainApp.yelp_mini.model.data.YelpSearchResult
import com.mainApp.yelp_mini.view.activity.DetailActivity
import kotlinx.android.synthetic.main.item_restaurant.view.*
import kotlin.math.roundToInt


class ResultsRestaurantsAdapter(private val activity: Activity) :
    RecyclerView.Adapter<ResultsRestaurantsAdapter.ViewHolder>() {


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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(restaurant: YelpSearchResult.YelpRestaurant, activity: Activity) {
            var price = restaurant.price
            if (price == null) {
                price = "$"
            }
            itemView.tvName.text = restaurant.name
            itemView.ratingBar.rating = restaurant.rating.toFloat()
            itemView.tvNumReviews.text = "${restaurant.numReviews} Reviews"
            itemView.tvAddress.text = restaurant.location.address
            itemView.tvCategory.text = restaurant.categories[0].title
            itemView.tvDistance.text = restaurant.distance.roundToInt().toString() + " km"
            itemView.tvPrice.text = price
            Glide.with(activity).load(restaurant.imageUrl).apply(
                RequestOptions().transforms(
                    CenterCrop(), RoundedCorners(20)
                )
            ).into(itemView.imageView)
        }
    }
}
