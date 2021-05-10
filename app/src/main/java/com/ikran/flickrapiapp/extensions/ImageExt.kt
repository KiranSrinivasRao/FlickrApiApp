package com.ikran.flickrapiapp.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ikran.flickrapiapp.R


fun ImageView.load(url: String, placeholder: Int = R.drawable.ic_image_ref) {

    Glide.with(context)
        .load(url)
        .centerCrop()
        .placeholder(placeholder)
        .error(placeholder)
        .into(this)

}