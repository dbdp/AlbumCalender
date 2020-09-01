package com.android.app.albumcalender

import androidx.recyclerview.widget.DiffUtil

class DetailItemDiffCallBack : DiffUtil.ItemCallback<DetailItem>() {
    override fun areItemsTheSame(oldDetailItem: DetailItem, newDetailItem: DetailItem): Boolean {
        return oldDetailItem == newDetailItem
    }

    override fun areContentsTheSame(oldDetailItem: DetailItem, newDetailItem: DetailItem): Boolean {
        return oldDetailItem.equals(newDetailItem)
    }

}