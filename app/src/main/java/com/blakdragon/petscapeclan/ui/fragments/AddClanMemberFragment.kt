package com.blakdragon.petscapeclan.ui.fragments

import android.app.DatePickerDialog
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
import com.blakdragon.petscapeclan.R
import com.blakdragon.petscapeclan.core.network.NetworkInstance
import com.blakdragon.petscapeclan.databinding.FragmentAddClanMemberBinding
import com.blakdragon.petscapeclan.models.WiseOldManPlayer
import com.blakdragon.petscapeclan.models.enums.Rank
import com.blakdragon.petscapeclan.ui.BaseFragment
import com.blakdragon.petscapeclan.ui.MainActivity
import com.blakdragon.petscapeclan.utils.toString
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate

class AddClanMemberFragment: BaseFragment<MainActivity>() {

    private var _binding: FragmentAddClanMemberBinding? = null
    private val binding: FragmentAddClanMemberBinding
        get() = _binding!!

    private val viewModel: AddClanMemberViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_clan_member, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bJoinDate.setOnClickListener { pickDate() }
        binding.ivRank.setOnClickListener { pickRank() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun pickDate() {
        val currentDate = viewModel.joinDate.value ?: LocalDate.now()

        DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            viewModel.joinDate.value = LocalDate.of(year, month + 1, dayOfMonth)
        }, currentDate.year, currentDate.monthValue - 1, currentDate.dayOfMonth).show()
    }

    private fun pickRank() {
        val popup = RankPopup()
        popup.show(binding.ivRank) { rank ->
            viewModel.rank.value = rank
            popup.dismiss()
        }
    }
}

class AddClanMemberViewModel : ViewModel() {

    val runescapeName = MutableLiveData("")
    val joinDate = MutableLiveData(LocalDate.now())
    val rank = MutableLiveData(Rank.Bronze)

    val joinDateString = MediatorLiveData<String>()
    val bossKc = MediatorLiveData<String>()

    val hiscoresLoading = MutableLiveData(false)

    private val wiseOldManPlayer = MediatorLiveData<WiseOldManPlayer>()

    private var hiscoresJob: Job? = null

    init {
        joinDateString.addSource(joinDate) { date -> joinDateString.value = date.toString("MMM dd, yyyy") }
        wiseOldManPlayer.addSource(runescapeName) { startHiscoresJob() }
        bossKc.addSource(wiseOldManPlayer) { player -> bossKc.value = player.totalBossKc().toString() }
    }

    private fun startHiscoresJob() = viewModelScope.launch {
        hiscoresJob?.cancel()
        delay(2000)

        if (runescapeName.value.isNullOrEmpty().not()) {
            hiscoresJob = loadHiScores()
        }
    }

    private fun loadHiScores() = viewModelScope.launch {
        hiscoresLoading.value = true

        try {
            wiseOldManPlayer.value = NetworkInstance.wiseOldManAPI.getPlayer(runescapeName.value!!) //todo check age
        } catch (e: CancellationException) {
        } catch (e: Exception) {
            Timber.i(e)
        }

        hiscoresLoading.value = false
    }
}