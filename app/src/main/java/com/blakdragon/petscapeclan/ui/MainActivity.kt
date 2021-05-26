package com.blakdragon.petscapeclan.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blakdragon.petscapeclan.R
import com.blakdragon.petscapeclan.core.PetscapePrefs
import com.blakdragon.petscapeclan.databinding.ActivityMainBinding


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.toolbar.binding.ivMenu.setOnClickListener { toggleDrawer() }

        initDrawer()
    }

    fun logout() {
        PetscapePrefs.user = null
        Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(this)
        }
    }

    private fun initDrawer() {
        val drawerToggle = object : ActionBarDrawerToggle(this, binding.drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                viewModel.drawerOpen.value = false
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                viewModel.drawerOpen.value = true
            }
        }

        binding.drawerLayout.addDrawerListener(drawerToggle)
    }

    fun toggleDrawer() {
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
