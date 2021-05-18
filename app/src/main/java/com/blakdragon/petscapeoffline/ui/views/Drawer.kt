package com.blakdragon.petscapeoffline.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.blakdragon.petscapeoffline.databinding.DrawerBinding
import com.blakdragon.petscapeoffline.ui.MainActivity

class Drawer(c: Context, attrs: AttributeSet) : FrameLayout(c, attrs) {

    val binding = DrawerBinding.inflate(LayoutInflater.from(c), this, true)

    init {
        binding.tvLogout.setOnClickListener { (context as MainActivity).logout() }
    }
}