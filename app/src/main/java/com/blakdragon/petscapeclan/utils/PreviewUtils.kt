package com.blakdragon.petscapeclan.utils

import com.blakdragon.petscapeclan.models.Achievement
import com.blakdragon.petscapeclan.models.ClanMember
import com.blakdragon.petscapeclan.models.Pet
import com.blakdragon.petscapeclan.models.Rank
import com.blakdragon.petscapeclan.models.enums.AchievementType
import com.blakdragon.petscapeclan.models.enums.PetType
import com.blakdragon.petscapeclan.models.enums.RankType
import java.time.LocalDate

val previewClanMember1 = ClanMember(
    id = "",
    runescapeName = "Blakdragon",
    rank = Rank(RankType.Dragon, "Dragon", 0, 0),
    joinDate = LocalDate.now(),
    lastSeen = LocalDate.now(),
    bossKc = 40000,
    pets = listOf(
        Pet(PetType.AbyssalOrphan, "Abyssal Orphan"),
        Pet(PetType.CallistoCub, "Callisto Cub")
    ),
    achievements = listOf(
        Achievement(AchievementType.AchievementCape, "Achievement Cape"),
        Achievement(AchievementType.ChampionCape, "Champion Cape")
    ),
    points = 100,
    alts = listOf("MsBlakdragon"),
    possibleRank = Rank(RankType.Dragon, "Dragon", 0, 0)
)

val previewClanMember2 = ClanMember(
    id = "",
    runescapeName = "MaxPerson808",
    rank = Rank(RankType.Owner, "Owner", 0, 0),
    joinDate = LocalDate.now(),
    lastSeen = LocalDate.now(),
    bossKc = 40000,
    pets = listOf(
        Pet(PetType.AbyssalOrphan, "Abyssal Orphan"),
        Pet(PetType.CallistoCub, "Callisto Cub")
    ),
    achievements = listOf(
        Achievement(AchievementType.AchievementCape, "Achievement Cape"),
        Achievement(AchievementType.ChampionCape, "Champion Cape")
    ),
    points = 100,
    alts = listOf("MsBlakdragon"),
    possibleRank = Rank(RankType.Dragon, "Dragon", 0, 0)
)