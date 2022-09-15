package com.mainApp.yelp_mini

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class SliderAdapter(val context: Context, private val photos: List<String>) {

    fun getCount() = photos.size

    fun onCreateViewHolder(parent: ViewGroup?): SliderAdapterVH {
        return SliderAdapterVH(
            LayoutInflater.from(context).inflate(R.layout.item_image_slider, parent, false)
        )
    }

    fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val sliderItem = photos[position]
        viewHolder.bind(sliderItem)
    }

    inner class SliderAdapterVH(itemView: View) {
        fun bind(photo: String) {
        }
    }
}
