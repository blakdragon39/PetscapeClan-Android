package com.blakdragon.petscapeclan.models

import com.blakdragon.petscapeclan.models.enums.Rank
import java.time.LocalDate

class ClanMember(
    val id: String,
    val runescapeName: String,
    val rank: Rank,
    val joinDate: LocalDate,
    val pets: List<Pet>,
    val achievements: List<Achievement>
)

class AddClanMemberRequest(
    val runescapeName: String,
    val rank: Rank,
    val joinDate: LocalDate,
    val pets: List<Pet>,
    val achievements: List<Achievement>
)