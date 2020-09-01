package com.android.app.albumcalender


import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("bind:setDataToRecyclerView")
fun setData(recyclerView: RecyclerView, uris: List<DetailItem>?) {
    uris?.let { (recyclerView.adapter as? DetailAdapter)?.setData(it) }
}