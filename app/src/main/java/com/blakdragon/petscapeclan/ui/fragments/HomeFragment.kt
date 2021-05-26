package com.blakdragon.petscapeclan.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.blakdragon.petscapeclan.R
import com.blakdragon.petscapeclan.core.network.NetworkInstance
import com.blakdragon.petscapeclan.databinding.FragmentHomeBinding
import com.blakdragon.petscapeclan.models.ClanMember
import com.blakdragon.petscapeclan.models.NetworkResult
import com.blakdragon.petscapeclan.ui.BaseFragment
import com.blakdragon.petscapeclan.ui.MainActivity
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<MainActivity>() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var adapter: ClanMemberAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeRefreshClanMembers.setOnRefreshListener { viewModel.getClanMembers() }

        adapter = ClanMemberAdapter(this::onClanMemberClick)
        binding.rvClanMembers.layoutManager = LinearLayoutManager(requireContext())
        binding.rvClanMembers.adapter = adapter

        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getClanMembers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        viewModel.clanMembersResult.observe(viewLifecycleOwner) { result ->
            if (!result.handled) {
                if (!result.isSuccessful) {
                    parentActivity.handleError(result.getUnhandledException())
                }
            }
        }

        viewModel.clanMembers.observe(viewLifecycleOwner) { clanMembers ->
            adapter.setClanMembers(clanMembers)
        }
    }

    private fun onClanMemberClick(clanMember: ClanMember) {
        findNavController().navigate(HomeFragmentDirections.toClanMember(clanMember))
    }
}

class HomeViewModel : ViewModel() {

    val clanMembersLoading = MutableLiveData(false)
    val clanMembersResult = MutableLiveData<NetworkResult<List<ClanMember>>>()

    val clanMembers = MutableLiveData<List<ClanMember>>()

    fun getClanMembers() = viewModelScope.launch {
        clanMembersLoading.value = true

        try {
            val response = NetworkInstance.API.getClanMembers()
            clanMembersResult.value = NetworkResult(data = response)

            clanMembers.value = response
                .sortedBy { it.runescapeName.lowercase() }
                .sortedBy { it.rank.ordinal }
        } catch (e: Exception) {
            clanMembersResult.value = NetworkResult(exception  = e)
        }

        clanMembersLoading.value = false
    }
}