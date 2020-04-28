package com.example.mytmdb.util

import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import at.grabner.circleprogress.CircleProgressView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mytmdb.R
import com.example.mytmdb.util.Constants.TMDB_BASE_IMAGE_URL
import com.timqi.sectorprogressview.ColorfulRingProgressView

@BindingAdapter("posterFromUrl")
fun bindPosterFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(TMDB_BASE_IMAGE_URL + imageUrl)
            .error(R.drawable.empty_poster)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
    else{
        view.setImageResource(R.drawable.empty_poster)
    }
}

@BindingAdapter("backdropFromUrl")
fun bindBackdropFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(TMDB_BASE_IMAGE_URL + imageUrl)
            .error(R.drawable.empty_backdrop)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
    else{
        view.setImageResource(R.drawable.empty_backdrop)
    }
}

@BindingAdapter("app:cpv_value")
fun bindPercentForRingProgressView(view: CircleProgressView, percent: Double?) {
    percent?.let {
        view.setValue(percent.toFloat())
        view.setTextTypeface(Typeface.DEFAULT)
    }
}

@BindingConversion
fun convertBooleanToVisible(visible: Boolean): Int {
    return if (visible) View.VISIBLE else View.GONE
}