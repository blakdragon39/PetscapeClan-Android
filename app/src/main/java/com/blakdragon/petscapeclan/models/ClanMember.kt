package com.blakdragon.petscapeclan.models

import android.os.Parcelable
import com.blakdragon.petscapeclan.models.enums.Rank
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
class ClanMember(
    val id: String,
    val runescapeName: String,
    val rank: Rank,
    val joinDate: LocalDate,
    val bossKc: Int,
    val pets: List<Pet>,
    val achievements: List<Achievement>
) : Parcelable

class AddClanMemberRequest(
    val runescapeName: String,
    val rank: Rank,
    val joinDate: LocalDate,
    val bossKc: Int,
    val pets: List<Pet>,
    val achievements: List<Achievement>
)