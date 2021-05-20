package com.blakdragon.petscapeclan.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.blakdragon.petscapeclan.databinding.DrawerBinding
import com.blakdragon.petscapeclan.ui.MainActivity

class Drawer(c: Context, attrs: AttributeSet) : FrameLayout(c, attrs) {

    val binding = DrawerBinding.inflate(LayoutInflater.from(c), this, true)

    init {
        binding.tvLogout.setOnClickListener { (context as MainActivity).logout() }
    }
}