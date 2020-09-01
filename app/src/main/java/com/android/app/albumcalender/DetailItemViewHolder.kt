package com.android.app.albumcalender

import androidx.recyclerview.widget.RecyclerView
import com.android.app.albumcalender.databinding.ItemBinding
import com.bumptech.glide.Glide


class DetailItemViewHolder(private val binding: ItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(detailItem: DetailItem) {
        binding.run {
            Glide.with(binding.root.context).load(detailItem.uri).into(image)
            text.text = detailItem.latitude + "-" + detailItem.longitude
            image.setOnClickListener {

            }
        }
    }
}