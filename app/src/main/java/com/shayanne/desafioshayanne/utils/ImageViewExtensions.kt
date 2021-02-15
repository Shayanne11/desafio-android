package com.shayanne.desafioshayanne.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadImageUrl(url: String) {
    Picasso.get()
        .load(url)
        .into(this)
}
