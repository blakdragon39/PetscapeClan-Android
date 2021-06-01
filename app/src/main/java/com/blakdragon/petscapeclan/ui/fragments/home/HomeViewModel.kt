package com.blakdragon.petscapeclan.ui.fragments.home

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blakdragon.petscapeclan.core.network.NetworkInstance
import com.blakdragon.petscapeclan.models.ClanMember
import kotlinx.coroutines.launch
import java.time.LocalDate

class HomeViewModel : ViewModel() {

    val exception = MutableLiveData<Exception>()
    val filteredClanMembers = MediatorLiveData<List<ClanMember>>()

    val filterNotSeenToday = MutableLiveData(false)
    val filterNeedsRankUp = MutableLiveData(false)
    val filterName = MutableLiveData("")
    val sortMethod = MutableLiveData<HomeSortMethod>(HomeSortMethod.DEFAULT)

    val clanMembersLoading = MutableLiveData(false)

    private val clanMembers = MutableLiveData<List<ClanMember>>()

    init {
        listOf(
            clanMembers,
            filterNotSeenToday,
            filterNeedsRankUp,
            filterName,
            sortMethod)
            .forEach { source ->
                filteredClanMembers.addSource(source) { filteredClanMembers.value = sortAndFilterClanMembers() }
            }
    }

    fun getClanMembers() = viewModelScope.launch {
        clanMembersLoading.value = true

        try {
            clanMembers.value = NetworkInstance.API.getClanMembers()
        } catch (e: Exception) {
            exception.value = e
        }

        clanMembersLoading.value = false
    }

    fun updateLastSeenClanMember(clanMember: ClanMember) = viewModelScope.launch {
        clanMembersLoading.value = true

        try {
            val result = NetworkInstance.API.updateLastSeen(clanMember.id)
            clanMembers.value = clanMembers.value
                ?.map { if (it.id == result.id) result else it }
        } catch (e: Exception) {
            exception.value = e
        }

        clanMembersLoading.value = false
    }

    private fun sortAndFilterClanMembers(): List<ClanMember>? {
        val nameFilter = filterName.value?.lowercase() ?: ""

        var returnMembers = clanMembers.value

        if (filterNotSeenToday.value == true) {
            val today = LocalDate.now()
            returnMembers = clanMembers.value?.filter { it.lastSeen?.isBefore(today) != false }
        }

        if (filterNeedsRankUp.value == true) {
            returnMembers = returnMembers?.filter { (it.determinePossibleRank()?.ordinal ?: Int.MAX_VALUE) < it.rank.ordinal }
        }

        returnMembers = returnMembers
            ?.filter { it.runescapeName.lowercase().contains(nameFilter) || it.alts.any { altName -> altName.lowercase().contains(nameFilter) } }

        returnMembers = when (sortMethod.value) {
            HomeSortMethod.DEFAULT -> returnMembers
                ?.sortedBy { it.runescapeName.lowercase() }
                ?.sortedBy { it.rank.ordinal }
            HomeSortMethod.ALPHABETICAL -> returnMembers?.sortedBy { it.runescapeName }
            else -> returnMembers
        }

        return returnMembers
    }
}

enum class HomeSortMethod { DEFAULT, ALPHABETICAL }