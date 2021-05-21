package com.blakdragon.petscapeclan.ui.fragments.members

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.blakdragon.petscapeclan.R
import com.blakdragon.petscapeclan.core.network.NetworkInstance
import com.blakdragon.petscapeclan.databinding.FragmentAddClanMemberBinding
import com.blakdragon.petscapeclan.models.*
import com.blakdragon.petscapeclan.models.enums.AchievementType
import com.blakdragon.petscapeclan.models.enums.PetType
import com.blakdragon.petscapeclan.models.enums.Rank
import com.blakdragon.petscapeclan.ui.BaseFragment
import com.blakdragon.petscapeclan.ui.MainActivity
import com.blakdragon.petscapeclan.ui.fragments.RankPopup
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
        
        initPets()
        initAchievements()

        viewModel.addClanMemberResult.observe(viewLifecycleOwner, Observer { result ->
            if (!result.handled) {
                if (result.isSuccessful) {
                    findNavController().popBackStack()
                } else {
                    parentActivity.handleError(result.getUnhandledException())
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initPets() {
        binding.rvPets.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPets.adapter = SelectableAdapter(
            PetType.values().map { Pet(it) }.map { SelectableObject(it, getString(it.type.displayNameId)) })
            { pet, selected -> onPetSelected(pet as Pet, selected) }
    }

    private fun initAchievements() {
        binding.rvAchievements.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAchievements.adapter = SelectableAdapter(
            AchievementType.values().map { Achievement(it) }.map { SelectableObject(it, getString(it.type.labelId)) })
            { achievement, selected -> onAchievementSelected(achievement as Achievement, selected)}
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

    private fun onPetSelected(pet: Pet, selected: Boolean) {
        val currentPets = viewModel.pets.value?.toMutableList() ?: mutableListOf()
        if (selected) currentPets.add(pet) else currentPets.remove(pet)
        viewModel.pets.value = currentPets
    }

    private fun onAchievementSelected(achievement: Achievement, selected: Boolean) {
        val currentAchievements = viewModel.achievements.value?.toMutableList() ?: mutableListOf()
        if (selected) currentAchievements.add(achievement) else currentAchievements.remove(achievement)
        viewModel.achievements.value = currentAchievements
    }
}

class AddClanMemberViewModel : ViewModel() {

    val runescapeName = MutableLiveData("")
    val joinDate = MutableLiveData(LocalDate.now())
    val rank = MutableLiveData(Rank.Bronze)
    val pets = MutableLiveData<List<Pet>>()
    val achievements = MutableLiveData<List<Achievement>>()

    val joinDateString = MediatorLiveData<String>()
    val bossKc = MediatorLiveData<String>()

    val hiscoresLoading = MutableLiveData(false)
    val addClanMemberLoading = MutableLiveData(false)

    val addClanMemberResult = MutableLiveData<NetworkResult<ClanMember>>()

    private val wiseOldManPlayer = MediatorLiveData<WiseOldManPlayer>()

    private var hiscoresJob: Job? = null

    init {
        joinDateString.addSource(joinDate) { date -> joinDateString.value = date.toString("MMM dd, yyyy") }
        wiseOldManPlayer.addSource(runescapeName) { startHiscoresJob() }
        bossKc.addSource(wiseOldManPlayer) { player -> bossKc.value = player.totalBossKc().toString() }
    }

    fun addClanMember() = viewModelScope.launch {
        addClanMemberLoading.value = true

        try {
            val response = NetworkInstance.API.addClanMember(
                AddClanMemberRequest(
                    runescapeName = runescapeName.value!!,
                    rank = rank.value!!,
                    joinDate = joinDate.value!!,
                    pets = pets.value ?: emptyList(),
                    achievements = achievements.value ?: emptyList()
                )
            )

            addClanMemberResult.value = NetworkResult(data = response)
        } catch (e: Exception) {
            addClanMemberResult.value = NetworkResult(exception = e)
        }

        addClanMemberLoading.value = false
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