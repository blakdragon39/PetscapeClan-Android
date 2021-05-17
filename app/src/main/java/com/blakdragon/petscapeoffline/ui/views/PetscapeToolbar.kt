package com.blakdragon.petscapeoffline.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import com.blakdragon.petscapeoffline.R
import com.blakdragon.petscapeoffline.databinding.PetscapeToolbarBinding


class PetscapeToolbar(c: Context, attrs: AttributeSet) : FrameLayout(c, attrs) {

    val binding = PetscapeToolbarBinding.inflate(LayoutInflater.from(c), this, true)
}

@BindingAdapter("drawerOpen")
fun setDrawerOpen(toolbar: PetscapeToolbar, drawerOpen: Boolean) {
    val drawableId = if (drawerOpen) R.drawable.back else R.drawable.menu
    val colorId = if (drawerOpen) R.color.primary else R.color.onPrimary
    val drawable = AppCompatResources.getDrawable(toolbar.context, drawableId)!!
    drawable.mutate()

    DrawableCompat.setTint(drawable, ContextCompat.getColor(toolbar.context, colorId))

    toolbar.binding.ivMenu.setImageDrawable(drawable)
}
