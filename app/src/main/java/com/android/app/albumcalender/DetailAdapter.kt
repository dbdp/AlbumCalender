package com.android.app.albumcalender

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.android.app.albumcalender.databinding.ItemBinding

class DetailAdapter : ListAdapter<DetailItem, DetailItemViewHolder>(DetailItemDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailItemViewHolder {
        return DetailItemViewHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holderDetail: DetailItemViewHolder, position: Int) {
        holderDetail.bind(currentList[position])
    }

    fun setData(uriList: List<DetailItem>) = submitList(uriList)
}

