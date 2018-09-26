package io.rodrigo.agimarveltest.ui

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import io.rodrigo.agimarveltest.R

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {

    if (url != null) {
        Glide.with(view.context)
                .load(url.replace("http://", "https://", true))
                .into(view)
    } else {
        view.setImageDrawable(null)
    }
}

@BindingAdapter("visibilityObject")
fun checkVisibility(view: View, obj: Any?) {
    if (obj == null) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}

@BindingAdapter("visible")
fun booleanVisibility(view: View, visible: Boolean) {
    if (visible) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("placeHolderObject")
fun checkPlaceHolder(view: View, obj: Any?) {
    val context = view.context
    if (obj == null) {
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.placeholder))
    } else {
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
    }
}