package com.blakdragon.petscapeoffline.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blakdragon.petscapeoffline.R
import com.blakdragon.petscapeoffline.databinding.ActivityMainBinding


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.toolbar.binding.ivMenu.setOnClickListener { toggleDrawer() }
    }

    private fun toggleDrawer() {
        viewModel.drawerOpen.value = viewModel.drawerOpen.value?.not()

        when (binding.drawerLayout.isDrawerOpen(binding.drawer)) {
            true -> binding.drawerLayout.closeDrawer(GravityCompat.START)
            false -> binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}

class MainViewModel : ViewModel() {
    val drawerOpen = MutableLiveData(false)
}