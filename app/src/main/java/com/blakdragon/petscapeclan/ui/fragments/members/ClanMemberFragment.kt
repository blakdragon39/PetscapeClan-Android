package com.blakdragon.petscapeclan.ui.fragments.members

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.blakdragon.petscapeclan.R
import com.blakdragon.petscapeclan.databinding.FragmentClanMemberBinding
import com.blakdragon.petscapeclan.models.ClanMember
import com.blakdragon.petscapeclan.ui.BaseFragment
import com.blakdragon.petscapeclan.ui.MainActivity
import com.blakdragon.petscapeclan.ui.theme.PetscapeTheme

class ClanMemberFragment : BaseFragment<MainActivity>() {

    private var _binding: FragmentClanMemberBinding? = null
    private val binding: FragmentClanMemberBinding
        get() = _binding!!

    private val viewModel: ClanMemberViewModel by viewModels()
    private val args: ClanMemberFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_clan_member, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clanMember.value = args.clanMember

        binding.ivEdit.setOnClickListener { findNavController().navigate(ClanMemberFragmentDirections.toEditClanMember(args.clanMember)) }

        binding.cvClanMember.setContent {
            PetscapeTheme {
                ClanMemberScreen(args.clanMember)
            }
        }
//        binding.cvPets.setContent { CardedList(args.clanMember.pets.map { it.label }) }
//        binding.cvAchievements.setContent { CardedList(args.clanMember.achievements.map { it.label }) }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

class ClanMemberViewModel : ViewModel() {

    val clanMember = MutableLiveData<ClanMember>()

    val bossKc = MediatorLiveData<Int>()
    val points = MediatorLiveData<Int>()
    val alts = MediatorLiveData<String>()
    val possibleRank = MediatorLiveData<String>()

    init {
        bossKc.addSource(clanMember) { bossKc.value = clanMember.value?.bossKc }
        points.addSource(clanMember) { points.value = clanMember.value?.points }
        alts.addSource(clanMember) { alts.value = clanMember.value?.alts?.joinToString("") { "• $it" } }
        possibleRank.addSource(clanMember) { possibleRank.value = clanMember.value?.possibleRank?.label }
    }
}