package io.rodrigo.agimarveltest.ui

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {

    url?.let {
        Glide.with(view.context)
                .load(url)
                .into(view)
    }
}