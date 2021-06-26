package com.blakdragon.petscapeclan.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter("android:src")
fun setImageResource(imageView: ImageView, @DrawableRes resourceId: Int) {
    imageView.setImageResource(resourceId)
}