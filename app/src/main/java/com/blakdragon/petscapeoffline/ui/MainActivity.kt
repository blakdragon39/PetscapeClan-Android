package com.blakdragon.petscapeoffline.ui

import android.os.Bundle
import androidx.core.view.GravityCompat
import com.blakdragon.petscapeoffline.databinding.ActivityMainBinding


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.binding.ivMenu.setOnClickListener { toggleDrawer() }
    }

    private fun toggleDrawer() {
        when (binding.drawerLayout.isDrawerOpen(binding.drawer)) {
            true -> binding.drawerLayout.closeDrawer(GravityCompat.START)
            false -> binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}