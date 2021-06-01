package com.blakdragon.petscapeclan.models

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.blakdragon.petscapeclan.models.enums.Rank
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
class ClanMember(
    val id: String,
    val runescapeName: String,
    val rank: Rank,
    val joinDate: LocalDate,
    var lastSeen: LocalDate?,
    val bossKc: Int,
    val pets: List<Pet>,
    val achievements: List<Achievement>,
    val points: Int,
    val alts: List<String>
) : Parcelable {

    fun determinePossibleRank(): Rank? = Rank.values().firstOrNull { it.points <= points }
}

class ClanMemberRequest(
    val id: String? = null,
    val runescapeName: String,
    val rank: Rank,
    val joinDate: LocalDate,
    val pets: List<Pet>,
    val achievements: List<Achievement>,
    val alts: List<String>
)

class ClanMemberDiffUtil(
    private val oldMembers: List<ClanMember>,
    private val newMembers: List<ClanMember>
) : DiffUtil.Callback() {

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMember = oldMembers[oldItemPosition]
        val newMember = newMembers[newItemPosition]

        return oldMember.id == newMember.id &&
                oldMember.runescapeName == newMember.runescapeName &&
                oldMember.rank == newMember.rank &&
                oldMember.joinDate == newMember.joinDate &&
                oldMember.bossKc == newMember.bossKc &&
                oldMember.pets == newMember.pets &&
                oldMember.achievements == newMember.achievements &&
                oldMember.points == newMember.points &&
                oldMember.alts == newMember.alts

    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldMembers[oldItemPosition].id == newMembers[newItemPosition].id
    }

    override fun getNewListSize(): Int = newMembers.size

    override fun getOldListSize(): Int = oldMembers.size
}