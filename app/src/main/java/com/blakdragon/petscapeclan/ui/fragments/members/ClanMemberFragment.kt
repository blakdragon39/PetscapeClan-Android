package com.blakdragon.petscapeclan.ui.fragments.members

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.blakdragon.petscapeclan.R
import com.blakdragon.petscapeclan.databinding.FragmentComposeViewBinding
import com.blakdragon.petscapeclan.ui.BaseFragment
import com.blakdragon.petscapeclan.ui.MainActivity
import com.blakdragon.petscapeclan.ui.screens.members.ClanMemberScreen
import com.blakdragon.petscapeclan.ui.theme.PetscapeTheme

class ClanMemberFragment : BaseFragment<MainActivity>() {

    private var _binding: FragmentComposeViewBinding? = null
    private val binding: FragmentComposeViewBinding
        get() = _binding!!

    private val args: ClanMemberFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_compose_view, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView.setContent {
            PetscapeTheme {
                ClanMemberScreen(
                    clanMember = args.clanMember,
                    onEditClick = { findNavController().navigate(ClanMemberFragmentDirections.toEditClanMember(args.clanMember)) }
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
