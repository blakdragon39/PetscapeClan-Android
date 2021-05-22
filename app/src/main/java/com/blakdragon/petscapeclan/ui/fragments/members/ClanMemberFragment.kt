package com.blakdragon.petscapeclan.ui.fragments.members

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.blakdragon.petscapeclan.R
import com.blakdragon.petscapeclan.databinding.FragmentClanMemberBinding
import com.blakdragon.petscapeclan.models.ClanMember
import com.blakdragon.petscapeclan.ui.BaseFragment
import com.blakdragon.petscapeclan.ui.MainActivity
import com.blakdragon.petscapeclan.ui.fragments.TextAdapter

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

        binding.rvPets.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPets.adapter = TextAdapter(args.clanMember.pets.map { getString(it.type.displayNameId) })

        binding.rvAchievements.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAchievements.adapter = TextAdapter(args.clanMember.achievements.map { getString(it.type.labelId) })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

class ClanMemberViewModel : ViewModel() {

    val clanMember = MutableLiveData<ClanMember>()

    val bossKc = MediatorLiveData<Int>()

    init {
        bossKc.addSource(clanMember) { bossKc.value = clanMember.value?.bossKc }
    }

}