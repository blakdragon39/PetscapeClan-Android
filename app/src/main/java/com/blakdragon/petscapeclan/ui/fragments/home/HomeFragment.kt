package com.blakdragon.petscapeclan.ui.fragments.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.blakdragon.petscapeclan.R
import com.blakdragon.petscapeclan.core.network.NetworkInstance
import com.blakdragon.petscapeclan.databinding.FragmentHomeBinding
import com.blakdragon.petscapeclan.models.ClanMember
import com.blakdragon.petscapeclan.ui.BaseFragment
import com.blakdragon.petscapeclan.ui.MainActivity
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate

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
        binding.ivFilters.setOnClickListener { FilterPopup().show(it, viewModel) }
        binding.ivSort.setOnClickListener { SortPopUp().show(it, viewModel) }

        adapter = ClanMemberAdapter(this::onClanMemberClick, this::onClanMemberLongClick)
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
        viewModel.exception.observe(viewLifecycleOwner) { parentActivity.handleError(it) }
        viewModel.filteredClanMembers.observe(viewLifecycleOwner) { if (it != null) adapter.setClanMembers(it) }

        viewModel.filterNotSeenToday.observe(viewLifecycleOwner) { Timber.i("LAST SEEN $it") }
    }

    private fun onClanMemberClick(clanMember: ClanMember) {
        findNavController().navigate(HomeFragmentDirections.toClanMember(clanMember))
    }

    private fun onClanMemberLongClick(clanMember: ClanMember) {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.home_update_last_seen)
            .setPositiveButton(android.R.string.ok) { _, _ -> viewModel.updateLastSeenClanMember(clanMember) }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }
}
