package com.blakdragon.petscapeclan.ui.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blakdragon.petscapeclan.databinding.CellClanMemberBinding
import com.blakdragon.petscapeclan.models.ClanMember
import com.blakdragon.petscapeclan.models.ClanMemberDiffUtil

class ClanMemberAdapter(
    private val onClanMemberClick: (ClanMember) -> Unit,
    private val onClanMemberLongClick: (ClanMember) -> Unit
) : RecyclerView.Adapter<ClanMemberAdapter.ClanMemberViewHolder>() {

    private var clanMembers: List<ClanMember> = listOf()

    fun setClanMembers(newClanMembers: List<ClanMember>) {
        val result = DiffUtil.calculateDiff(ClanMemberDiffUtil(clanMembers, newClanMembers))
        clanMembers = newClanMembers
        result.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = clanMembers.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClanMemberViewHolder =
        ClanMemberViewHolder(CellClanMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ClanMemberViewHolder, position: Int) = holder.bindView(clanMembers[position])

    inner class ClanMemberViewHolder(private val binding: CellClanMemberBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(clanMember: ClanMember) {
            binding.tvName.text = clanMember.runescapeName
            binding.ivRank.setImageResource(clanMember.rank.iconId)
            binding.tvAlts.text = clanMember.alts.joinToString("\n") { "• $it" }

            binding.root.setOnClickListener { onClanMemberClick(clanMember) }
            binding.root.setOnLongClickListener {
                onClanMemberLongClick(clanMember)
                true
            }
        }
    }
}
