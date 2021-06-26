package com.blakdragon.petscapeclan.ui.fragments.members

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.blakdragon.petscapeclan.R
import com.blakdragon.petscapeclan.core.network.NetworkInstance
import com.blakdragon.petscapeclan.databinding.DialogAddNameBinding
import com.blakdragon.petscapeclan.databinding.FragmentAddEditClanMemberBinding
import com.blakdragon.petscapeclan.models.*
import com.blakdragon.petscapeclan.models.enums.AchievementType
import com.blakdragon.petscapeclan.models.enums.PetType
import com.blakdragon.petscapeclan.models.enums.Rank
import com.blakdragon.petscapeclan.ui.BaseFragment
import com.blakdragon.petscapeclan.ui.MainActivity
import com.blakdragon.petscapeclan.utils.toString
import kotlinx.coroutines.launch
import java.time.LocalDate

class AddEditClanMemberFragment: BaseFragment<MainActivity>() {

    private var _binding: FragmentAddEditClanMemberBinding? = null
    private val binding: FragmentAddEditClanMemberBinding
        get() = _binding!!

    private val viewModel: AddEditClanMemberViewModel by viewModels()
    private val args: AddEditClanMemberFragmentArgs by navArgs()

    private lateinit var altsAdapter: AltsAdapter

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

        altsAdapter = AltsAdapter(
            onAddNameClick = { openNameDialog() },
            onRemoveNameClick = { name -> removeAlt(name) }
        )
        binding.rvAlts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAlts.adapter = altsAdapter

        initModel()
        initPets()
        initAchievements()

        observeViewModel()
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
            viewModel.alts.value = clanMember.alts
        }
    }

    private fun observeViewModel() {
        viewModel.alts.observe(viewLifecycleOwner, Observer { alts -> altsAdapter.setNames(alts) })

        viewModel.addClanMemberResult.observe(viewLifecycleOwner, Observer { result ->
            if (result.isSuccessful) {
                findNavController().navigate(AddEditClanMemberFragmentDirections.toClanMember(result.getData()))
            } else if (!result.exceptionHandled) {
                parentActivity.handleError(result.getUnhandledException())
            }
        })
    }

    private fun initPets() {
        val selectables = PetType.values()
            .filter { it != PetType.Unknown }
            .map { Pet(it) }
            .map { pet -> SelectableObject(pet, getString(pet.type.displayNameId), isSelected = args.clanMember?.pets?.contains(pet) == true) }

        binding.rvPets.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPets.adapter = SelectableAdapter(selectables, this::onPetSelected)
    }

    private fun initAchievements() {
        val selectables = AchievementType.values()
            .filter { it != AchievementType.Unknown }
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

    private fun openNameDialog() {
        val view: DialogAddNameBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.dialog_add_name, null, false)
        view.viewModel = viewModel

        AlertDialog.Builder(requireContext())
            .setView(view.root)
            .setPositiveButton(android.R.string.ok) { _, _ -> viewModel.addAlt() }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun removeAlt(name: String) {
        viewModel.alts.value = viewModel.alts.value?.filter { it != name }
    }
}

class AddEditClanMemberViewModel : ViewModel() {

    val clanMember = MutableLiveData<ClanMember>()

    val runescapeName = MutableLiveData("")
    val joinDate = MutableLiveData(LocalDate.now())
    val rank = MutableLiveData(Rank.Bronze)
    val pets = MutableLiveData<List<Pet>>()
    val achievements = MutableLiveData<List<Achievement>>()
    val alts = MutableLiveData<List<String>>(listOf())

    val newAltName = MutableLiveData("")
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
                alts = alts.value ?: emptyList()
            )

            val response = if (clanMember.value == null) NetworkInstance.API.addClanMember(request) else NetworkInstance.API.updateClanMember(request)

            addClanMemberResult.value = NetworkResult(data = response)
        } catch (e: Exception) {
            addClanMemberResult.value = NetworkResult(exception = e)
        }

        addClanMemberLoading.value = false
    }

    fun addAlt() {
        alts.value = mutableListOf<String>().apply {
            alts.value?.let { addAll(it) }
            newAltName.value?.let { add(it) }
        }

        newAltName.value = ""
    }
}