package io.rodrigo.agimarveltest.ui

import android.databinding.BindingAdapter
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import io.rodrigo.agimarveltest.R

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {

    url?.let {
        Glide.with(view.context)
                .load(url)
                .into(view)
    }
}

@BindingAdapter("placeHolderObject")
fun checkPlaceHolder(view: View, obj: Any?) {
    val context = view.context
    if (obj == null) {
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.placeholder))
    } else {
        view.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
    }
}