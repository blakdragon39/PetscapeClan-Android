package com.blakdragon.petscapeclan.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.blakdragon.petscapeclan.core.PetscapePrefs
import com.blakdragon.petscapeclan.databinding.FragmentHomeBinding
import com.blakdragon.petscapeclan.ui.BaseFragment
import com.blakdragon.petscapeclan.ui.MainActivity

class HomeFragment : BaseFragment<MainActivity>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (PetscapePrefs.user?.isAdmin) {
            true -> findNavController().navigate(HomeFragmentDirections.toAdminHome())
            else -> findNavController().navigate(HomeFragmentDirections.toUserHome())
        }
    }
}