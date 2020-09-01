package com.android.app.albumcalender

import androidx.lifecycle.ViewModel

class DetailViewModel(model: DetailModel) : ViewModel() {
    val imageUrisLiveData = model.imageUrisLiveData
}