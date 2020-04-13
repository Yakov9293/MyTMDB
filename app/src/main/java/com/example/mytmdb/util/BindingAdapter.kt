package com.example.mytmdb.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mytmdb.util.Constants.TMDB_BASE_IMAGE_URL
import com.timqi.sectorprogressview.ColorfulRingProgressView

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(TMDB_BASE_IMAGE_URL + imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@BindingAdapter("app:percent")
fun bindPercentForRingProgressView(view: ColorfulRingProgressView, percent: Double?) {
    percent?.let {
        view.percent = percent.toFloat()
    }
}