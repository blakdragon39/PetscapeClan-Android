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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.blakdragon.petscapeclan.R
import com.blakdragon.petscapeclan.core.network.NetworkInstance
import com.blakdragon.petscapeclan.databinding.FragmentAddEditClanMemberBinding
import com.blakdragon.petscapeclan.models.*
import com.blakdragon.petscapeclan.models.enums.AchievementType
import com.blakdragon.petscapeclan.models.enums.PetType
import com.blakdragon.petscapeclan.models.enums.Rank
import com.blakdragon.petscapeclan.ui.BaseFragment
import com.blakdragon.petscapeclan.ui.MainActivity
import com.blakdragon.petscapeclan.ui.fragments.RankPopup
import com.blakdragon.petscapeclan.utils.toString
import kotlinx.coroutines.launch
import java.time.LocalDate

class AddEditClanMemberFragment: BaseFragment<MainActivity>() {

    private var _binding: FragmentAddEditClanMemberBinding? = null
    private val binding: FragmentAddEditClanMemberBinding
        get() = _binding!!

    private val viewModel: AddEditClanMemberViewModel by viewModels()
    private val args: AddEditClanMemberFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_edit_clan_member, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bJoinDate.setOnClickListener { pickDate() }
        binding.ivRank.setOnClickListener { pickRank() }

        initModel()
        initPets()
        initAchievements()

        viewModel.addClanMemberResult.observe(viewLifecycleOwner, Observer { result ->
            if (!result.handled) {
                if (result.isSuccessful) {
                    findNavController().navigate(AddEditClanMemberFragmentDirections.toClanMember(result.getUnhandledData()))
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

    private fun initModel() {
        args.clanMember?.let { clanMember ->
            viewModel.clanMember.value = clanMember
            viewModel.runescapeName.value = clanMember.runescapeName
            viewModel.joinDate.value = clanMember.joinDate
            viewModel.rank.value = clanMember.rank
            viewModel.pets.value = clanMember.pets
            viewModel.achievements.value = clanMember.achievements
        }
    }

    private fun initPets() {
        val selectables = PetType.values()
            .map { Pet(it) }
            .map { pet -> SelectableObject(pet, getString(pet.type.displayNameId), isSelected = args.clanMember?.pets?.contains(pet) == true) }

        binding.rvPets.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPets.adapter = SelectableAdapter(selectables, this::onPetSelected)
    }

    private fun initAchievements() {
        val selectables = AchievementType.values()
            .map { Achievement(it) }
            .map { achievement -> SelectableObject(achievement, getString(achievement.type.labelId), isSelected = args.clanMember?.achievements?.contains(achievement) == true) }
        binding.rvAchievements.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAchievements.adapter = SelectableAdapter(selectables, this::onAchievementSelected)
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

class AddEditClanMemberViewModel : ViewModel() {

    val clanMember = MutableLiveData<ClanMember>()

    val runescapeName = MutableLiveData<String>("")
    val joinDate = MutableLiveData<LocalDate>(LocalDate.now())
    val rank = MutableLiveData<Rank>(Rank.Bronze)
    val pets = MutableLiveData<List<Pet>>()
    val achievements = MutableLiveData<List<Achievement>>()

    val joinDateString = MediatorLiveData<String>()

    val addClanMemberLoading = MutableLiveData(false)

    val addClanMemberResult = MutableLiveData<NetworkResult<ClanMember>>()

    init {
        joinDateString.addSource(joinDate) { date -> joinDateString.value = date.toString("MMM dd, yyyy") }
    }

    fun addUpdateClanMember() = viewModelScope.launch {
        addClanMemberLoading.value = true

        try {
            val request = ClanMemberRequest(
                id = clanMember.value?.id,
                runescapeName = runescapeName.value!!,
                rank = rank.value!!,
                joinDate = joinDate.value!!,
                pets = pets.value ?: emptyList(),
                achievements = achievements.value ?: emptyList(),
            )

            val response = if (clanMember.value == null) NetworkInstance.API.addClanMember(request) else NetworkInstance.API.updateClanMember(request)

            addClanMemberResult.value = NetworkResult(data = response)
        } catch (e: Exception) {
            addClanMemberResult.value = NetworkResult(exception = e)
        }

        addClanMemberLoading.value = false
    }
}