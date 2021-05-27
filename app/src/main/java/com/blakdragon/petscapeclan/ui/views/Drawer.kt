package com.blakdragon.petscapeclan.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.blakdragon.petscapeclan.R
import com.blakdragon.petscapeclan.databinding.DrawerBinding
import com.blakdragon.petscapeclan.ui.MainActivity


class Drawer(c: Context, attrs: AttributeSet) : FrameLayout(c, attrs) {

    private val binding = DrawerBinding.inflate(LayoutInflater.from(c), this, true)

    private val activity: MainActivity
        get() = (context as MainActivity)

    private val navController: NavController
        get() = activity.findNavController(R.id.nav_host_fragment)

    init {
        binding.tvLogout.setOnClickListener { activity.logout() }

        binding.tvAddNewClanMember.setOnClickListener {
            navController.navigate(R.id.to_addClanMember)
            activity.toggleDrawer()
        }
    }
}