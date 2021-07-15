package com.blakdragon.petscapeclan.core

import com.blakdragon.petscapeclan.core.network.NetworkInstance
import com.blakdragon.petscapeclan.models.AchievementData
import com.blakdragon.petscapeclan.models.ClanMember
import com.blakdragon.petscapeclan.models.PetData
import com.blakdragon.petscapeclan.models.Rank
import com.blakdragon.petscapeclan.models.enums.RankType

object DataRepo {

    private var achievements: List<AchievementData>? = null //todo should these be live data?
    private var pets: List<PetData>? = null
    private var ranks: List<Rank>? = null

    suspend fun getAchievements(): List<AchievementData> {
        if (achievements == null) {
            achievements = NetworkInstance.API.getAchievements()
        }

        return achievements!!
    }

    suspend fun getPets(): List<PetData> {
        if (pets == null) {
            pets = NetworkInstance.API.getPets()
        }

        return pets!!
    }

    suspend fun getRanks(): List<Rank> {
        if (ranks == null) {
            ranks = NetworkInstance.API.getRanks().sortedByDescending { it.order }
        }

        return ranks!!
    }
}