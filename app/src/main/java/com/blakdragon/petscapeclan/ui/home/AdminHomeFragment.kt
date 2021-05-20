package com.blakdragon.petscapeclan.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blakdragon.petscapeclan.R
import com.blakdragon.petscapeclan.core.network.NetworkInstance
import com.blakdragon.petscapeclan.databinding.FragmentAdminHomeBinding
import com.blakdragon.petscapeclan.models.ClanMember
import com.blakdragon.petscapeclan.models.NetworkResult
import com.blakdragon.petscapeclan.ui.BaseFragment
import com.blakdragon.petscapeclan.ui.MainActivity
import kotlinx.coroutines.launch

class AdminHomeFragment : BaseFragment<MainActivity>() {

    private var _binding: FragmentAdminHomeBinding? = null
    private val binding: FragmentAdminHomeBinding
        get() = _binding!!

    private val viewModel: AdminHomeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_home, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bAddClanMember.setOnClickListener { /* todo */ }

        viewModel.clanMembersResult.observe(viewLifecycleOwner) { result ->
            if (!result.handled) {
                if (result.isSuccessful) {
                    initClanMembers(result.getUnhandledData())
                } else {
                    parentActivity.handleError(result.getUnhandledException())
                }
            }
        }

        viewModel.getClanMembers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initClanMembers(clanMembers: List<ClanMember>) {
        //todo
    }
}

class AdminHomeViewModel : ViewModel() {

    val clanMembersResult = MutableLiveData<NetworkResult<List<ClanMember>>>()

    fun getClanMembers() = viewModelScope.launch {
        try {
            val response = NetworkInstance.API.getClanMembers()
            clanMembersResult.value = NetworkResult(data = response)
        } catch (e: Exception) {
            clanMembersResult.value = NetworkResult(exception  = e)
        }
    }
}