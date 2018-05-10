package io.rodrigo.agimarveltest.ui

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener

@BindingAdapter("visible")
fun booleanVisibility(view: View, visible: Boolean) {
    if (visible) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    loadImage(view, url, null)
}

@BindingAdapter("imageUrl", "thumbnail")
fun loadImage(view: ImageView, url: String?, thumbnail: String?) {
    loadImage(view, url, thumbnail, null)
}

@BindingAdapter("imageUrl", "thumbnail", "thumbnailListener")
fun loadImage(view: ImageView, url: String?, thumbnail: String?, thumbnailListener: RequestListener<Drawable>?) {

    val thumbnailRequest = thumbnail?.let {
        Glide.with(view.context)
                .load(thumbnail)
                .listener(thumbnailListener)
    }

    url?.let {
        Glide.with(view.context)
                .load(url)
                .thumbnail(thumbnailRequest)
                .into(view)
    }
}